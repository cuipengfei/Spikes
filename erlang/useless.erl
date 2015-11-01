-module(useless).
-export([add/2, greet_and_add_two/1, hello/0, greet/2]).

add(A, B) ->
  A + B.

hello() ->
  io:format("hello").

greet_and_add_two(X) ->
  hello(),
  add(X, 2).

greet(male, Name) ->
  io:format("Hello, Mr ~s!", [Name]);
greet(female, Name) ->
  io:format("Hello, Ms ~s!", [Name]);
greet(_, Name) ->
  io:format("Hello,  ~s!", [Name]).
