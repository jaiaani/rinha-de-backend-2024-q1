(ns jaiaani.logic.transactions)

(defn debit-transaction [actual-balance value limit]
  (let [new-balance (- actual-balance value )
        valid-balance? (<= 0 (+ limit new-balance))]
   (when valid-balance? new-balance)))

(defn credit-transaction [actual-balance value]
  (+ actual-balance value))

(defn new-balance
  [{:cliente/keys [saldo limite]}
   {:cliente/keys [transacoes]}]
  (cond
   (= :d (:transacoes/tipo transacoes)) (debit-transaction saldo (:transacoes/valor transacoes) limite)
   (= :c (:transacoes/tipo transacoes)) (credit-transaction saldo (:transacoes/valor transacoes))))
