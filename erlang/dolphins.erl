-module(dolphins).
-author("pfcui").
-compile(export_all).

dolphin1() ->
  receive
    do_a_flip ->
      io:format("How about no?~n");
    fish ->
      io:format("So long and thanks for all the fish!~n");
    _ ->
      io:format("Heh, we're smarter than you humans.~n")
  end.

dolphin2() ->
  receive
    {From, do_a_flip} ->
      From ! "How about no?";
    {From, fish} ->
      From ! "So long and thanks for all the fish!";
    _ ->
      io:format("Heh, we're smarter than you humans.~n")
  end.

dolphin3() ->
  receive
    {From, do_a_flip} ->
      From ! "How about no?",
      dolphin3();
    {From, fish} ->
      From ! "So long and thanks for all the fish!";
    _ ->
      io:format("Heh, we're smarter than you humans.~n"),
      dolphin3()
  end.

call_dophin1() ->
  Dolphin = spawn(dolphins, dolphin1, []),
  Dolphin ! "oh, hello dolphin!".

call_dophin3() ->
  Dolphin3 = spawn(dolphins, dolphin3, []),
  Dolphin3 ! Dolphin3 ! {self(), do_a_flip},
  Dolphin3 ! {self(), unknown_message},
  Dolphin3 ! Dolphin3 ! {self(), fish}.