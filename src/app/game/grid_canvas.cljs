(ns app.game.grid-canvas
  (:require [oops.core :refer [oset!]]
            [app.game.imgs :as imgs]))

(defn ->grid-canvas [{:keys [grid-width
                             grid-height
                             block-width
                             block-height] :as opts}]
  (assert grid-width)
  (assert grid-height)
  (assert block-width)
  (assert block-height)

  (let [canvas (.getElementById js/document "game-canvas")
        ctx (.getContext canvas "2d")]

    (doto canvas
      (oset! :width grid-width)
      (oset! :height grid-height))

    (merge opts
           {:ctx ctx
            :canvas canvas})))

(defn clear-grid! [{:keys [ctx grid-width grid-height]} color]
  (doto ctx
    (oset! :fillStyle color)
    (.fillRect 0 0 grid-width grid-height)))

(defn fill!
  ([grid-canvas [x y] color]
   (fill! grid-canvas x y color))

  ([{:keys [ctx block-width block-height]} x y color]
   (let [x (* x block-width)
         y (* y block-height)]
     (doto ctx
       (oset! :fillStyle color)
       (.fillRect x y block-width block-height)))))

(defn image!
  ([grid-canvas [x y] color]
   (image! grid-canvas x y color))

  ([{:keys [ctx block-width block-height]} x y {:keys [img scale *loaded?]}]
   (let [x (* x block-width)
         y (* y block-height)

         scale (or scale 1)
         w (* block-width scale)
         h (* block-height scale)

         offset-x (- 0 (/ (- w block-width) 2))
         offset-y (- 0 (/ (- w block-height) 2))

         img (if @*loaded? img imgs/placeholder)]

       (.translate ctx offset-x offset-y)
       (.drawImage ctx img x y w h)
       (.resetTransform ctx))))
