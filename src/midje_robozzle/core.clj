(ns midje_robozzle.core
  (:use [clojure.set]))

(def north :north)
(def south :south)
(def east :east)
(def west :west)

(defn position [heading coords]
  [heading coords])

(defn coords [position]
  (last position))

(defn heading [position]
  (first position))

(defn trail [journey]
  (map coords journey))

(def forward-offsets 
  { :north [0 -1]
   :south [0 1]
   :east [1 0]
   :west [-1 0] })

(defn forward [pos]
  (position (heading pos) 
            (map + 
                 (forward-offsets (heading pos))
                 (coords pos))))

(def left-rotation
  { :north :west
   :south :east
   :east :north
   :west :south})

(defn left [pos]
  (position (left-rotation (heading pos)) (coords pos)))

(def right-rotation
  { :north :east
   :south :west
   :east :south
   :west :north})

(defn right [pos]
  (position (right-rotation (heading pos)) (coords pos)))

(defn journey [start moves]
    (reduce (fn [so-far move] 
                (conj so-far (move (last so-far))))
            [start]
            moves))

(defn win? [start moves stars within]
      (subset?
        (set stars)
        (set (take (inc within) (trail (journey start moves))))))

