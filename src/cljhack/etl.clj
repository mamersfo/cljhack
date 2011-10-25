(ns cljhack.etl
	(:require [clojure-csv.core :as csv]
		      [clojure.java.jdbc :as sql]
		      [cljhack.db :as db]))
	
(defn read-feature [[id name _ _ lat lon class code & more]]
	[(try (Integer/parseInt id) (catch Exception e (0)))
	 name
	 (try (Double/parseDouble lat) (catch Exception _ (0.0)))
	 (try (Double/parseDouble lon) (catch Exception _ (0.0)))
	 class
	 code])

(defn read-features [f]
	(map read-feature
		(binding [csv/*delimiter* \tab]
			(csv/parse-csv (slurp f)))))
			
(defn create-features []
	(sql/with-connection db/spec
		(sql/create-table
			:features
			[:id :integer "PRIMARY KEY"]
			[:name :varchar "(200)"]
			[:lat :float]
			[:lon :float]
			[:class :varchar "(1)"]
			[:code :varchar "(10)"])))

(defn drop-features []
	(sql/with-connection db/spec
		(sql/drop-table :features)))

(defn insert-features [features]
	(sql/with-connection db/spec
		(sql/transaction
			(doseq [f features]
				(sql/insert-values :features [:id :name :lat :lon :class :code]
					f)))))
					
(defn list-features [limit]
	(sql/with-connection db/spec
		(sql/with-query-results rows
			["select * from features"]
			(into [] (take limit rows)))))

