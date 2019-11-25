(ns app.game.connection
  (:require [app.game.metrics :as metrics]))

(defn throw-unknown-type [type origin]
  (throw (js/Error (str "Unknow type " type " @ " origin))))

(defn ready? [{:keys [type *tick-counter tick-length-in-ms *game tick]} dt]
  (if (= type :offline)
    (let [[ms _] (swap-vals! *tick-counter #(+ %1 dt))]
      (when (<= tick-length-in-ms ms)
        (do (reset! metrics/*tick-time ms)
            (reset! *tick-counter (- ms tick-length-in-ms))
            (swap! *game tick)
            true)))
    (throw-unknown-type type
                        "app.game.conncection/ready?")))

(defn next-state [{:keys [*game]}]
  @*game)

(defn ->connection [{:keys [type ticks-per-second game tick]}]
  (if (= type :offline)
    {:tick tick
     :type :offline
     :*game (atom game)
     :*tick-counter (atom 0)
     :tick-length-in-ms (/ 1000 ticks-per-second)}
    (throw-unknown-type type
                        "app.game.connection/->connection")))
