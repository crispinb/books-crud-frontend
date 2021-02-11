(ns app.core
  (:require [reagent.dom :as rdom]
            [re-frame.core :as rf]
            [app.components.books :refer [books-component]]))

;; TODO: replace with data from backend (https://day8.github.io/re-frame/Talking-To-Servers/#version-2)
;;       - try both their approaches from that article. Note they're using direct js->clj on the ajax response. Perhaps I was wrong to think the JSON comes back in the response as a string? Maybe it's a js object already and that's why I got the errors tryign js/JSON.parse??
;; TODO: book detail (linked)
;; TODO: delete
;; TODO: add
;; TODO: update

(def books [{:id 2 :title "fark"  :author "tolstoy"  :publication-date "2020"},
            {:id 3 :title "another book"  :author "tolstoy"  :publication-date "2020"}
            {:id 4 :title "yeranuvva book"  :author "tolstoy"  :publication-date "2020"}])

(rf/reg-event-db
 :get-books
 (fn [db]
   (println "get-books dispatched")
   (assoc db :books books)))

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
  
 