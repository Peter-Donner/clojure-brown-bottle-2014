(defproject clojure-brown-bottle "0.1.0-SNAPSHOT"
  :description "Clojure Brown Bottle"
  :url "https://github.com/Peter-Donner/clojure-brown-bottle-2014"
  :plugins [[lein-cljsbuild "0.3.2"]
            [lein-haml-sass "0.2.7-SNAPSHOT"]
            [com.cemerick/austin "0.1.3"]]
  :cljsbuild {:builds
              [{:source-paths ["src/cljs"]
                :compiler {:output-to "target/classes/public/clojure-brown-bottle-app.js"
                           :optimizations :whitespace
                           :pretty-print true}}]}
  :source-paths ["src/clj"]
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2156"]
                 [compojure "1.1.0"]
                 [ring/ring-jetty-adapter "1.1.1"]
                 [domina "1.0.2"]
                 [net.drib/strokes "0.5.1"]
                 [org.clojure/core.logic "0.8.7"]]
  :scss {:src "resources/scss"
         :output-directory "resources/public/css"
         :output-extension "css"}
  :haml {:src "resources/haml"
         :output-directory "resources/public"}
  :main clojure-brown-bottle.server)
