(ns jaiaani.http.routes
  (:require [compojure.core :refer :all]
            [clojure.data.json :as json]
            [jaiaani.adapter :as adapter]
            [jaiaani.controller :as controller]
            [jaiaani.database.operations :as db.ops]))

(defn post-transaction
  []
  (POST "/clientes/:id/transacoes" {params :params body :body}
    (let [body {:body (json/read-str (slurp body)) :id params}
          transaction (adapter/transaction-out->in (:body body))]
      (db.ops/do-transact (adapter/transaction-out->db (:body body)))
      {:status 200
       :body (controller/update-balance-response params transaction)})))


(defn get-extract
  []
  (GET "/clientes/:id/extrato" [id] (controller/statement-response id)))
