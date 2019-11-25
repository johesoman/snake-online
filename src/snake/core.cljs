(ns snake.core)

(defn ->game [{:keys [width height]}]
  (assert width)
  (assert height)
  {:x 0
   :width width
   :height height})

(defn tick [{:keys [x width] :as state}]
  (merge state
         {:x (mod (inc x) width)}))
