% raup@itu.dk * 2024-11-14
-module(bank).

%% export any necessary functions
%% 12.1.2
-export([start/1, loop/1]).

%% define the bank actor state
-record(bank_state, {name, accounts}).

%% define a start(...) function that spawns a bank actor
start(Name) ->
    spawn(?MODULE, loop, [init(Name)]).

%% define a init(...) function that initalizes the state of the bank actor
init(Name) ->
    #bank_state{name = Name, accounts = []}.

%% loop(...) function with the behavior of the bank actor upon receiving messages
loop(State) ->
    receive
        {transfer, {Amount, Account1, Account2}} ->
            Account1 ! {deposit, -Amount},
            Account2 ! {deposit, Amount},
            loop(State);
        %% 12.1.8 and 12.1.9
        {transfer_bank, {Amount, Account1, Account2}} ->
            Reply = account:deposit_bank(Account1, -Amount),
            case Reply of
                ok ->
                    account:deposit_bank(Account2, Amount);
                wrong_bank ->
                    io:format("The withdrawal was denied: wrong bank~n");
                insufficient_funds ->
                    io:format("There are not enough funds in account ~p~n", [Account1])
            end,
            loop(State)
    end.
