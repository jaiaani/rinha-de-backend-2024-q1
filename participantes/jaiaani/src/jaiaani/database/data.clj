(ns jaiaani.database.data)

(defn customer
 [id nome limite saldo]
  {:cliente/id id
   :cliente/nome nome
   :cliente/limite limite
   :cliente/saldo saldo})

(def customer-1 (customer 1 "o barato sai caro" (* 1000 100) 0))
(def customer-2 (customer 2 "zan corp ltda" (* 800 100) 0))
(def customer-3 (customer 3 "les cruders" (* 1000000 100) 0))
(def customer-4 (customer 4 "padaria joia de cocaia" (* 100000 100) 0))
(def customer-5 (customer 5 "kid mais" (* 5000 100) 0))


(def initial-customers [customer-1 customer-2 customer-3 customer-4 customer-5])
