(ns jaiaani.database.queries
  (:require [jaiaani.database.operations :as db.ops]
            [datomic.api :as d]
            [clj-time.core :as time]))

(defn client [id]
  (let [db (d/db (db.ops/conn))]
    (first (d/q '[:find ?limit ?balance
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

(defn past-transactions [client-id]
  (let [db (d/db (db.ops/conn))]
    (d/q '[:find ?value ?type ?description ?moment
           :in $ ?client-id
           :where [?e :transacao/cliente-id ?client-id]
                  [?e :transacao/valor ?value]
                  [?e :transacao/tipo ?type]
                  [?e :transacao/descricao ?description]
                  [?e :transacao/realizada-em ?moment]]
         db client-id)))


(db.ops/do-transact [{:transacao/cliente-id 1
                      :transacao/valor 100
                      :transacao/tipo :d
                      :transacao/descricao "testando2"
                      :transacao/realizada-em (str (time/now))}])