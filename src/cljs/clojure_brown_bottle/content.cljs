(ns clojure-brown-bottle.content
  (:use [domina :only [nodes html]]
        [domina.css :only [sel]]))

(def ^:private content-slides-dom (nodes (sel ".slide")))

(defn slide-content [slide-number]
  "Returns the HTML content of the slide, the first slide has slide-number 1"
  (html (nth content-slides-dom (dec slide-number))))

(defn count-slides []
  (count content-slides-dom))
