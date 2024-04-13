;hard PowLog modification
(defn constant [x] (fn [m] (identity x)))
(defn variable [s] (fn [m] (m s)))
(defn operation [f] (fn [& args] (fn [m] (apply f (mapv #(% m) args)))))
(def add (operation +))
(def subtract (operation -))
(def multiply (operation *))
(defn div ([arg] (/ 1 (double arg)))
  ([first & rest] (reduce #(/ (double %1) (double %2)) first rest)))
(def divide (operation div))
(def negate (operation -))

(defn abs [n] (max n (- n)))

(def log (operation #(/ (Math/log (abs %2)) (Math/log (abs %1)))))
(def pow (operation #(Math/pow %1 %2)))


(def operations {'+ add '- subtract '* multiply '/ divide 'negate negate 'pow pow 'log log})
(defn parseFunction [input]
  (letfn [(parseExpression [tokens] (cond
                                      (number? tokens) (constant tokens)
                                      (symbol? tokens) (variable (str tokens))
                                      :else (apply (operations (first tokens)) (mapv parseExpression (rest tokens)))))]
    (parseExpression (read-string input))))


(load-file "proto.clj")



(def _value (field :value))
(def _s (field :s))
(def _op (field :op))
(def _name (field :name))
(def _diffop (field :diffop))
(def _operands (field :operands))
(def evaluate (method :evaluate))
(def toString (method :toString))
(def toStringSuffix (method :toStringSuffix))
(def diff (method :diff))

(declare Constant)
(def ConstantPrototype
  {:evaluate       (fn [this args] (_value this))
   :toString       (fn [this] (str (_value this)))
   :toStringSuffix (fn [this] (str (_value this)))
   :diff           (fn [this diffvar] (Constant 0))})
(defn ConstantConstructor [this value]
  (assoc this
    :value value))
(def Constant (constructor ConstantConstructor ConstantPrototype))

(def VariablePrototype
  {
   :evaluate       (fn [this m] (m (clojure.string/lower-case (first (_s this)))))
   :toString       (fn [this] (_s this))
   :toStringSuffix (fn [this] (_s this))
   :diff           (fn [this diffvar] (cond
                                        (= diffvar (clojure.string/lower-case (first(_s this)))) (Constant 1)
                                        :else (Constant 0)))})
(defn VariableConstructor [this s]
  (assoc this
    :s s))
(def Variable (constructor VariableConstructor VariablePrototype))


(def AbstractOperationPrototype
  {:evaluate       (fn [this args] (apply (_op this) (mapv #(evaluate % args) (_operands this))))
   :toString       (fn [this] (str "(" (_name this) " " (clojure.string/join " " (mapv toString (_operands this))) ")"))
   :toStringSuffix (fn [this] (str "(" (clojure.string/join " " (mapv toStringSuffix (_operands this))) " " (_name this) ")"))
   :diff           (fn [this diffvar] ((_diffop this) (_operands this) (mapv #(diff % diffvar) (_operands this))))})

(defn OperationConstructor [this & operands]
  (assoc this
    :operands operands))
(defn AbstractOperationConstructor [this op name diffop]
  (assoc this
    :op op
    :name name
    :diffop diffop))
(defn createOperation [f name diffop]
  (constructor OperationConstructor
               ((constructor AbstractOperationConstructor AbstractOperationPrototype) f name diffop)))

(declare Abs)

(def LogPrototype
   {:evaluate       (fn [this args] (apply (_op this) (mapv #(evaluate % args) (_operands this))))
    :toString       (fn [this] (str "(" (_name this) " " (clojure.string/join " " (mapv toString (_operands this))) ")"))
    :toStringSuffix (fn [this] (str "(" (clojure.string/join " " (mapv toStringSuffix (_operands this))) " " (_name this) ")"))
    :diff           (fn [this diffvar] ((_diffop this) (mapv Abs (_operands this)) (mapv #(diff % diffvar) (mapv Abs (_operands this)))))})

(defn LogConstructor [this s]
  (assoc this
    :s s))
(def Log (constructor LogConstructor LogPrototype))

(def Add (createOperation + "+" #(apply Add %2)))
(def Subtract (createOperation - "-" #(apply Subtract %2)))
(def Negate (createOperation - "negate" #(apply Negate %2)))
(def Abs (createOperation abs "abs" #(apply Abs %2)))


(declare Multiply Divide)

(defn diffFunc [a da f] (second (reduce f (mapv vector a da))))
(def f #(Multiply (nth %1 %3) (nth %2 %4)))
(defn diffMultiply [a da] (diffFunc a da (fn [a b] [(f a b 0 0)
                                                              (Add (f a b 1 0) (f a b 0 1))])))
(def Multiply (createOperation * "*" (fn [a da] (diffMultiply a da))))

(defn diffBinaryDivide [a b] [(Divide (nth a 0) (nth b 0)) (Divide (Subtract (f a b 1 0) (f a b 0 1)) (f b b 0 0))])

(defn diffDivide [a da] (let [[b db] (cond
                                       (< 1 (count a)) [a da]
                                       :else [(apply conj [(Constant 1)] a) (apply conj [(Constant 0)] da)])]
                          (diffFunc b db diffBinaryDivide)))

(def Divide (createOperation div "/" diffDivide))

(declare Pow Log)
(defn diffLn [a da] (Multiply (Divide (Constant 1) a)  da))
(defn diffLog [[a b] [da db]] (diffDivide [(Log (Constant (Math/E)) b) (Log (Constant (Math/E)) a)]
                                          [(diffLn b db) (diffLn a da)]))
(def Log (createOperation (fn [& args] (reduce #(/ (Math/log (abs %2)) (Math/log (abs %1))) args)) "log" diffLog))

(defn diffBinaryPow [[a b] [da db]] (Multiply (Pow a b) (diffMultiply [(Log (Constant (Math/E)) a) b]
                                                  [(diffLn a da) db])))
(def Pow (createOperation (fn [& args] (reduce #(Math/pow %1 %2) args)) "pow" diffBinaryPow))

(declare Sumexp)
(defn diffExp [a b] (Multiply (Sumexp a) b))
(def diffSumexp (fn [a da] (apply Add (mapv diffExp a da))))
(def Sumexp (createOperation (fn [& args] (apply + (mapv #(Math/exp %) args))) "sumexp" diffSumexp))
(def Softmax (createOperation (fn [& args] (/ (Math/exp (first args)) (apply + (mapv #(Math/exp %) args)))) "softmax"
                              (fn [a da] (diffDivide [(Sumexp (first a)) (apply Sumexp a)]
                                                     [(diffExp (first a) (first da)) (diffSumexp a da)]))))

(def objects {'+ Add '- Subtract '* Multiply '/ Divide 'negate Negate 'sumexp Sumexp
              'softmax Softmax 'pow Pow 'log Log 'abs Abs})

(defn parseObject [input]
  (letfn [(parseExpression [tokens] (cond
                                      (number? tokens) (Constant tokens)
                                      (symbol? tokens) (Variable (str tokens))
                                      :else (apply (objects (first tokens)) (mapv parseExpression (rest tokens)))))]
    (parseExpression (read-string input))))


(load-file "parser.clj")

(defn bitOp [f] (fn [& a] (Double/longBitsToDouble (apply f (mapv #(Double/doubleToLongBits %) a)))))
(def BitAnd (createOperation (bitOp bit-and) "&" nil))
(def BitOr (createOperation (bitOp bit-or) "|" nil))
(def BitXor (createOperation (bitOp bit-xor) "^" nil))
(def objectOperations {"+" Add "-" Subtract "*" Multiply "/" Divide "negate" Negate "&" BitAnd "|" BitOr "^" BitXor})

(defn sign [s tail]
  (if (= \- s)
    (cons s tail)
    tail))

(def parseObjectSuffix
  (let
    [*digit (+char ".0123456789")
     *constant (+map #(Constant (read-string %)) (+str (+seqf sign (+opt (+char "-")) (+plus *digit))))
     *variable (+map #(Variable %) (+str (+plus (+char "xyzXYZ"))))
     *negate (apply +seqf str (mapv #(+char (str %)) "negate"))
     *space (+char " \t\n\r")
     *ws (+ignore (+star *space))
     *operation (+or (+char "+-*/&|^") *negate)]
    (letfn [(*value [] (delay (+or *constant *variable *operation (*expression (*value)))))
            (*expression [p]
            (+map #(apply (objectOperations (str (last %))) (butlast %))
                  (+seqn 1
                         (+char "(")
                         (+opt (+seqf cons *ws p (+star (+seqf identity *ws p))))
                         *ws
                         (+char ")"))))]
      (+parser (+seqn 0 *ws (*value) *ws)))))
