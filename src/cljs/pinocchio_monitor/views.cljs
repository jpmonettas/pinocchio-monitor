(ns pinocchio-monitor.views
  (:require [re-frame.core :as re-frame]))

(defn listen [s]
  @(re-frame/subscribe s))
(defn main-panel []
  (let [img-stream-style {:width 200
                          :height 200
                          :margin-left 20}]
    [:div
     [:div
      [:img {:src "http://localhost:8181/streams/camera1" :style img-stream-style}]
      [:img {:src "http://localhost:8181/streams/color-filtered" :style img-stream-style}]
      [:img {:src "http://localhost:8181/streams/tracked" :style img-stream-style}]]
     [:div {} (str (listen [:stats]))]]))
