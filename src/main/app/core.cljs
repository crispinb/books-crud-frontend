(ns app.core
  (:require [reagent.dom :as rdom]
            [ajax.core :refer [GET ajax-request json-response-format]]))

;; TODO: replace cljs-ajax (which I can't get to give me real JSON) with re-frame effects
;; TODO: get current books displaying with a ajax call, without re-frame or separation
;; TODO: get backend running from commandline
;; TODO: add re-frame subs & events (ie. use ajax effects rather than direct ajax)
;; TODO: separate into components

(defn main-component
  []
  [:p "we're done"])

(defn main
  []
  (js/console.log "dispatching main")
  (rdom/render [main-component]
               (.getElementById js/document "reframe-root")))

(comment

  ;;
  )
