(ns jaiaani.http.routes
  (:require [compojure.core :refer :all]
            [clojure.data.json :as json]
            [jaiaani.controller :as controller]))

(defn post-transaction
  []
  (POST "/clientes/:id/transacoes" {params :params body :body}
    (let [body {:body (json/read-str (slurp body)) :id (Integer/parseInt (:id params))}]
      (controller/get-client-handler-wrapper body controller/transaction-response!))))


(defn get-extract
  []
  (GET "/clientes/:id/extrato" [id]
    (controller/get-client-handler-wrapper {:id (Integer/parseInt id)} controller/statement-response)))
