(ns jaiaani.database.schema)

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
