% raup@itu.dk * 2024-11-14
-module(mobile_app).

%% 12.1.1
%% export any necessary functions
-export([start/2, loop/1, send_N_requests/4, send_bank_payment_request/4]).

%% define the mobile app actor state
-record(mobile_app_state, {name, bank}).

%% define a start(...) function that spawns a mobile app actor
start(Name, Bank_PID) ->
    spawn(?MODULE, loop, [init(Name, Bank_PID)]).

%% define a init(...) function that initalizes the state of the mobile app actor
init(Name, Bank_PID) ->
    #mobile_app_state{name = Name, bank = Bank_PID}.

%% loop(...) function with the behavior of the mobile app actor upon receiving messages
loop(State) ->
    receive
        {payment_request, {Amount, Account1, Account2}} ->
            State#mobile_app_state.bank ! {transfer, {Amount, Account1, Account2}},
            loop(State);
        {payment_request_bank, {Amount, Account1, Account2}} ->
            State#mobile_app_state.bank ! {transfer_bank, {Amount, Account1, Account2}},
            loop(State)
    end.

%% 12.1.5
send_N_requests(0, _MobileAppPid, _Account1, _Account2) ->
    error({badarg, "The number of requests must be larger than 0"});
send_N_requests(N, MobileAppPid, Account1, Account2) when N > 0 ->
    SendRequest =
        fun(_X) ->
            MobileAppPid ! {payment_request, {1, Account1, Account2}}
        end,
    lists:foreach(SendRequest, lists:seq(1, N)).

%% 12.1.8
send_bank_payment_request(MobileAppPid, Account1, Account2, Amount) ->
    MobileAppPid ! {payment_request_bank, {Amount, Account1, Account2}}.
