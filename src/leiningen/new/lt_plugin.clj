(ns leiningen.new.lt-plugin
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]))

(def render (renderer "lt-plugin"))

(defn lt-plugin
  "FIXME: write documentation"
  [name]
  (let [data {:name name
              :sanitized (name-to-path name)}]
    (main/info "Generating fresh 'lein new' lt-plugin project.")
    (->files data
             ["src/lt/plugins/{{sanitized}}.cljs" (render "plug.cljs" data)]
             ["{{sanitized}}.behaviors" (render "behaviors" data)]
             ["project.clj" (render "project" data)]
             ["plugin.edn" (render "edn" data)])))
