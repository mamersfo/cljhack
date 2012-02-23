(defproject cljhack "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [ring/ring-core "1.0.2"]
                 [ring/ring-jetty-adapter "1.0.2"]
                 [compojure "1.0.1"]
                 [clojure-csv "1.3.2"]
                 [org.clojure/java.jdbc "0.0.7"]
                 [postgresql/postgresql "9.1-901.jdbc4"]]
  :dev-dependencies
                [[lein-ring "0.5.4"]]
  :ring {:handler cljhack.web/app})