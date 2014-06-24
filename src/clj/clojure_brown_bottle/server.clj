(ns clojure-brown-bottle.server
  (:require [cemerick.austin.repls :refer (browser-connected-repl-js)]
            [compojure.route :refer (resources)]
            [compojure.core :refer (GET defroutes)]  
            ring.adapter.jetty
            [clojure.java.io :as io]))

(defroutes site
  (GET "/repl.js" [] (str (browser-connected-repl-js)))
  (resources "/"))

(defn create-repl-env []
  (reset! cemerick.austin.repls/browser-repl-env
          (cemerick.austin/repl-env)))

(defn run []
  (defonce ^:private server
    (ring.adapter.jetty/run-jetty #'site {:port 4000 :join? false})))

(defn cljs-repl []
  (run)
  (cemerick.austin.repls/cljs-repl (create-repl-env)))
