(ns pinocchio-monitor.views
  (:require [re-frame.core :as re-frame]
            [re-com.core :as rc]))

(defn listen [s]
  @(re-frame/subscribe s))

(defn connection-screen []
  (let [url (atom "ws://localhost:8181/ws")]
   (fn []
     [rc/modal-panel
      :child [rc/v-box
                     :children [[rc/title :label "Connect to pinocchio server" :level :level3]
                                [rc/v-box
                                 :class "form-group"
                                 :children [[:label {:for "connection-url"} "Url"]
                                            [rc/input-text
                                             :class "form-control"
                                             :model @url
                                             :placeholder "ws://localhost:8181/ws"
                                             :on-change #(reset! url %)]
                                            [rc/button
                                             :label "Connect"
                                             :class "btn-primary"
                                             :on-click #(re-frame/dispatch [:connect @url])]]]]]])))

(defn main-screen []
  (let [img-stream-style {:width 200
                          :height 200
                          :margin-left 20}]
    [:div
     [:div
      (map
       (fn [str-url]
         [:img {:src str-url
                :style img-stream-style
                :key str-url}])
       (listen [:streams-urls]))]
     [:div {} (str (listen [:stats]))]]))



(defn main-panel []
  [:div
   [main-screen]
   (when-not (listen [:connected?])
     [connection-screen])])
 
 
