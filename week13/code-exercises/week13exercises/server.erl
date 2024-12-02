% raup@itu.dk * 2024-11-22

-module(server).
-export([start/1, start/2, init/1, init/2]).
-include("defs.hrl").

% 1. State
-record(server_state, {
    % 13.1.1
    idle_workers,
    busy_workers,
    pending_tasks,
    % 13.1.2
    minWorkers,
    maxWorkers
}).

% 2. Start

% 13.1.1
start(NumWorkers) ->
    spawn(?MODULE, init, [NumWorkers]).

% 13.1.2
start(MinWorkers, MaxWorkers) ->
    spawn(?MODULE, init, [MinWorkers, MaxWorkers]).

% 3. Initialization

init(NumWorkers) ->
    init(NumWorkers, NumWorkers).

init(MinWorkers, MaxWorkers) ->
    Workers = lists:map(fun(_X) -> worker:start(self()) end, lists:seq(1, MinWorkers)),
    % 13.1.3
    lists:foreach(fun(X) -> monitor(process, X) end, Workers),
    State = #server_state{
        idle_workers = Workers,
        busy_workers = [],
        pending_tasks = [],
        minWorkers = MinWorkers,
        maxWorkers = MaxWorkers
    },
    loop(State).

% 4. Behavior upon receiving messages
loop(State) ->
    receive
        {compute, SenderPID, Tasks} ->
            handle_compute(Tasks, SenderPID, State);
        {work_done, WorkerPID} ->
            handle_work_done(WorkerPID, State);
        {'DOWN', _Ref, process, WorkerPID, Reason} ->
            handle_down_signal(WorkerPID, Reason, State);
        %%% Debugging messages
        idle_workers ->
            io:format("Idle workers: ~p~n", [State#server_state.idle_workers])
    end.

% 5. Message handlers

% 13.1.3
handle_down_signal(WorkerPID, normal, State) ->
    io:format("Process ~p terminated normally~n", [WorkerPID]),
    loop(State);
% 13.1.3
handle_down_signal(
    WorkerPID,
    Reason,
    % 13.1.5
    State = #server_state{
        pending_tasks = [],
        busy_workers = BusyWorkers,
        idle_workers = IdleWorkers
    }
) ->
    io:format("Worker ~p crashed due to ~p", [WorkerPID, Reason]),
    NewWorkerPID = worker:start(self()),
    monitor(process, NewWorkerPID),
    NewIdleWorkers = [NewWorkerPID | IdleWorkers],
    NewBusyWorkers = BusyWorkers -- [WorkerPID],
    NewState = State#server_state{
        busy_workers = NewBusyWorkers,
        idle_workers = NewIdleWorkers
    },
    loop(NewState);
% 13.1.5
handle_down_signal(
    WorkerPID,
    Reason,
    State = #server_state{
        pending_tasks = [H | T],
        busy_workers = BusyWorkers
    }
) ->
    io:format("Worker ~p crashed due to ~p~n", [WorkerPID, Reason]),
    NewWorkerPID = worker:start(self()),
    monitor(process, NewWorkerPID),
    {SenderPID, Task} = H,
    NewWorkerPID ! {compute, SenderPID, Task},
    NewBusyWorkers = (BusyWorkers -- [WorkerPID]) ++ [NewWorkerPID],
    NewState = State#server_state{
        busy_workers = NewBusyWorkers,
        pending_tasks = T
    },
    loop(NewState).

% 13.1.4
% it instructs the worker to terminate if the number of workers in the
% system is greater than minWorkers.
handle_work_done(
    WorkerPID,
    State = #server_state{
        idle_workers = IdleWorkers,
        busy_workers = BusyWorkers,
        pending_tasks = [],
        minWorkers = MinWorkers
    }
) when
    length(BusyWorkers) + length(IdleWorkers) > MinWorkers
->
    WorkerPID ! stop,
    NewBusyWorkers = lists:delete(WorkerPID, BusyWorkers),
    NewState = State#server_state{busy_workers = NewBusyWorkers},
    loop(NewState);
% 13.1.1
%% (b) If there are no pending tasks, it must set the worker as idle.
handle_work_done(
    WorkerPID,
    State = #server_state{
        busy_workers = BusyWorkers,
        idle_workers = IdleWorkers,
        pending_tasks = []
    }
) ->
    NewBusyWorkers = lists:delete(WorkerPID, BusyWorkers),
    NewIdleWorkers = IdleWorkers ++ [WorkerPID],
    NewState = State#server_state{
        busy_workers = NewBusyWorkers,
        idle_workers = NewIdleWorkers
    },
    loop(NewState);
% 13.1.1
% (a) If there are pending tasks, then the server selects one and sends it to the worker.
handle_work_done(
    WorkerPID,
    State = #server_state{pending_tasks = [H | T]}
) ->
    {SenderPID, Task} = H,
    WorkerPID ! {compute, SenderPID, Task},
    NewState = State#server_state{pending_tasks = T},
    loop(NewState).

% 13.1.1 - 13.1.3
handle_compute(Tasks, SenderPID, State) ->
    send_or_pending(Tasks, SenderPID, State).

% Private functions (to be used, e.g., by message handlers)

send_or_pending([], _SenderPID, State) ->
    loop(State);
% 13.1.2
% (b) If there are no idle workers, but the number of busy workers is
% less than the maximum number of workers, then start a new worker and
% send the task to the worker.
send_or_pending(
    [H | T],
    SenderPID,
    State = #server_state{
        idle_workers = [],
        busy_workers = BusyWorkers,
        maxWorkers = MaxWorkers
    }
) when
    length(BusyWorkers) < MaxWorkers
->
    WorkerPID = worker:start(self()),
    % 13.1.3
    monitor(process, WorkerPID),
    WorkerPID ! {compute, SenderPID, H},
    NewBusyWorkers = [WorkerPID | BusyWorkers],
    NewState = State#server_state{busy_workers = NewBusyWorkers},
    send_or_pending(T, SenderPID, NewState);
% (13.1.1, 13.1.2)
% (c) If there are no idle workers and the number of busy workers
% equals the maximum number of workers, then it must place the task in
% the list of pending tasks.

% NOTE: The main difference between 13.1.1 and 13.1.2 is the check in
% the guard
send_or_pending(
    [H | T],
    SenderPID,
    State = #server_state{
        idle_workers = [],
        pending_tasks = PendingTasks,
        busy_workers = BusyWorkers,
        maxWorkers = MaxWorkers
    }
) when
    length(BusyWorkers) >= MaxWorkers
->
    NewPendingTasks = [{SenderPID, H} | PendingTasks],
    NewState = State#server_state{pending_tasks = NewPendingTasks},
    send_or_pending(T, SenderPID, NewState);
%%% (13.1.1, 13.1.2)
% (a) If there are idle workers, then it must send the task to one of
% the idle workers.
send_or_pending(
    [H | T],
    SenderPID,
    State = #server_state{idle_workers = [Worker | Workers]}
) ->
    Worker ! {compute, SenderPID, H},
    NewBusyWorkers = [Worker | State#server_state.busy_workers],
    NewState = State#server_state{
        busy_workers = NewBusyWorkers,
        idle_workers = Workers
    },
    send_or_pending(T, SenderPID, NewState).
