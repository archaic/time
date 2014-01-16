(ns time.core
  (:require [clj-time.coerce :as co]
            [clj-time.core :as t]
            [clj-time.format :as format :refer [formatter unparse]]))

(defn n-days-ago [n]
  "dt, n days ago from local date time"
  (let [dt (co/to-date-time (t/today))]
    (t/minus dt (t/days n))))

(defn dates [from to]
  "dt's from-to, inclusive"
  (take-while (fn [dt] (or (t/before? dt to) (= dt to)))
              (iterate #(t/plus % (t/days 1)) from)))

(defn n-last-days
  "An ordered sequence of the n last days in dts from today, inclusive"
  ([n] (n-last-days (co/to-date-time (t/today)) n))
  ([dt n] (reverse (dates (t/minus dt (t/days (dec n))) dt))))

(defn- pprint [dt s]
  (format "%04d%s%02d%s%02d" (t/year dt) s (t/month dt) s (t/day dt)))

(defn concise [dt]
  (pprint dt "/"))

(defn standard [dt]
  (pprint dt "-"))

(defn dt->date-map [dt]
  {:year (t/year dt)
   :month (t/month dt)
   :day (t/day dt)})

(defn current-date-map [str-time-zone]
  (let [time-zone (t/time-zone-for-id str-time-zone)
        dt (t/to-time-zone (t/now) time-zone)]
    (dt->date-map dt)))
