(ns katie-e-vogel-main.core
  (:require [katie-e-vogel-main.player-interaction :refer [click-bnc-button]]
            [katie-e-vogel-main.html :refer [render-bnc]]
            [goog.dom :as gdom]
            [goog.functions :as gfunctions]
            [oops.core :refer [ocall oget oset!]]))


(defonce state (atom {:p1-choice nil
                      :p2-choice nil
                      :winner nil
                      :p1-score 0
                      :p2-score 0}))

;test case for debugging
(comment
  (score :p1 @state))


(defn init-handlers! []
  (let [el (.querySelector js/document ".btn-b")]
    (.addEventListener el "click" (fn [event]
                                    (println "bear" event)
                                    (click-bnc-button state :bear))))
  (let [el (.querySelector js/document ".btn-n")]
    (.addEventListener el "click" (fn [event]
                                    (println "ninja" event)
                                    (click-bnc-button state :ninja))))
  (let [el (.querySelector js/document ".btn-c")]
    (.addEventListener el "click" (fn [event]
                                    (println "cowboy" event)
                                    (click-bnc-button state :cowboy)))))

(defn set-app-html!
  [html-str]
  (let [el (gdom/getElement "appContainer")]
    (oset! el "innerHTML" html-str)
    (init-handlers!)))

(defn render-ui! [_ _ _prev-val new-val]
  (set-app-html! (render-bnc new-val)))

(def init! (gfunctions/once (fn []
                              (add-watch state :render-ui render-ui!)
                              (render-ui! nil nil nil @state)
                              (println "running"))))

(init!)

