(ns clj-pbrt.core
  (:import [mikera.vectorz AVector Vectorz Vector Vector1 Vector2 Vector3 Vector4])
  ; (:import [clj_pbrt.ray Ray]
  ;         [clj_pbrt.bbox BBox])
  (:require [mikera.vectorz.core :as v]
            [clj-pbrt.ray :as ray]
            [clj-pbrt.bbox :as bbox]
            )
  (:gen-class))

(deftype Normal [^Vector3 v])

(defn point
  (^Vector3 [^double x ^double y ^double z]
            (v/vec3 x y z)))

(defn vec3
  (^Vector3 [^double x ^double y ^double z]
            (v/vec3 x y z)))

;; test

(def v1 (vec3 1 2 3))
(def v2 (vec3 2 3 4))
(def v3 (vec3 1 1 1))
(def r1 (ray/ray v1 v2 0 1000))
(def bb1 (bbox/bbox v1 v2))
(def bb2 (bbox/bbox (v/- v1 v3) (v/+ v2 v3)))
