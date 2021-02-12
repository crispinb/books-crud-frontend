(ns app.core
  (:require [reagent.dom :as rdom]
            [re-frame.core :as rf]
            [app.components.books :refer [books-component]]
            [app.events :refer [register-events]]))

;; TODO: book detail (linked)
;; TODO: delete
;; TODO: add
;; TODO: update



(defn main-component
  []
  [:div {:id :main}
   [books-component @(rf/subscribe [:books-changed])]])

(defn main
  []
  (rdom/render [main-component]
               (.getElementById js/document "reframe-root")))

;; should these be in main?
(register-events)
(rf/dispatch [:get-books])


(comment
  ;;
  )

