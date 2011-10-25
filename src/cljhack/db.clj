(ns cljhack.db
	(:require [clojure.java.jdbc :as sql]))
	
(def spec {:classname	"com.postgresql.Driver"
           :subprotocol	"postgresql"
           :subname     "//localhost:5432/cljhack" 
           :user        "cljhack"
           :password    "cljhack"})

(defn find-city [city]
	(sql/with-connection spec
		(sql/with-query-results rows
			[(str "select * from features where class = 'P' and name = '" city "'")] 
			(first rows))))
