(ns clojure-brown-bottle.demo-repl
  (:require [clojure.browser.repl]))

(defn hello []
  (js/alert "hello"))

(defn whoami []
  (.-userAgent js/navigator))
