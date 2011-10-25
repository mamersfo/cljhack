(ns forecast
	(:require [goog.dom :as dom]
			  [goog.net.XhrIo :as xhr]
			  [goog.events :as events]))
	
(defn get-value [id]
	(.value (dom/getElement id)))

(defn set-text [id text]
	(if-let [elem (dom/getElement id)]
		(dom/setTextContent elem text)))

(defn show-forecast [msg]
	(set-text "result" (. msg/target (getResponseText))))

(defn get-forecast [e]
	(let [city (get-value "city")
		  url (str "http://127.0.0.1:8080/forecast/" city)]
		(xhr/send url show-forecast)))

(defn ^:export main [n]
	(events/listen
		(dom/getElement "search")
		"click"
		get-forecast))