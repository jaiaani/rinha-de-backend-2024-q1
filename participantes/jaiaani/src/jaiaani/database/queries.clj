(ns jaiaani.database.queries
  (:require [clojure.data.json :as json]
            [jaiaani.adapter :as adapter]
            [jaiaani.logic.transactions :as logic]
            [jaiaani.database.operations :as db.ops]
            [datomic.api :as d]))

(defn client [id]
  (let [db (d/db (db.ops/conn))]
    (first (d/q '[:find ?id ?limit ?balance
                  :in $ ?id
                  :where [?e :cliente/id ?id]
                  [?e :cliente/limite ?limit]
                  [?e :cliente/saldo ?balance]] db id))))

(defn update-value [client entity att]
  (db.ops/do-transact [[:db/add (first client) entity att]]))

(defn update-client-balance
  [client-id new-balance]
  (let [db (d/db (db.ops/conn))]
    (-> (first (d/q '[:find ?e
                      :in $ ?client-id
                      :where [?e :cliente/id ?client-id]] db client-id))
        (update-value :cliente/saldo new-balance))))

(def transactions-pattern
  [{:cliente/transacoes [:transacoes/valor
                         :transacoes/tipo
                         :transacoes/descricao
                         :transacoes/realizada-em]}])

(defn last-10-transactions
  [client-id]
  (let [db (d/db (db.ops/conn))]
  (->> (d/pull db transactions-pattern [:cliente/id client-id])
      first
      second
      (take-last 10)
       reverse)))
(defn client-id
  [id]
  (let [db (d/db (db.ops/conn))]
    (d/pull db [:cliente/id] [:cliente/id id])))

(defn client-account
  [client-id]
  (let [db (d/db (db.ops/conn))]
    (d/pull db [:cliente/limite :cliente/saldo] [:cliente/id client-id])))



(defn register-transaction!
  [transaction]
  (db.ops/do-transact [(assoc transaction :db/id [:cliente/id 1])]))

(defn update-client-balance
  [client-id account new-balance]
  (let [actual-balance (:cliente/saldo account)]
(db.ops/do-transact [[:db/cas [:cliente/id client-id] :cliente/saldo actual-balance new-balance]])))

(defn successful-transaction
  [client-id]
  (let [db (d/db (db.ops/conn))
        account (d/pull db [:cliente/saldo :cliente/limite] [:cliente/id client-id])
        balance->out (adapter/balance->out account)]
    {:status 200
     :headers {"Content-Type" "application/json"}
     :body (json/write-str balance->out)}))

(def error-response
  {:status 422
   :headers {"Content-Type" "text/plain"}
   :body "Sorry, cant do that"})

(defn update-client-balance!
  [client-id transaction]
  (let [db (d/db (db.ops/conn))
        account (d/pull db [:cliente/saldo :cliente/limite] [:cliente/id client-id])
        new-balance (logic/new-balance account transaction)
        successful? (when new-balance true)]
  (try  (if successful?
          (do (try (do @(update-client-balance client-id account new-balance)
                       )(register-transaction! transaction)
              (successful-transaction client-id))
           error-response)
        (catch Exception e (do (update-client-balance client-id account (:cliente/saldo account))
                               (successful-transaction client-id))))))
