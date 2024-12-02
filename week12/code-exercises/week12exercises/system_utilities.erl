% raup@itu.dk * 2024-11-14
-module(system_utilities).
-export([
    system_start2/0,
    send_2_requests/4,
    requests_and_print/0,
    test_bank_request/0,
    test_bank_request/2
]).

%% 12.1.4

system_start2() ->
    A1 = account:start(),
    A2 = account:start(),
    B1 = bank:start("DanskeBank"),
    B2 = bank:start("Nordea"),
    MA1 = mobile_app:start("Mobile Pay", B1),
    MA2 = mobile_app:start("Swish", B2),
    {A1, A2, B1, B2, MA1, MA2}.

send_2_requests(MA1, MA2, A1, A2) ->
    MA1 ! {payment_request, {1, A1, A2}},
    MA2 ! {payment_request, {1, A2, A2}}.

%% 12.1.6
requests_and_print() ->
    A1 = account:start(),
    A2 = account:start(),
    B1 = bank:start("DanskeBank"),
    MA1 = mobile_app:start("Mobile Pay", B1),
    mobile_app:send_N_requests(100, MA1, A1, A2),
    account:print_balance(A1),
    account:print_balance(A2),
    {A1, A2}.

%% 12.1.8
test_bank_request() ->
    test_bank_request(0, 0).

%% 12.1.9
test_bank_request(Initial_balance_1, Initial_balance_2) ->
    B1 = bank:start("Nordea"),
    B2 = bank:start("DanskeBank"),
    A1 = account:start(Initial_balance_1, B1),
    A2 = account:start(Initial_balance_2, B2),
    MA1 = mobile_app:start("Mobile Pay", B1),
    mobile_app:send_bank_payment_request(MA1, A2, A1, 10),
    {B1, B2, A1, A2, MA1}.
