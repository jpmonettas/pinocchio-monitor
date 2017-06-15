(ns pinocchio-monitor.events
  (:require [re-frame.core :as re-frame]
            [pinocchio-monitor.db :as db]))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/reg-event-db
 :new-stats
 (fn  [db [_ stats]]
   (assoc db :stats stats)))
