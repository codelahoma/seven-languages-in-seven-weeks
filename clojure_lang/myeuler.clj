(defn gen-primes 
  []
  (str "Generates an infinite, lazy sequence of prime numbers"
       "\n found @ http://stackoverflow.com/questions/960980/fast-prime-number-generation-in-clojure")
  (let [reinsert (fn [table x prime]
                   (update-in table [(+ prime x)] conj prime))]
    (defn primes-step [table d]
                 (if-let [factors (get table d)]
                   (recur (reduce #(reinsert %1 d %2) (dissoc table d) factors)
                          (inc d))
                   (lazy-seq (cons d (primes-step (assoc table (* d d) (list d))
                                                 (inc d))))))
    (primes-step {} 2)))

(def prob11-grid [ 
                  8 2 22 97 38 15 0 40 0 75 4 5 7 78 52 12 50 77 91 8
                  49 49 99 40 17 81 18 57 60 87 17 40 98 43 69 48 4 56 62 0
                  81 49 31 73 55 79 14 29 93 71 40 67 53 88 30 3 49 13 36 65
                  52 70 95 23 4 60 11 42 69 24 68 56 1 32 56 71 37 2 36 91
                  22 31 16 71 51 67 63 89 41 92 36 54 22 40 40 28 66 33 13 80
                  24 47 32 60 99 3 45 2 44 75 33 53 78 36 84 20 35 17 12 50
                  32 98 81 28 64 23 67 10 26 38 40 67 59 54 70 66 18 38 64 70
                  67 26 20 68 2 62 12 20 95 63 94 39 63 8 40 91 66 49 94 21
                  24 55 58 5 66 73 99 26 97 17 78 78 96 83 14 88 34 89 63 72
                  21 36 23 9 75 0 76 44 20 45 35 14 0 61 33 97 34 31 33 95
                  78 17 53 28 22 75 31 67 15 94 3 80 4 62 16 14 9 53 56 92
                  16 39 5 42 96 35 31 47 55 58 88 24 0 17 54 24 36 29 85 57
                  86 56 0 48 35 71 89 7 5 44 44 37 44 60 21 58 51 54 17 58
                  19 80 81 68 5 94 47 69 28 73 92 13 86 52 17 77 4 89 55 40
                  4 52 8 83 97 35 99 16 7 97 57 32 16 26 26 79 33 27 98 66
                  88 36 68 87 57 62 20 72 3 46 33 67 46 55 12 32 63 93 53 69
                  4 42 16 73 38 25 39 11 24 94 72 18 8 46 29 32 40 62 76 36
                  20 69 36 41 72 30 23 88 34 62 99 69 82 67 59 85 74 4 36 16
                  20 73 35 29 78 31 90 1 74 31 49 71 48 86 81 16 23 57 5 54
                  1 70 54 71 83 51 54 69 16 92 33 48 61 43 52 1 89 19 67 48
                  ])

(defn cell [x y] [x y])

(defn val-at [c] (let [[x y] c]
                      (if 
                        (or (> x 19) 
                            (< x 0) 
                            (> y 19) 
                            (< y 0)) 
                        1 
                        (prob11-grid (+ x (* 20 y))))))


(defn east [c]
  (let [[x y] c]
    (for [i (range 4)] [(+ x i) y])))

(defn west [c]
  (let [[x y] c]
    (for [i (range 4)] [(- x i) y])))

(defn north [c]
  (let [[x y] c]
    (for [i (range 4)] [x (- y i)])))

(defn south [c]
  (let [[x y] c]
    (for [i (range 4)] [x (+ y i)])))

(defn northeast [c]
  (let [[x y] c]
    (for [i (range 4)] [(+ x i) (- y i)])))

(defn southeast [c]
  (let [[x y] c]
    (for [i (range 4)] [(+ x i) (+ y i)])))

(defn southwest [c]
    (let [[x y] c]
          (for [i (range 4)] [(- x i) (+ y i)])))

(defn northwest [c]
    (let [[x y] c]
          (for [i (range 4)] [(- x i) (- y i)])))

(def directions [east west north south northeast southeast southwest northwest])

(defn product [cs] (reduce * (map val-at cs)))

(defn max-for-cell [x y] 
  (apply max 
    (for [direction directions] 
      (product (direction (cell x y))))))

(defn prob11-answer []
  (apply max
         (for [x (range 19) y (range 19)] (max-for-cell x y))))

(defn factor? [n potential]
  (zero? (rem n potential)))

(defn factors [n]
  (let [factors-below-sqrt (filter #(factor? n %) (range 1 (inc (Math/sqrt n))))
        factors-above-sqrt (map #(/ n %) factors-below-sqrt)]
    (set(concat factors-below-sqrt factors-above-sqrt))))

(def get-factors (memoize factors))

(def triangle-number 
  (fn [n]
    (loop [cnt n acc 1]
      (if (<= cnt 1)
        acc
        (recur (dec cnt) (+ acc cnt))))))

(def get-triangle-number (memoize triangle-number))


(def triangle-numbers (map get-triangle-number (rest (range))))

(def triangle-numbers-with-factor-count (map #(vector % (count (get-factors %))) triangle-numbers))

(def collatz-length
  (fn [n]
    (loop [cur n acc 1]
      (if (<=  cur 1)
        acc
        (recur
               (if (zero? (rem cur 2))
                 (/ cur 2)
                 (inc (* cur 3)))
          (inc acc))))))

(def get-collatz-length (memoize collatz-length))

(defn lattice-transform [row]
  (letfn [(newval [i r]
          (if (zero? i)
            1
            (+ (row i) (newval (dec i) r))))]
    (vec (map-indexed newval row))))

(def ones ["" "one" "two" "three" "four" "five" "six" "seven" "eight" "nine" ])
(def teens ["ten" "eleven" "twelve" "thirteen" "fourteen" "fifteen" "sixteen" "seventeen" "eighteen" "nineteen"])
(def tens ["" "" "twenty" "thirty" "forty" "fifty" "sixty" "seventy" "eighty" "ninety"])

(defn digits [n]
  (if (zero? n)
    []
    (conj (digits (quot n 10)) (rem n 10))))

(defn ones-digit [n]
  (rem n 10))

(defn tens-digit [n]
  (rem (quot n 10) 10))

(defn hundreds-digit [n]
  (rem (quot n 100) 10))

(defn spell-number [n] ; for numbers < 1000
  (let
    [h (hundreds-digit n)
     t (tens-digit n)
     o (ones-digit n)]

    (str (if (not (zero? h))
           (str (ones h) "hundred"
                (if (not= n (* 100 h))
                  "and")
                ))

         (cond
           (zero? t) (ones o) 
           (= 1 t) (teens o)
           (> t 1) (if (zero? o)
             (tens t)
             (str (tens t)  (ones o)))))))




(time (do (defn char-score [c] (- (int c) 64))
(defn word-score [w] (reduce + (map char-score w)))
(defn word-pos-score [[p w]] (* p (word-score w)))

(->>
  (->
    (time (slurp "http://projecteuler.net/project/names.txt"))
    (clojure.string/split #","))
  (map #(.replace % "\"" ""))
  (sort)
  (map vector (range 1 10000))
  (map word-pos-score)
  (reduce +))))

(def problem18-triangle 
[[75]
[95 64]
[17 47 82]
[18 35 87 10]
[20 4 82 47 65]
[19 1 23 75 3 34]
[88 2 77 73 7 63 67]
[99 65 4 28 6 16 70 92]
[41 41 26 56 83 40 80 70 33]
[41 48 72 33 47 32 37 16 94 29]
[53 71 44 65 25 43 91 52 97 51 14]
[70 11 33 28 77 73 17 78 39 68 17 57]
[91 71 52 38 17 14 91 43 58 50 27 29 48]
[63 66 4 68 89 53 67 30 73 16 69 87 40 31]
[4 62 98 27 23 9 70 98 73 93 38 53 60 4 23]])
