(ns app.events
  (:require [re-frame.core :as rf]
            [ajax.core :as ajax]
            [day8.re-frame.http-fx]))

;; One file's enough for this simple app

;; how would this typically be done?
(defn register-events
  []
  (rf/reg-event-fx
   :get-books
   (fn [{db :db}]
     {:http-xhrio {:method :get
                   :uri "http://localhost:8080/api/books"
                   :response-format (ajax/json-response-format {:keywords? true})
                   :on-success [:books-received]
                   :on-failure [:book-fetch-failed]}}))

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

  )
