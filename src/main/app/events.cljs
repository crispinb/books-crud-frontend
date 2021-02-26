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
   (fn [_ [_ id]]
     {:fetch {:method :delete
              :url (str  "http://localhost:8080/api/books/" id)
              :timeout 10000
              :mode :cors
              :response-content-types {#"application/.*json" :json}
              :on-success [:get-books]
              :on-failure [:book-delete-failed]}}))
  
  (rf/reg-event-fx
   :add-book
   (fn [_ [_ book]]
     {:fetch {:method :post
              :url "http://localhost:8080/api/books"
              :timeout 10000
              :mode :cors
              :body book
              :request-content-type  :json
              :response-content-types {#"application/.*json" :json}
              :on-success [:books-received]
              :on-failure []}}))

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
 (def testbook {:author "McBurger, Funky"
                :title "Nest IV"
                :publication-date "1999"})
  
  (rf/dispatch 
   [:add-book
    testbook])
  (tap> "test")
  (+ 10 01))
