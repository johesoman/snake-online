(ns app.game.game-loop
  (:require [app.game.connection :as connection]
            [app.game.metrics :as metrics]))

(def *tick (atom nil))
(def *draw (atom nil))
(def *input (atom nil))
(def *running (atom true))
(def *connection (atom nil))

(declare next-iteration)

(defn game-loop [prev-ms curr-ms]
  (when @*running
    (let [dt (- curr-ms prev-ms)]
      (reset! metrics/*delta-time dt)
      (@*input)
      (when (connection/ready? @*connection dt)
        (@*tick dt @*connection))
      (@*draw dt)
      (next-iteration curr-ms))))

(defn next-iteration
  ([] (next-iteration (.now js/performance)))
  ([curr-ms]
   (.requestAnimationFrame js/window
                           (fn [next-ms]
                             (game-loop curr-ms
                                        next-ms)))))

(defn pause! []
  (reset! *running false))

(defn play! []
  (reset! *running true)
  (next-iteration))

(defn setup! [{:keys [tick draw input connection]}]
  (reset! *tick tick)
  (reset! *draw draw)
  (reset! *input input)
  (reset! *connection connection)
  (next-iteration))
