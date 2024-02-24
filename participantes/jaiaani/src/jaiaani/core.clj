(ns jaiaani.core
  (:require [org.httpkit.server :refer [run-server]]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [jaiaani.http.routes :as http]
            [jaiaani.database.data :as db.data]
            [jaiaani.database.schema :as db.schema]
            [jaiaani.database.operations :as db.ops]))



(db.ops/delete-database)
(db.ops/create-database)
(db.ops/do-transact db.schema/schema)
(db.ops/do-transact db.data/initial-customers)

(defroutes app
           (http/post-transaction)
           (http/get-extract)
           (route/not-found "<h1> not found, honey 🍯</h1>"))
(defn -main [& args]
  (run-server app {:port 8080})
  (println "Server started on port 8080"))
