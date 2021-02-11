(ns app.components.books
  (:require [app.components.book :refer [book-row]]))

(defn books-component
  [books]
  [:div {:id :books} 
   [:h2 "Books"]
   (doall
    (for [book books]
      [:div {:id :book-row :key (:id book)}
       [book-row book]]))])