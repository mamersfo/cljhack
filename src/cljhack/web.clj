(ns cljhack.web
	(:use ring.adapter.jetty
		  ring.middleware.params
		  [compojure core route]
		  cljhack.core))
		
(defroutes handler
	(GET "/forecast/:city" [city]
		(let [body (pr-str (lookup-forecast city))]
			{:status 200
			 :headers { "Content-Type", "text/plain" }
			 :body body}))
    (PUT "/forecast/:station" [station forecast]
		(do
			(update-forecast station (read-string forecast))
			{:status 204}))
	(files "/" {:root "web"})
	(not-found "Not found"))	

(def app
	(wrap-params handler))		

(defn -main [p]
	(let [port (Integer/parseInt p)]
		(run-jetty app {:port port})))