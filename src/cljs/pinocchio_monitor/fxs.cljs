(ns pinocchio-monitor.fxs
  (:require [re-frame.core :as re-frame]
            [cljs.core.async :as async :refer (<! >! put! chan pipe)]
            [taoensso.sente  :as sente :refer (cb-success?)])
  (:require-macros
      [cljs.core.async.macros :as asyncm :refer (go go-loop)]))


(def chsk (atom nil))
(def ch-recv (chan))

(go-loop [msg nil]
  (when msg
    (let [[chsk-ev-key chsk-ev-data] (:event msg)]
      (case chsk-ev-key
        :chsk/recv (let  [[ev-key data] chsk-ev-data]
                     (when-not (= ev-key :pinocchio.monitor/new-stats)
                       (.log js/console (:event msg)))
                     (case ev-key
                       :pinocchio.monitor/new-stats (re-frame/dispatch [:new-stats data])
                       :pinocchio.monitor/streams (re-frame/dispatch [:streams data])
                       nil))
        :chsk/state (let [[old-state new-state] chsk-ev-data]
                      (when (:open? new-state)
                         (re-frame/dispatch [:connected])))
        nil)))
  (recur (<! ch-recv)))

(re-frame/reg-fx
 :ws-send
 (fn [ev]
   (.log js/console "Sending " ev)
   ((:send-fn @chsk) ev)))

(re-frame/reg-fx
 :ws-connect
 (fn [url]
   (.log js/console "Connecting to " url)
   (let [cs (sente/make-channel-socket! url
                                  {:type :ws
                                   :chsk-url-fn (constantly url)})]
     (pipe (:ch-recv cs) ch-recv)
     (reset! chsk cs))))



