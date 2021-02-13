(ns app.core
  (:require  [reagent.core :as r]
             [reagent.dom :as rdom]
             [re-frame.core :as rf]
             [reitit.frontend :as rtf]
             [reitit.frontend.easy :as rte]
             [app.components.books :refer [books-component]]
             [app.components.book :refer [book-detail-component]]
             [app.events :refer [register-events]]))

;; TODO: wire up book detail component to rf/subscription - presumably via a new single-book subs 
;;      (see  :vehicle-by-id in Normand's)
;; TODO: convert to proper re-frame approach to loading routes (ie. via events/subs)
;;        https://gist.github.com/vharmain/a8bbfa5bc601feba0f421959228139a1
;; TODO: delete
;; TODO: add
;; TODO: update
;; TODO: read https://github.com/jacekschae/conduit for ideas 
;; TODO: is there a way to get automatic callbacks when db changes? eg.
;;      - hook into  POSTS, OR set a db trigger
;;      - then send something (event? websocket?) back to the client

(defonce current-match (r/atom nil))
(defn main-component
  [& args]
  (println "main got passed " args)
  [:div {:id :main}
   [books-component @(rf/subscribe [:books-changed])]])

(defn test-component
  [_]
  [:div [:p "Tested. All good"]])

(defn current-component
  [_]
  [:div {:id "container"}
   [:li [:a {:href (rte/href ::frontpage)} "Home"]]
   [:li [:a {:href (rte/href ::test)} "Test"]]
   (if @current-match
     (let [view  (get-in @current-match [:data :view])]
         [view @current-match])
     [:p "Something's not working"])])

(def routes
  [["/"
    {:name ::frontpage
     :view #'main-component}]
   ;; TODO: 'books' as an aliaas for /?
   ["/books/:id"
    {:name ::book-detail
     :view #'book-detail-component
     :parameters {:path {:id int?}}}]
     ;; TODO: now what to do with the id param?}]

   ["/test"
    {:name ::test
     :view #'test-component}]])

(defn main
  []
  (rte/start!  (rtf/router  routes)
               (fn [match]    (reset! current-match match))
               {:use-fragment false})
  (rdom/render [current-component]
               (.getElementById js/document "reframe-root")))

;; should these be in main?
(register-events)
(rf/dispatch [:get-books])


(comment
  ::book-detail
  @current-match
  ;;
  (println "farko"))

