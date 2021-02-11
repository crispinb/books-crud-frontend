(ns app.core
  (:require [reagent.dom :as rdom]
            [re-frame.core :as rf]
            [app.components.books :refer [books-component]]
            [ajax.core :refer [GET]]))

;; TODO: shift to using non-side-effect based get-books event
;;       (as per https://day8.github.io/re-frame/Talking-To-Servers/#version-2)
;; TODO: book detail (linked)
;; TODO: delete
;; TODO: add
;; TODO: update

;; TODO: move events to events.cljs
(rf/reg-event-db
 :get-books
 (fn [db _]
   (GET "http://localhost:8088/api/books"
     {:handler #(rf/dispatch [:books-received %])
      :error-handler #(rf/dispatch [:book-fetch-failed %])
      :response-format :json
      :keywords? true})
   (assoc db :loading? true)))

(rf/reg-event-db
 :books-received
 (fn [db [_ response]]
   (let [books (js->clj response)]
     (println (str "books received: " books))
     (assoc db :loading? false)
   ;; wrong, but will do for a quick ttest
     (assoc db :books books))))

(rf/reg-event-db
 :book-fetch-failed
 (fn [db [_ response]]
   (println (str "Book fetch failed with error" response))))

(rf/reg-sub
 :books-changed
 (fn [db]
   (:books db)))

(defn main-component
  []
  [:div {:id :main}
   [books-component @(rf/subscribe [:books-changed])]])

(defn main
  []
  (js/console.log "dispatching main")
  (rdom/render [main-component]
               (.getElementById js/document "reframe-root")))

(defn init
  []
  (rf/dispatch [:get-books]))


(comment
  (init))
  ;;
  
 