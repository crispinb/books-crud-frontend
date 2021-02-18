(ns app.components.books
  (:require [app.components.book :refer [book-summary-component]]))

(defn books-component
  [books]
  (tap> "Books map: ") (tap> books)(tap> "(those were hte books)")
  [:div {:id :books}
   [:h2 "Books"]
   (doall
    (for [book books]
      [:div {:id :book-row :key (:id book)}
       [book-summary-component book]]))])