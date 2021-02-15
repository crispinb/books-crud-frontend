(ns app.components.book
  (:require [reitit.frontend.easy :as rte]
            ))

;; ie row in the main (book list) component
(defn book-summary-component
  [{:keys [id author title publication-date]}]
  [:span   author ", " 
  [:a {:href (rte/href :app.core/book-detail {:id id})}  [:i  title]]
   " (" publication-date ")"])

(defn book-detail-component
  [book]
  (println "Got this book: " book)
  [:div {:id :book-detail}
   [:span [:p "Some book detail or other"]]])
