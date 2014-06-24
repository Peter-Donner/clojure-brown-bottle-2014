(ns clojure-brown-bottle.slide-switcher
  (:require-macros [cljs.core.logic.macros :as m])
  (:use [domina :only [by-id set-html! set-text! toggle-class! value single-node html]]
        [domina.css :only [sel]]
        [clojure-brown-bottle.content :only [slide-content count-slides]]
        [clojure-brown-bottle.model :only [slide-state]]
        [clojure-brown-bottle.progressbar :only [update-progressbar]]
        [cljs.core.logic :only [membero]]))

(def ^:private pointer-left 33)
(def ^:private pointer-right 34)
(def ^:private pointer-blackout 190)
(def ^:private pointer-blackout-on-mac 66)
(def ^:private cursor-left 37)
(def ^:private cursor-right 39)
(def ^:private space-bar 32)
(def ^:private first-slide-number 1)
(def ^:private last-slide-number (count-slides))
(def ^:private main-slide-content-dom (by-id "main-slide-content"))

(defn- decorate-code []
  "Do the thing highlightjs needs"
  (when-let [node (single-node (sel "#main-slide-content code"))]
    (-> js/hljs (.highlightBlock node))))

(defn- update-ui []
  "Refresh the UI with the content of the current slide"
  (set-html! main-slide-content-dom
             (slide-content (:slide-number @slide-state)))
  (decorate-code)
  (update-progressbar))

(defn- switch-slide [f]
  "Switch one page up or down, f is either inc or dec"
  (when-not (:is-blackout @slide-state)
    (swap! slide-state
           #(let [new-value (f (:slide-number %))]
              (if (or (<= new-value (dec first-slide-number))
                      (>= new-value (inc last-slide-number)))
                % ; don't change the value because we hit a border
                (assoc % :slide-number new-value))))
    (update-ui)))

(defn- toggle-blackout []
  "Toggle between slide view and blackout view"
  (swap! slide-state #(assoc % :is-blackout (not (:is-blackout %))))
  (toggle-class! (by-id "slide-container") "hide") 
  (toggle-class! (by-id "blackout-container") "hide")
  (update-ui))

(defn- handle-key [which]
  "Handle key events from the pointer device or keyboard"
  (cond
   (or (= which pointer-left) (= which cursor-left))
   (switch-slide dec)
   (or (= which pointer-right) (= which cursor-right))
   (switch-slide inc)
   (or (= which pointer-blackout)
       (= which pointer-blackout-on-mac)
       (= which space-bar))
   (toggle-blackout)
   :else (.log js/console (str "key: " which))))

(.addEventListener js/window "keydown"
                   #(handle-key (.-which %))
                   false)

(-> js/hljs (.configure (clj->js {:languages ["clojure"]})))
(toggle-blackout)
(.log js/console (str "core.logic " (m/run* [q] (m/== q true))))
