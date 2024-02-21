(ns jaiaani.database.operations
  (:require [datomic.api :as d]))

(def db-uri "datomic:dev://localhost:4334/rinha")

(defn conn []
  (d/connect db-uri))

(defn create-database []
  (d/create-database db-uri)
  (conn))

(defn delete-database []
  (d/delete-database db-uri))


(defn do-transact [thing]
  (d/transact (d/connect db-uri) thing))

(defn populate-dataset [schema data]
  (do-transact schema)
  (map #(do-transact [%]) data))
