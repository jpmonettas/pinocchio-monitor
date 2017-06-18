(ns pinocchio-monitor.events
  (:require [re-frame.core :as re-frame :refer [debug]]
            [pinocchio-monitor.db :as db]))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/reg-event-db
 :new-stats
 (fn  [db [_ stats]]
   (assoc db :stats stats)))

(re-frame/reg-event-fx
 :connect
 [debug]
 (fn  [cofxs [_ url]]
   {:ws-connect url
    :db (assoc (:db cofxs) :server-host (second (re-matches #"ws://(.+?)/ws" url)))}))

(re-frame/reg-event-db
 :connected
 [debug]
 (fn  [db _]
   (assoc db :connected? true)))

(re-frame/reg-event-db
 :streams
 [debug]
 (fn  [db [_ streams-names]]
   (assoc db :streams-names streams-names)))
