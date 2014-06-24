(ns clojure-brown-bottle.progressbar
  (:use [domina :only [by-id set-html! set-text! set-styles! toggle-class! value single-node html]]
        [domina.css :only [sel]]
        [clojure-brown-bottle.content :only [slide-content count-slides]]
        [clojure-brown-bottle.model :only [slide-state]]))

(defn- presentation-minutes []
  (/ (- (.getTime (js/Date.)) (:presentation-start-time @slide-state)) (* 60 1000)))

(defn- percent-values []
  (let [slide-value (* (/ 100 (count-slides)) (:slide-number @slide-state))
        time-value (* (/ 100 (:presentation-run-time @slide-state)) (presentation-minutes))]
    {:slide-value (min 100 slide-value)
     :time-value (min 100 time-value)}))

(defn update-progressbar []
  (let [values (percent-values)]
    (if (> (:time-value values) (:slide-value values))
      (do
        (set-styles! (sel ".progress-bar-default") {:width (str (:slide-value values) "%")})
        (set-styles! (sel ".progress-bar-success") {:width "0%"})
        (set-styles! (sel ".progress-bar-danger") {:width (str (- (:time-value values) (:slide-value values)) "%")}))
      (do
        (set-styles! (sel ".progress-bar-default") {:width (str (:time-value values) "%")})
        (set-styles! (sel ".progress-bar-success") {:width (str (- (:slide-value values) (:time-value values)) "%")})
        (set-styles! (sel ".progress-bar-danger") {:width "0%"})))))

(js/setInterval update-progressbar 10000)
