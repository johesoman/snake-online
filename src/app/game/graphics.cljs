(ns app.game.graphics
  (:require [oops.core :refer [oset!]]))

(def background-color "#2f8136")

(defn image [url scale]
  (let [*loaded? (atom false)
        img (new js/Image)]
    (oset! img :onload #(reset! *loaded? true))
    (oset! img :src url)
    {:img img
     :scale scale
     :*loaded? *loaded?}))

(def snake-head-urls
  ["http://raw.githubusercontent.com/EmojiTwo/emojitwo/master/png/1f438.png"
   "http://raw.githubusercontent.com/EmojiTwo/emojitwo/master/png/1f435.png"
   "http://raw.githubusercontent.com/EmojiTwo/emojitwo/master/png/1f43c.png"
   "http://raw.githubusercontent.com/EmojiTwo/emojitwo/master/png/1f42f.png"
   "http://raw.githubusercontent.com/EmojiTwo/emojitwo/master/png/1f436.png"
   "http://raw.githubusercontent.com/EmojiTwo/emojitwo/master/png/1f981.png"
   "http://raw.githubusercontent.com/EmojiTwo/emojitwo/master/png/1f43b.png"
   "http://raw.githubusercontent.com/EmojiTwo/emojitwo/master/png/1f431.png"
   "http://raw.githubusercontent.com/EmojiTwo/emojitwo/master/png/1f417-1f464.png"
   "http://raw.githubusercontent.com/EmojiTwo/emojitwo/master/png/1f437.png"])

(def snake-head-scales
  [1.55
   1.75
   1.65
   1.75
   1.75
   1.75
   1.65
   1.65
   1.75
   1.65])

(def snake-colors
  ["#83bf4f"
   "#89664c"
   "#555e63"
   "#f29a2e"
   "#f5d1ac"
   "#e5bc5e"
   "#947151"
   "#4c5359"
   "#89664c"
   "#fc97b2"])

(def snakes
  (let [imgs (map image
                  snake-head-urls
                  snake-head-scales)]
    (vec (map (fn [img color] {:head-img img
                               :body-color color})
              imgs
              snake-colors))))

(def food-urls
  ["http://raw.githubusercontent.com/EmojiTwo/emojitwo/master/png/1f34e.png"
   "http://raw.githubusercontent.com/EmojiTwo/emojitwo/master/png/1f4a9.png"])

(def food-scales
  [1.4
   1.4])

(def food
  (vec (map image
            food-urls
            food-scales)))
