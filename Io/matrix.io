Matrix := Object clone do(
  data ::= nil
)


Matrix dim := method(x, y,
    data = list()
    for(i, 1, y, data append(list() for(j, 1, x, append(0)))))

Matrix set := method(x, y, value,
    data at(y) atPut(x, value))

Matrix get := method(x, y, data at(y) at(x))

Matrix transpose := method(
    Matrix clone for(x, 0, (data at(0) size) -1 , append(
     list() for(y, 0, (data size) - 1, append(at(y) at(x))))))

Matrix save := method(
  File with("matrix.data") open write(serialized))

Matrix restore := method(
   doRelativeFile("matrix.data")
)


m := Matrix clone

m dim(3,7)
m set(2,5,25)
/* n := m transpose */
writeln(m get(2,5))

m save

m = nil

m = Matrix restore

writeln(m get(2,5))
