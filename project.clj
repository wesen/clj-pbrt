(defproject clj-pbrt "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://github.com/wesen/clj-pbrt"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
                 [org.clojure/clojure "1.6.0"]
                 [net.mikera/vectorz-clj "0.30.1"]
                 [net.mikera/imagez "0.6.0"]                      
                 ]
  :main ^:skip-aot clj-pbrt.core
  :target-path "target/%s"
  :profiles {
             :uberjar {:aot :all}
             })
