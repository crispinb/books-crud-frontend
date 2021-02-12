(ns app.core
  (:require  [reagent.core :as r]
             [reagent.dom :as rdom]
             [re-frame.core :as rf]
             [reitit.frontend :as rtf]
             [reitit.frontend.easy :as rte]
             [app.components.books :refer [books-component]]
             [app.events :refer [register-events]]))

;; TODO: book detail (linked via route)
;; TODO: delete
;; TODO: add
;; TODO: update
;; TODO: read https://github.com/jacekschae/conduit for ideas 


(defonce current-match (r/atom nil))
(defn main-component
  []
  [:div {:id :main}
   [books-component @(rf/subscribe [:books-changed])]])

(defn test-component
  []
  [:div [:p "Tested. All good"]])

(defn current-component
  []
  (println "current match: " @current-match)
  [:div {:id "container"}
   [:li [:a {:href (rte/href ::frontpage)} "Home"]]
   [:li [:a {:href (rte/href ::test)} "Test"]]
   (if @current-match
     (do
       (println "Match: " @current-match)
       (let [view [(get-in @current-match [:data :view])]]
         (println "View is: " view)
         view))
     [:p "Something's not working"])])

(def routes
  [["/"
    {:name ::frontpage
     :view #'main-component}]

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
  @current-match
  ;;
  (println "farko"))

