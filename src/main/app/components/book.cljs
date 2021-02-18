(ns app.components.book
  (:require [reitit.frontend.easy :as rte]
            [re-frame.core :as rf]
            ))

;; ie row in the main (book list) component
(defn book-summary-component
  [{:keys [id author title publication-date]}]
  [:span   author ", " 
  [:a {:href (rte/href :app.core/book-detail {:id id}) }  [:i  title]]
   " (" publication-date ")    " 
   [:button  {:type :button 
              :on-click (fn [e] (.preventDefault e) (rf/dispatch [:delete-book ]))}  "❌"]])

(defn book-detail-component
  [book]
  (tap> "Got this book: ") (tap> book)
  [:div {:id :book-detail}
   [:p (:author book)]
   [:p [:i (:title book)]]
   [:p (str "("  (:publication-date book) ")")]])
