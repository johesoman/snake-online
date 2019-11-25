(ns app.main
  (:require [app.game.connection :as connection]
            [app.game.core :as game]
            [app.game.game-loop :as game-loop]
            [snake.core :as snake]))

(defn ^:dev/after-load start! []
  (let [grid-width 800
        grid-height 600
        block-width 50
        block-height 50
        width (/ grid-width block-width)
        height (/ grid-height block-height)

        game (snake/->game {:width width
                            :height height})

        conn (connection/->connection {:game game
                                       :type :offline
                                       :tick snake/tick
                                       :ticks-per-second 4})]

    (game/setup! {:grid-width grid-width
                  :grid-height grid-height
                  :block-width block-width
                  :block-height block-height})

    (game-loop/setup! {:tick game/tick
                       :draw game/draw
                       :input game/input
                       :connection conn})))

(.addEventListener js/document "DOMContentLoaded" start! false)
