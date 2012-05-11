add2d := method(arr,
          arr map(x, x sum) sum)

myList := list(
  list(1,2,3),
  list(20,15,75),
  list(3,4,5))

writeln(add2d(myList))
