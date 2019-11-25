(ns app.game.core
  (:require [app.game.connection :as connection]
            [app.game.grid-canvas :as grid-canvas]
            [app.game.graphics :as graphics]))

(def *prev-state (atom nil))
(def *curr-state (atom nil))

(defn input [])

(defn tick [_ conn]
  (reset! *prev-state @*curr-state)
  (reset! *curr-state (connection/next-state conn)))

(def *grid-canvas (atom nil))

(defn draw [_]
  (let [{:keys [x]} @*curr-state]
    (doto @*grid-canvas
      (grid-canvas/clear-grid! graphics/background-color)
      (grid-canvas/fill! [x 1] (:body-color (get graphics/snakes 0)))
      (grid-canvas/image! [(+ x 1) 1] (:head-img (get graphics/snakes 0)))
      (grid-canvas/image! [(+ x 2) 1] (get graphics/food 0)))))

(defn setup! [opts]
  (reset! *grid-canvas (grid-canvas/->grid-canvas opts)))
