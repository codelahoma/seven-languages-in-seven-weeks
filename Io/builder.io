OperatorTable addAssignOperator(":", "atPutNumber")
curlyBrackets := method(
  r := Map clone
  call message arguments foreach(arg,
  r doMessage(arg)
)
r
)

Map atPutNumber := method(
  self atPut(
    call evalArgAt(0) asMutable removePrefix("\"") removeSuffix("\""),
    call evalArgAt(1)
  )
)

Builder := Object clone do(
  indent ::= ""
)
Builder forward := method(
  /* if(call evalArgAt(0) type == Map, "yep" println) */
  writeln("#{indent}<" interpolate, call message name, ">")
  setIndent(indent .. "  ")
  call message arguments foreach(
    arg, 
    content := self doMessage(arg);
    if(content type == "Sequence", writeln(indent .. content)))
  setIndent(indent exSlice(2))
  writeln("#{indent}</" interpolate, call message name, ">"))

Builder ul(
  li("Io"),
  li("Lua"),
  li("JavaScript"))

Builder {"j": "j"} println
