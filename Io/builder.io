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

List asAttributes := method(
  r := ""
  foreach(pair,
    r = r .. " #{pair at(0)}=\"#{pair at(1)}\"" interpolate
  )
  r
)

Builder := Map clone do(
  indent ::= ""
)

Builder atPutNumber := method(
  self atPut(
    call evalArgAt(0) asMutable removePrefix("\"") removeSuffix("\""),
    call evalArgAt(1)
  )
)

Builder curlyBrackets := method(
  r := list
  call message arguments foreach(arg,
  r append( arg asSimpleString split(": ") )
)
r 
)

Builder forward := method(
  attributes := doMessage(call message argAt(0))
  attributes = if(attributes type == "List", attributes asAttributes, "")
  writeln(indent, "<" , call message name, attributes, ">")
  setIndent(indent .. "  ")
  call message arguments foreach(
    arg, 
    content := self doMessage(arg);
    if(content type == "Sequence", writeln(indent .. content)))
  setIndent(indent exSlice(2))
  writeln("#{indent}</" interpolate, call message name, ">"))


Builder ul( { hello: world , this: that},
  li("Io"),
  li("Lua"),
  li("JavaScript"))


