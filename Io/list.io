squareBrackets := method(
  r := List clone
  call message arguments foreach(arg,
    r append(doMessage(arg))
  )
  r
)

List squareBrackets := method(a, at(a))

l := [1, 2, 4, 5 + 4]
writeln(l)

