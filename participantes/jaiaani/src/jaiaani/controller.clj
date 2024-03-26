(ns jaiaani.controller
  (:require [clojure.data.json :as json]
            [jaiaani.database.queries :as db.queries]
            [jaiaani.adapter :as adapter]))
(defn get-client-handler-wrapper
  [body handler]
  (let [client-id (db.queries/client-id (:id body))
        id (:cliente/id client-id)]
    (if client-id
      (handler body id)
      {:status 404
       :headers {"Content-Type" "text/plain"}
       :body "Hmm...No, i don't know this guy"})))

(defn transaction-response!
  [body client-id]
  (let [transaction (adapter/out-transaction->db (:body body))]
    (db.queries/update-client-balance! client-id transaction)))

(defn statement-response
  [_ client-id]
    (let [last-10-transactions (db.queries/last-10-transactions client-id)
          account (db.queries/client-account client-id)
          statement (adapter/statement->out account last-10-transactions)]
      (println last-10-transactions)
    {:status 200
     :headers {"Content-Type" "application/json"}
     :body (json/write-str statement)}))
