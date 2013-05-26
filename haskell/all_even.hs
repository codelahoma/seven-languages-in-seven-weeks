module Main where
allEven :: [Integer] -> [Integer]
allEven [] = []
allEven (h:t) = if even h then h:allEven t else allEven t


rev :: [Integer] -> [Integer]
rev [] = []
rev (h:t) = rev t ++ [h]



