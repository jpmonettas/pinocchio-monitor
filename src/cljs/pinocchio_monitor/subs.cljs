(ns pinocchio-monitor.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :stats
 (fn [db]
   (:stats db)))
