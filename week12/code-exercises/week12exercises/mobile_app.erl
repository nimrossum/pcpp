% raup@itu.dk * 2024-11-14

-module(mobile_app).

%% export any necessary functions
%% -export([...]).
-export([start/0, init/1]).

-record(counter_state, {total = 0}).

%% define the mobile app actor state

%% define a start(...) function that spawns a mobile app actor
start() ->
  spawn(mobile_app, init, []).

%% define a init(...) function that initalizes the state of the mobile app actor
init(InitialValue) ->
  InitialState = #counter_state{total = InitialValue},
  loop(InitialState).

%% loop(...) function with the behavior of the mobile app actor upon receiving messages
loop(State) ->
  receive
      increment ->
        CurrentTotal = State#counter_state.total,
        NewState = State#counter_state{total = CurrentTotal + 1},
        io:format("A visitor arrived. Total visitors: ~p~n", [NewState#counter_state.total]),
        loop(NewState);
    print_total ->
        io:format("Total visitors: ~p~n", [State#counter_state.total]),
        loop(State);
  end.
