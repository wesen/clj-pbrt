(ns clj-pbrt.ray
  (:import [mikera.vectorz AVector Vectorz Vector Vector1 Vector2 Vector3 Vector4])
  (:require [mikera.vectorz.core :as v])
  (:gen-class))

(deftype Ray [^Vector3 o ; origin
              ^Vector3 d ; direction
              mint maxt]
  Object
  (toString [ray]
    (str o " -> " d ", " mint "-" maxt))
  )

(defn ray [^Vector3 o ^Vector3 d ^double mint ^double maxt]
  (Ray. o d mint maxt))

(defn at [ray t]
  (v/+ (v/scale (.-d ray) t) (.-o ray)))
