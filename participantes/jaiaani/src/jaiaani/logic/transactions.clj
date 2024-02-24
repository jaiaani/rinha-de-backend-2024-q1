(ns jaiaani.logic.transactions
  (:require [jaiaani.adapter :as adapter]
            [clj-time.coerce :as time]))

(defn debit-transaction [actual-balance value limit]
  (let [new-balance (- actual-balance value )
        valid-balance? (< 0 (+ limit new-balance))]
   (when valid-balance? new-balance)))

(defn credit-transaction [actual-balance value]
  (+ actual-balance value))

(defn new-balance
  [{:keys [balance limit]}
   {:keys [value type]}]
  (cond
   (= :d type) (debit-transaction balance value limit)
   (= :c type) (credit-transaction balance value)))

(defn sort-by-date
  [transactions-list]
  (take 10 (sort-by #(time/from-string (get % 2)) transactions-list)))

(defn last-10-transactions
  [transactions-list]
  (->> (sort-by-date transactions-list)
  (mapv #(adapter/transaction-db->out %))))
