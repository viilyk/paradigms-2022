;(ns linear)

;(defn myscalar [x] (x))
;(defn myvector [& args] (apply vector args))
;(defn mymatrix [& args] (apply myvector args))

;(defn operation [f] (fn [& args]  (apply mapv f args)))

;(def v+ (operation +))
;(def v- (operation -))
;(def v* (operation *))
;(def vd (operation /))
;(def m+ (operation v+))
;(def m- (operation v-))
;(def m* (operation v*))
;(def md (operation vd))

(defn op [f] (fn [& args]
               (cond
                 (every? number? args) (apply f args)
                 :else (apply mapv (op f) args))))

(def s+ (op +))
(def s- (op -))
(def s* (op *))
(def sd (op /))
(def v+ s+)
(def v- s-)
(def v* s*)
(def vd sd)
(def m+ s+)
(def m- s-)
(def m* s*)
(def md sd)

(defn scalar [& args] (reduce + (apply v* args)))
(defn vect [& args]
  (letfn [(f [x y] (nth (nth args x) y))
          (g [x1 y1 x2 y2] (* (f x1 y1) (f x2 y2)))
          (h [& args] (apply #(- (g %1 %2 %3 %4) (g %5 %6 %7 %8)) args))]
  (vector (h 0 1 1 2 0 2 1 1)
                       (h 0 2 1 0 0 0 1 2)
                       (h 0 0 1 1 0 1 1 0))))
(defn v*s [args s] (mapv #(* % s) args))
(defn m*s [m s] (mapv #(v*s % s) m))
(defn m*v [m v] (mapv #(scalar v %) m))
(defn transpose [m] (apply mapv vector m))
(defn m*m [m1 m2] (mapv #(m*v (transpose m2) %) m1))



