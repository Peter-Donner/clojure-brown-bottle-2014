(ns clojure-brown-bottle.model)

(def slide-state
  (atom {:is-blackout true
         :slide-number 1
         :presentation-start-time (.getTime (js/Date.))
         :presentation-run-time 60}))
