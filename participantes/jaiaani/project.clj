(defproject jaiaani "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :repositories {"my.datomic.com" {:url "https://my.datomic.com/repo"
                                   :creds :gpg}}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [http-kit "2.7.0"]
                 [compojure "1.7.1"]
                 [clj-time "0.15.2"]
                 [org.clojure/data.json "2.5.0"]
                 [com.datomic/peer "1.0.7075"
                  :exclusions [org.slf4j/slf4j-nop org.slf4j/log4j-over-slf4j]]]
  :repl-options {:init-ns jaiaani.core}
  :main jaiaani.core)
