(ns leiningen.new.lt-plugin
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]))

(def render (renderer "lt-plugin"))

(defn lt-plugin
  "Generates a Light Table plugin skeleton project"
  [name & args]
  (let [data {:name name
              :sanitized (name-to-path name)}
        json? (and args (= "-j" (subs (first args) 0 2)))
        desc (if json? ["plugin.json" (render "json" data)]
                       ["plugin.edn" (render "edn" data)])]
    (main/info (str "Generating Light Table plugin '" name "'"))
    (->files data
             desc
             ["src/lt/plugins/{{sanitized}}.cljs" (render "plug.cljs" data)]
             ["{{sanitized}}.behaviors" (render "behaviors" data)]
             ["{{sanitized}}_compiled.js" ""]
             ["project.clj" (render "project" data)]
             [".gitignore" (render "gitignore" data)])))
