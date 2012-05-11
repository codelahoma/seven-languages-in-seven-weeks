writeln( 4 / 0)

Number oldDiv := Number getSlot("/")

writeln(4 oldDiv(2))

Number setSlot("/", method( n, 
    if(n == 0, 0, oldDiv(n))))

writeln( 4 / 0)
