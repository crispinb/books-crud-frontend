(ns app.core
  (:require [reagent.dom :as rdom]
            [re-frame.core :as rf]
            [day8.re-frame.http-fx]
            [app.components.books :refer [books-component]]
            [ajax.core :as ajax]))

;; TODO: get the book page to refresh!
;; TODO: tidy up, put events in rght plac, etc.
;; TODO: book detail (linked)
;; TODO: delete
;; TODO: add
;; TODO: update

;; TODO: move events to events.cljs
(rf/reg-event-fx
 :get-books
 (fn [{db :db}]
   {:http-xhrio {:method :get
                 :uri "http://localhost:8088/api/books"
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success [:books-received]
                 :on-failure [:book-fetch-failed]
                 }}
   ))

(rf/reg-event-db
 :books-received
 (fn [db [_ response]]
   (let [books (js->clj response)]
     (-> db
         (assoc :loading? false)
         (assoc :books books)))))

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

(rf/dispatch [:get-books])


(comment
  (init))
  ;;

