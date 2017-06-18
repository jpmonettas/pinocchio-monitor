(ns pinocchio-monitor.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :stats
 (fn [db]
   (:stats db)))

(re-frame/reg-sub
 :connected?
 (fn [db]
   (:connected? db)))

(re-frame/reg-sub
 :streams-urls
 (fn [db]
   (map #(str "http://" (:server-host db) "/streams/" %)
        (:streams-names db))))


