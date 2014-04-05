(ns clj-duplicate.core
  (:use mikera.image.core
        mikera.image.filters) 
  (:require [clojure.java.io :refer [resource]]))

(defn load-picture
  [filename]
  (load-image filename))

(defn grey-scale
  [picture]
  (filter-image (grayscale) picture))

(defn resize
  [picture width height]
  (scale-image picture width height))

(defn dhash-part [pixels]
  (reduce #(+ (bit-shift-left %1 1) %2)
          0
          (map #(if (> (nth pixels %) (nth pixels (inc %)))
                  1
                  0)
               (range 8))))

(defn dhash-binary
  [picture]
  (map #(dhash-part %)
       (partition-all 9
                      (map #(bit-and 0xff %)
                           (get-pixels picture)))))

(defn dhash
  [picture]
  (reduce #(format "%s%x" %1 %2)
            ""
            (dhash-binary picture)))
          
(defn generate-dhash
  [filename]
  (-> filename
      load-picture
      (resize 9 8)
      dhash))

