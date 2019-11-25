(ns app.game.metrics
  (:require [oops.core :refer [oset!]]))

(def *tick-time (atom nil))
(def *delta-time (atom nil))

(defn set-inner-html [id & values]
  (oset! (.getElementById js/document id)
         :innerHTML
         (apply str values)))

(js/setInterval #(set-inner-html "tps-value"
                                 (.toFixed (/ 1000 @*tick-time) 3))
                250)

(js/setInterval #(set-inner-html "fps-value"
                                 (.toFixed (/ 1000 @*delta-time) 2))
                250)
