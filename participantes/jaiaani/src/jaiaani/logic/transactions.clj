(ns jaiaani.logic.transactions
  (:require [jaiaani.adapter :as adapter]
            [clj-time.coerce :as time]))

(defn debit-transaction [actual-balance value limit]
  (let [new-balance (- value actual-balance)
        valid-balance? (< 0 (+ limit new-balance))]
   (when valid-balance? new-balance)))

(defn credit-transaction [actual-balance value]
  (+ actual-balance value))

(defn new-balance
  [{:keys [balance limit]}
   {:keys [value transaction-type]}]
  (cond
   (= :d transaction-type) (debit-transaction balance value limit)
   (= :c transaction-type) (credit-transaction balance value)))

(defn last-transactions
  [transactions-list]
  (mapv #(adapter/transaction-db->out %) transactions-list))

(defn last-10-transactions [transactions-list]
  (take 10 (reverse (sort-by #(time/from-string (:realizada_em %)) (last-transactions transactions-list)))))

(defn has-nil-value? [m]
  (some? (some nil? (vals m))))
