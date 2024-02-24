(ns jaiaani.adapter
  (:require [clj-time.core :as time]))

(defn in-transaction->db
  [{:keys [value type description]}]
  {:cliente/transacoes {:transacoes/valor value
                         :transacoes/tipo (keyword type)
                         :transacoes/descricao description
                         :transacoes/realizada-em (str (time/now))}})

(defn out-transaction->in
  [body]
  (let [value       (get body "valor")
        type        (get body "tipo")
        description (get body "descricao")]
    {:value value
     :type (keyword type)
     :description description}))

(defn transaction-db->out
  [{:transacoes/keys [valor tipo descricao realizada-em]}]
    {:valor valor
     :tipo tipo
     :descricao descricao
     :realizada_em realizada-em})

(defn out-client->in
  [balance]
    (let [id (first balance)
          limit (second balance)
          balance (get balance 2)]
      {:id id
       :limit limit
       :balance balance}))

(defn statement->out
  [{:keys [balance limit]}
   last-transactions]
  {:saldo {:total balance
           :limite limit
           :data_extrato (str (time/now))}
   :ultimas_transacoes (mapv #(transaction-db->out %)last-transactions)})

(defn balance->out
  [{:keys [limit]}
   new-balance]
  {:saldo new-balance
   :limite limit})
