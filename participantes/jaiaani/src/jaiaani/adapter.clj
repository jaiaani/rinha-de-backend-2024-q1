(ns jaiaani.adapter
  (:require [clj-time.core :as time]))

(defn transaction-out->db
  [body]
  (let [valor     (get body "valor")
        tipo      (get body "tipo")
        descricao (get body "descricao")]
    {:transacao/valor valor
     :transacao/tipo (keyword tipo)
     :transacao/descricao descricao
     :transacao/realizada-em (str (time/now))}))

(defn transaction-out->in
  [body]
  (let [valor     (get body "valor")
        tipo      (get body "tipo")]
    {:value valor
     :transaction-type (keyword tipo)}))

(defn transaction-db->out
  [values]
  (let [value (nth values 0)
        type  (nth values 1)
        description (nth values 2)
        moment (nth values 3)]
    {:valor value
     :tipo type
     :descricao description
     :realizada_em moment}))

(defn client-out->in
  [id balance]
  (let [limit (first balance)
        balance (second balance)]
    {:id id
     :limit limit
     :balance balance}))

(defn statement->out
  [{:keys [balance limit]}
   last-transactions]
  {:saldo {:total balance
           :limite limit
           :data_extrato (str (time/now))}
   :ultimas_transacoes last-transactions})

(defn balance->out
  [{:keys [limit]}
   new-balance]
  {:saldo new-balance
   :limite limit})
