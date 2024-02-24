(ns jaiaani.database.queries
  (:require [jaiaani.database.operations :as db.ops]
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
