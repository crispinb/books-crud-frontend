(ns app.subscriptions
  (:require [re-frame.core :as rf]))

(defn register-subscriptions
  []
  (rf/reg-sub
   :books-changed
   (fn [db]
     (:books db)))

  (rf/reg-sub
   :book-by-id
   (fn []
     (rf/subscribe [:books-changed]))
   (fn [books [_ id]]
     (println " books, id: " books ":" id)
     (first (filter #(= id (:id %)) books )))))

(comment
  (first (filter #(= 4 (:id %)) [{:a 1 :b 2 :id 3} {:a 1 :b 2 :id 4}]))

  , )