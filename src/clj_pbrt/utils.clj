(ns clj-pbrt.utils
  (:import [mikera.vectorz AVector Vectorz Vector Vector1 Vector2 Vector3 Vector4])
  (:require [mikera.vectorz.core :as v])
  (:gen-class))

(defn vzip [f ^Vector3 v1 ^Vector v2]
  (v/vec3 (f (.x v1) (.x v2))
          (f (.y v1) (.y v2))
          (f (.z v1) (.z v2))))

(defn lerp
  (^double [^double t ^double start ^double end]
           (+ start (* t (- end start)))))
  
