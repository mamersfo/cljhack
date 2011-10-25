(ns cljhack.test.core
  (:use [cljhack.core])
  (:use [clojure.test]))

(deftest test-fahrenheit-to-celsius
  (is (= (celsius-to-fahrenheit -32) -26))
  (is (= (celsius-to-fahrenheit 0) 32))
  (is (= (celsius-to-fahrenheit 32) 90)))
