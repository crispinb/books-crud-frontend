(ns app.events
  (:require [re-frame.core :as rf]
            [superstructor.re-frame.fetch-fx]))

;; One file's enough for this simple app

;; how would this typically be done?
(defn register-events
  []
  (rf/reg-event-fx
   :get-books
   (fn [_]
     (tap>  "fetching books ..")
     {:fetch {:method :get
              :url "http://localhost:8080/api/books"
              :mode :cors
              :response-content-types {#"application/.*json" :json}
              :on-success [:books-received]
              :on-failure [:book-fetch-failed]}}))

  (rf/reg-event-fx
   :delete-book
   (fn [_]
     {:fetch {:method :delete
              :url "http://localhost:8080/api/books/100"
              :timeout 2000
              :mode :cors
              :response-content-types {"application/json" :json}
              :on-success [:books-received]
              :on-failure [:book-delete-failed]}}))

  (rf/reg-event-db
   :books-received
   (fn [db [_ response]]
   (tap> "Books received!\nResponse:")(tap> response)
     (let [books (:body  (js->clj response))]
       (-> db
           (assoc :loading? false)
           (assoc :books books)))))

  (rf/reg-event-db
   :book-fetch-failed
   (fn [db [_ response]]
     (tap>  "Book fetch failed with error") (tap>  response)))

  (rf/reg-event-db
   :book-delete-failed
   (fn [_ [_ response]]
     (tap> "Book delete failed with error")
     (tap> response))))

(comment 
  (tap> "test")
  (+ 10 01))
