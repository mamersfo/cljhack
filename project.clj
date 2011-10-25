(defproject cljhack "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [ring/ring-core "1.0.0-RC1"]
                 [ring/ring-jetty-adapter "1.0.0-RC1"]
                 [compojure "0.6.5"]
                 [clojure-csv "1.3.2"]
                 [org.clojure/java.jdbc "0.0.7"]
                 [postgresql/postgresql "9.0-801.jdbc4"]]
  :dev-dependencies
                [[lein-ring "0.4.6"]]
  :ring {:handler cljhack.web/app})