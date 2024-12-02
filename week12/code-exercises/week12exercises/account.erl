% raup@itu.dk * 2024-11-14
-module(account).

%% 12.1.3
%% export any necessary functions
-export([start/0, start/1, start/2, loop/1, print_balance/1, deposit_bank/2]).

%% define the account actor state
-record(account_state, {balance, bank}).

%% define a start(...) function that spawns an account actor
start() ->
    spawn(?MODULE, loop, [init(0)]).

start(Initial_balance) ->
    spawn(?MODULE, loop, [init(Initial_balance)]).

start(Initial_balance, Bank_PID) ->
    spawn(?MODULE, loop, [init(Initial_balance, Bank_PID)]).

init(Initial_balance) ->
    #account_state{balance = Initial_balance}.

%% define a init(...) function that initalizes the state of the account actor
init(Initial_balance, Bank_PID) ->
    #account_state{balance = Initial_balance, bank = Bank_PID}.

%% loop(...) function with the behavior of the account actor upon receiving messages
loop(State) ->
    receive
        {deposit, Amount} ->
            CurrentBalance = State#account_state.balance,
            NewState = State#account_state{balance = CurrentBalance + Amount},
            loop(NewState);
        %% 12.1.6
        {print_balance} ->
            io:format(
                "The balance in account ~p is ~p ~n",
                [self(), State#account_state.balance]
            ),
            loop(State);
        %% 12.1.9 (recall that withdrawals are negative deposits, that is why we write ... + Amount < 0)
        {deposit_bank, {Ref, Bank_PID, Amount}} when State#account_state.balance + Amount < 0 ->
            Bank_PID ! {insufficient_funds, Ref},
            loop(State);
        %% 12.1.8 (recall that these cases are only executed if State#account_state.balance + Amount < 0 >= 0)
        {deposit_bank, {Ref, Bank_PID, Amount}} when Amount >= 0 ->
            CurrentBalance = State#account_state.balance,
            NewState = State#account_state{balance = CurrentBalance + Amount},
            Bank_PID ! {ok, Ref},
            loop(NewState);
        {deposit_bank, {Ref, Bank_PID, Amount}} when Amount < 0 ->
            case Bank_PID == State#account_state.bank of
                true ->
                    CurrentBalance = State#account_state.balance,
                    NewState = State#account_state{balance = CurrentBalance + Amount},
                    Bank_PID ! {ok, Ref},
                    loop(NewState);
                false ->
                    Bank_PID ! {wrong_bank, Ref},
                    loop(State)
            end
    end.

print_balance(AccountPID) ->
    AccountPID ! {print_balance}.

%% 12.1.8 and 12.1.9
deposit_bank(Account_PID, Amount) ->
    %% We tacitly assume that this function is only executed by bank actors
    Ref = make_ref(),
    Account_PID ! {deposit_bank, {Ref, self(), Amount}},
    receive
        {ok, Ref} ->
            ok;
        {wrong_bank, Ref} ->
            wrong_bank;
        {insufficient_funds, Ref} ->
            insufficient_funds
    end.
