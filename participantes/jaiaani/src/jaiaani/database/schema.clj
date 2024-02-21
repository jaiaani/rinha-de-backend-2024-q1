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
    :db/cardinality  :db.cardinality/one}

   {:db/ident        :transacao/cliente-id
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
    :db/valueType    :db.type/string
    :db/cardinality  :db.cardinality/one}])
