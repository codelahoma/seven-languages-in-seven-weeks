-module(langs).
-export([lookup/2]).
lookup([], _) -> "not found";
lookup([{Keyword, Desc}|T], Key) ->
  case Keyword of
    Key -> Desc;
      _ -> lookup(T, Key)
