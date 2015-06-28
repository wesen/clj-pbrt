(ns clj-pbrt.bbox
  (:import [mikera.vectorz AVector Vectorz Vector Vector1 Vector2 Vector3 Vector4])
  (:require [mikera.vectorz.core :as v]
            [clj-pbrt.utils :as u]
            )
  (:gen-class))

(deftype BBox [^Vector3 min
               ^Vector3 max]

  Object
  (toString [bb]
    (str min ", " max)))

(defn bbox
  "Build a bbox from a point or 2 points"
  (^BBox [^Vector3 p]
         (BBox. p p))
  (^BBox [^Vector3 p1 ^Vector3 p2]
         (BBox. (u/vzip min p1 p2)
                (u/vzip max p1 p2))))

(defn union-p
  "Merge a bbox and a p, returning a new bbox"
  (^BBox [^BBox bb ^Vector3 p]
         (BBox. (u/vzip min (.-min bb) p)
                (u/vzip max (.-max bb) p))))

(defn union-bb
  "Merge 2 bboxes"
  (^BBox [^BBox bb1 ^BBox bb2]
         (BBox. (u/vzip min (.-min bb1) (.-min bb2))
                (u/vzip max (.-max bb1) (.-max bb2)))))

(defn overlaps?
  "Check if the bboxes overlap"
  [^BBox bb1 ^BBox bb2]
  (let [max1 (.-max bb1)
        max2 (.-max bb2)
        min1 (.-min bb1)
        min2 (.-min bb2)]
    (and (and (>= (.-x max1) (.-x min2))
              (<= (.-x min1) (.-x max2)))
         (and (>= (.-y max1) (.-y min2))
              (<= (.-y min1) (.-y max2)))
         (and (>= (.-z max1) (.-z min2))
              (<= (.-z min1) (.-z max2))))))

(defn inside?
  "Check if a point is located inside the bbox"
  [^BBox bb ^Vector3 p]
  (let [min (.-min bb)
        max (.-max bb)
        x (.-x p) y (.-y p) z (.-z p)]
    (and (>= x (.-x min)) (<= x (.-x max))
         (>= y (.-y min)) (<= y (.-y max))
         (>= z (.-z min)) (<= z (.-z max)))))

(defn expand 
  "Pad a bounding box by a constant factor"
  (^BBox [^BBox bb ^double delta]
         (bbox (v/+ (.-min bb) (v/vec3 (repeat 3 (- delta))))
               (v/+ (.-max bb) (v/vec3 (repeat 3 delta))))))

(defn diag
  "Returns the diagonal of the bbox"
  (^Vector3 [^BBox bb]
            (v/- (.-max bb) (.-min bb))))

(defn surface-area
  "Returns the surface area of the 6 faces of the bbox"
  (^double [^BBox bb]
           (let [[x y z] (seq (diag bb))]
             (* 2 (+ (* x y)
                     (* x z)
                     (* y z))))))
    
(defn volume
  "Returns the volume of the bbox"
  (^double [^BBox bb]
           (apply * (diag bb))))
  
(defn max-extent
  "Returns which of the axes is the longest"
  [^BBox bb]
  (let [[x y z] (seq (diag bb))]
    (cond (and (> x y) (> x z)) :x
          (> y z)               :y
          :default              :z)))
(defn lerp
  "Interpolate between the corners of the bbox"
  (^Vector3 [^BBox bb ^double tx ^double ty ^double tz]
            (let [min (.-min bb)
                  max (.-max bb)]
              (v/vec3 (u/lerp tx (.-x min) (.-x max))
                      (u/lerp ty (.-y min) (.-y max))
                      (u/lerp tz (.-z min) (.-z max))))))

(defn offset
  "Returns the position of a point relative to the corners of the box,
  where a point at the minimum corner has offset (0,0,0), a point at
  the maximum corner has offset (1,1,1)"
  (^Vector3 [^BBox bb ^Vector3 p]
            (v/div (v/- p (.-min bb)) (diag bb))))

(defn bounding-sphere
  "Returns a sphere encompassing the bbox"
  [^BBox bb]
  (let [c (v/scale (v/+ (.-min bb) (.-max bb)) 0.5)]
    {:center c
     :rad (if (inside? bb c)
            (v/distance c (.-max bb))
            0.0)}))
