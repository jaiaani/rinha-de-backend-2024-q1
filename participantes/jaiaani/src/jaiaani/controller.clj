(ns jaiaani.controller
  (:require [clojure.data.json :as json]
            [jaiaani.database.queries :as db.queries]
            [jaiaani.database.operations :as db.ops]
            [jaiaani.logic.transactions :as logic]
            [jaiaani.adapter :as adapter]))
(defn get-client-handler-wrapper
  [body handler]
  (let [get-client (db.queries/client (:id body))
        client (when (not-empty get-client) (adapter/out-client->in get-client))]
    (if client
      (handler body client)
      {:status 404
       :headers {"Content-Type" "text/plain"}
       :body "Hmm...No, i don't know this guy"})))

(defn transaction-response!
  [body client]
  (let [transaction (adapter/out-transaction->in (:body body))
        db-transaction (adapter/in-transaction->db transaction)
        new-balance (when transaction (logic/new-balance client transaction))
        balance-response (when new-balance (adapter/balance->out client new-balance))]
    (cond
      new-balance (do
                    (db.ops/do-transact [(assoc db-transaction
                                                    :db/id [:cliente/id (:id client)])])
                    (db.queries/update-client-balance (:id client) new-balance)
                      {:status 200
                       :headers {"Content-Type" "application/json"}
                       :body (json/write-str balance-response)})
      :else  {:status 422
              :headers {"Content-Type" "text/plain"}
              :body "Sorry baby, I can't deal with this 💅"})))

(defn statement-response
  [_ client]
    (let [last-10-transactions (db.queries/last-10-transactions (:id client))
          statement (adapter/statement->out client last-10-transactions)]
    {:status 200
     :headers {"Content-Type" "application/json"}
     :body (json/write-str statement)}))
