-module(yet_again).
-export([another_factorial/1]).
-export([another_fib/1]).
-export([count/1]).
-export([log/1]).
another_factorial(0) -> 1;
another_factorial(N) -> N * another_factorial(N-1).
another_fib(0) -> 1;
another_fib(1) -> 1;
another_fib(N) -> another_fib(N-1) + another_fib(N-2).
count(1) -> io:format("1~n",[]);
count(N) -> count(N - 1), io:format("~w~n",[N]).
log(success) -> io:format("success~n",[]);
log({error, Message}) -> io:format("error: ~s~n", [Message]).

