(ns jaiaani.controller
  (:require [clojure.data.json :as json]
            [jaiaani.database.queries :as db.queries]
            [jaiaani.logic.transactions :as logic]
            [jaiaani.adapter :as adapter]))

(defn update-balance-response
  [id
   transaction]
  (let [id (Integer/parseInt id)
        client (when id (adapter/client-out->in id (db.queries/client id)))
        valid-client? (not (logic/has-nil-value? client))
        transaction (when valid-client? (adapter/transaction-out->in transaction))
        new-balance (when transaction (logic/new-balance client transaction))
        return (when new-balance (adapter/balance->out client new-balance))]
    (cond
      (and valid-client? new-balance) (do (db.queries/update-client-balance (:id client) new-balance)
                                   {:status 200
                                    :headers {"Content-Type" "application/json"}
                                    :body (json/write-str return)})

      valid-client?                      {:status 422
                                   :headers {"Content-Type" "text/plain"}
                                   :body "Sorry baby, I can't deal with this 💅"}
      :else {:status 404
             :headers {"Content-Type" "text/plain"}
             :body "Are you lost?"})))

(defn statement-response
  [id]
  (let [id (Integer/parseInt id)
        client (when id (adapter/client-out->in id (db.queries/client id)))
        valid-client? (not (logic/has-nil-value? client))
        past-transactions (when valid-client? (db.queries/past-transactions id))
        last-10-transactions (when past-transactions (logic/last-10-transactions past-transactions))]
    (cond
      (not valid-client?) {:status 404
                           :headers {"Content-Type" "text/plain"}
                           :body "Are you lost?"}

      (and valid-client? last-10-transactions) {:status 200
                                               :headers {"Content-Type" "application/json"}
                                               :body (json/write-str (adapter/statement->out client last-10-transactions))})))

