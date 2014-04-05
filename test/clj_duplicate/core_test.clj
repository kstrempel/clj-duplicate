(ns clj-duplicate.core-test
  (:require [clojure.test :refer :all]
            [clj-duplicate.core :refer :all]))

(def picture_a "cat_grumpy_orig.png")
(def picture_b "cat_grumpy_modif.png")
(def picture_contact "contact.png")
(def picture_superman "superman.png")

(defn compare_pictures [a b]
  (let [hasha (generate-dhash a)
        hashb (generate-dhash b)]
    [hasha hashb]))

(deftest compare-same-pictures_a
  (testing "Both pictures are equal"
    (let [hashes (compare_pictures picture_a picture_a)]
    (is (apply = hashes)))))

(deftest compare-same-pictures_b
  (testing "Both pictures are equal"
    (let [hashes (compare_pictures picture_b picture_b)]
    (is (apply = hashes)))))

(deftest compare-example-pictures
  (testing "Pictures with small difference"
    (let [hashes (compare_pictures picture_a picture_b)]
    (is (apply = hashes)))))

(deftest compare-google-drive-soundcloud-pictures
  (testing "Pictures with big difference"
    (let [hashes (compare_pictures picture_contact picture_superman)]
    (is (not (apply = hashes))))))
