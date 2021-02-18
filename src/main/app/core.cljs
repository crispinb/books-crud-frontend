(ns app.core
  (:require  [reagent.core :as r]
             [reagent.dom :as rdom]
             [re-frame.core :as rf]
             [reitit.frontend :as rtf]
             [reitit.frontend.easy :as rtfe]
             [app.components.books :refer [books-component]]
             [app.components.book :refer [book-detail-component]]
             [app.events :refer [register-events]]
             [app.subscriptions :refer [register-subscriptions]]))


;; For the next 2 create a flip from book outline detail to form.
;; TODO: add
;; TODO: update
;; ;; TODO: convert to proper re-frame approach to loading routes (ie. via events/subs)
;;        https://gist.github.com/vharmain/a8bbfa5bc601feba0f421959228139a1
;; TODO: read https://github.com/jacekschae/conduit for ideas 
;; TODO: is there a way to get automatic callbacks when db changes? eg.
;;      - hook into  POSTS, OR set a db trigger
;;      - then send something (event? websocket?) back to the client


(defonce current-match (r/atom nil))

(defn main-component
  [_]
  [:div {:id :main}
   [books-component @(rf/subscribe [:books-changed])]])

(defn book-detail
  [match]
  (tap>  "match for detail: ") (tap>  match)
  [book-detail-component @(rf/subscribe [:book-by-id  (js/parseInt (get-in match [:path-params :id]))])])


(defn current-component
  [_]
  [:div {:id "container"}
   (if @current-match
     (let [view  (get-in @current-match [:data :view])]
       [view @current-match])
     [:p "Something's not working (not found? client-side '404'?)"])
   [:p]
   [:hr]
   [:li [:a {:href (rtfe/href ::frontpage)} "Home"]]])

(def routes
  [["/"
    {:name ::frontpage
     :view #'main-component}]
   ;; there must be a way of aliasing 2 routes to the same data?
   ["/books"
    {:name ::books
     :view #'main-component}]
   ["/books/:id"
    {:name ::book-detail
     :view #'book-detail
     :parameters {:path {:id int?}}}]])

(defn main
  []
  (rtfe/start!  (rtf/router  routes)
                (fn [match]    (reset! current-match match))
                {:use-fragment false})
  (rdom/render [current-component]
               (.getElementById js/document "reframe-root")))

(register-subscriptions)
(register-events)
(rf/dispatch [:get-books])


(comment
  (println "test console log")
  (tap> "test again like we did last summer")
  ::book-detail
  (tap> {:a 1 :b 2 :c 3})
  (->  {:a 1 :b 2 :c {:a 1 :b 2 :c 3}} :c :a)
  (get-in {:a 1 :b 2 :c {:a 1 :b 2 :c 3}} [:c :a])
  @current-match
  (println "fark")
  (println "farko")
  (js/parseInt  "10")
  ((fn [] println "fark")))

