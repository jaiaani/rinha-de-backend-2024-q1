(ns jaiaani.database.schema)

(def client-schema
  [{:db/ident        :cliente/id
    :db/valueType    :db.type/long
    :db/cardinality  :db.cardinality/one}
   {:db/ident        :cliente/nome
    :db/valueType    :db.type/string
    :db/cardinality  :db.cardinality/one}
   {:db/ident        :cliente/limite
    :db/valueType    :db.type/long
    :db/cardinality  :db.cardinality/one}
   {:db/ident        :cliente/saldo
    :db/valueType    :db.type/long
    :db/cardinality  :db.cardinality/one}])

(def transactions-schema
  [{:db/ident        :transacao/cliente-id
    :db/valueType    :db.type/long
    :db/cardinality  :db.cardinality/one}
   {:db/ident        :transacao/valor
    :db/valueType    :db.type/long
    :db/cardinality  :db.cardinality/one}
   {:db/ident        :transacao/tipo
    :db/valueType    :db.type/keyword
    :db/cardinality  :db.cardinality/one}
   {:db/ident        :transacao/descricao
    :db/valueType    :db.type/string
    :db/cardinality  :db.cardinality/one}
   {:db/ident        :transacao/realizada-em
    :db/valueType    :db.type/instant
    :db/cardinality  :db.cardinality/one}])

(def balance-schema
  [{:db/ident        :saldo/cliente-id
    :db/valueType    :db.type/long
    :db/cardinality  :db.cardinality/one}
   {:db/ident        :saldo/total
    :db/valueType    :db.type/long
    :db/cardinality  :db.cardinality/one}
   {:db/ident        :saldo/limite
    :db/valueType    :db.type/long
    :db/cardinality  :db.cardinality/one}])

(def schema
  [{:db/id           #db/id [:db.part/db]
    :db/ident        :cliente/id
    :db/valueType    :db.type/long
    :db/unique       :db.unique/identity
    :db/cardinality  :db.cardinality/one}
   {:db/ident        :cliente/nome
    :db/valueType    :db.type/string
    :db/cardinality  :db.cardinality/one}
   {:db/ident        :cliente/limite
    :db/valueType    :db.type/long
    :db/cardinality  :db.cardinality/one}
   {:db/ident        :cliente/saldo
    :db/valueType    :db.type/long
    :db/cardinality  :db.cardinality/one}
   {:db/id           #db/id [:db.part/db]
    :db/ident        :cliente/transacoes
    :db/valueType    :db.type/ref
    :db/isComponent  true
    :db/cardinality  :db.cardinality/many}


   {:db/ident        :transacoes/valor
    :db/valueType    :db.type/long
    :db/cardinality  :db.cardinality/one}
   {:db/ident        :transacoes/tipo
    :db/valueType    :db.type/keyword
    :db/cardinality  :db.cardinality/one}
   {:db/ident        :transacoes/descricao
    :db/valueType    :db.type/string
    :db/cardinality  :db.cardinality/one}
   {:db/ident        :transacoes/realizada-em
    :db/valueType    :db.type/string
    :db/cardinality  :db.cardinality/one}])
