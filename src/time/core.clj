(ns time.core
  (:require [clj-time.coerce :as coerce]
            [clj-time.core :as time :refer [date-time year month day now]]
            [clj-time.format :as format :refer [formatter unparse]]
            [clojure.pprint :refer [pp pprint]]))

(defn current []
  (let [[year month day] ((juxt time/year time/month time/day)
                          (time/plus (now) (time/days 1)))]
    {:year year
     :month month
     :day day}))

(defn dt->time-map [dt]
  {:year (year dt) :month (month dt) :day (day dt)})

(defn n-days-ago [n]
  (let [current-dt (now)
        prior-dt (time/minus current-dt (time/days n))]
    (dt->time-map prior-dt)))

(defn dates [from to]
  "Dates from-to, inclusive"
  (take-while #(time/before? % (time/plus to (time/days 1)))
              (iterate #(time/plus % (time/days 1)) from)))

(defn n-last-days
  "An ordered sequence of the n last days from today, inclusive"
  ([n] (n-last-days (now) n))
  ([dt n]
     (reverse (dates (time/minus dt (time/days (dec n))) dt))))

(defn concise [dt]
  (format "%d/%d" (day dt) (month dt)))

(defn ppretty [date-time]
  (format "%04d/%02d/%02d" (year date-time) (month date-time) (day date-time)))

(defn date->standard [date]
  (let [date-time- (coerce/from-date date)]
    (format "%04d-%02d-%02d"
            (year date-time-)
            (month date-time-)
            (day date-time-))))

(defn current-date-map [time-zone-string]
  (let [time-zone (time/time-zone-for-id time-zone-string)
        current-date-time (time/to-time-zone (now) time-zone)
        [year month day] ((juxt time/year time/month time/day) current-date-time)]
    {:year year :month month :day day}))

(defn tommorrow-date-map [time-zone-string]
  (let [time-zone (time/time-zone-for-id time-zone-string)
        current-date-time (time/to-time-zone (time/plus (now) (time/days 1)) time-zone)
        [year month day] ((juxt time/year time/month time/day) current-date-time)]
    {:year year :month month :day day}))
