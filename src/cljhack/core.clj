(ns cljhack.core
	(:use cljhack.db))

(defn celsius-to-fahrenheit
	"Converts degrees Celsius to degrees Fahrenheit"
	[c]
	(+ (Math/round (* c 1.8)) 32))

(defn distance 
	"Calculates the Great Circle Distance in kilometers between two features
	 r * acos[sin(lat1) * sin(lat2) + cos(lat1) * cos(lat2) * cos(lon2 - lon1)]"
	[{latitude1 :lat longitude1 :lon} {latitude2 :lat longitude2 :lon}]
	(let [lat1  	(/ latitude1  (/ 180 Math/PI))
		  lon1 		(/ longitude1 (/ 180 Math/PI))
		  lat2  	(/ latitude2  (/ 180 Math/PI))
		  lon2 		(/ longitude2 (/ 180 Math/PI))
		  theta 	(- lon2 lon1)
		  radius	6378.137]
		(* radius (Math/acos (+ (* (Math/sin lat1) (Math/sin lat2))
								(* (Math/cos lat1) (Math/cos lat2) (Math/cos theta)))))))

(def weather-stations
	[{:name "Amsterdam", 	:lat 52.37403, 	:lon 4.88969}
	 {:name "Rotterdam", 	:lat 51.92250, 	:lon 4.47917}
	 {:name "Vlissingen", 	:lat 51.44250, 	:lon 3.57361}
	 {:name "Eindhoven", 	:lat 51.44083, 	:lon 5.47778}
	 {:name "Maastricht", 	:lat 50.84833, 	:lon 5.68889}
	 {:name "Arnhem", 		:lat 51.98000, 	:lon 5.91111}
	 {:name "De Bilt", 		:lat 52.11000, 	:lon 5.18056}
	 {:name "Zwolle", 		:lat 52.51250, 	:lon 6.09444}
	 {:name "Assen", 		:lat 52.99667, 	:lon 6.56250}
	 {:name "Groningen", 	:lat 53.21917, 	:lon 6.56667}
	 {:name "Leeuwarden", 	:lat 53.20000, 	:lon 5.78333}
	 {:name "Lelystad", 	:lat 52.50833, 	:lon 5.47500}
	])
	
(defn find-nearest-weather-station [city]
	(first 
		(sort-by :distance 
			(map #(assoc % :distance (distance % city)) weather-stations))))

(def forecasts (ref
	{"Amsterdam" 		{:temperature 15 :condition :rain	}
	 "Rotterdam" 		{:temperature 17 :condition :cloudy	}
	 "Vlissingen" 		{:temperature 21 :condition :sunny	}
	 "Eindhoven" 		{:temperature 14 :condition :sunny	}
	 "Maastricht" 		{:temperature 18 :condition :rain	}
	 "Arnhem" 			{:temperature 13 :condition :cloudy	}
	 "De Bilt" 			{:temperature 15 :condition :cloudy	}
	 "Zwolle" 			{:temperature 14 :condition :fog	}
	 "Assen" 			{:temperature 13 :condition :fog	}
	 "Groningen" 		{:temperature 12 :condition :fog	}
	 "Leeuwarden" 		{:temperature 11 :condition :fog	}
	 "Lelystad" 		{:temperature 16 :condition :cloudy	}
	}))
	
(defn lookup-forecast [c]
	(if-let [city (find-city c)]
		(let [station (find-nearest-weather-station city)]
			(assoc (@forecasts (:name station)) :station station))
		{:error (str "City " c " not found.")}))	

(defn update-forecast [station forecast]
	(if-let [previous (forecasts station)]
		(dosync
			(alter forecasts assoc station forecast)
			{:station station, :forecast (@forecasts station)})
		{:error (str "Station " station " not found.")}))