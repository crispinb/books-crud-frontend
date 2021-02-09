(ns app.core
  (:require [reagent.dom :as rdom]))

(defn main-component 
  []
  [:p "we're fone"])

(defn main
  []
  (js/console.log "dispatching main")
  (rdom/render [main-component]
               (.getElementById js/document "reframe-root")))

(comment 
  (js/console.log "fark"))
