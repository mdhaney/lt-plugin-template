(ns lt.plugins.{{name}}
  (:require [lt.object :as object]
            [lt.objs.tabs :as tabs]
            [lt.objs.command :as cmd]
            [lt.objs.files :as files]
            [lt.objs.document :as doc]
            [lt.objs.opener :as opener]
            [lt.objs.editor :as editor]
            [lt.objs.plugins :as plugins]
            [lt.objs.platform :as platform]
            [lt.objs.sidebar.command :as scmd]
            [lt.objs.editor.pool :as pool]
            [lt.util.load :as load]
            [lt.util.dom :as dom]
            [clojure.string :as string]
            [crate.binding :as bindings])
  (:require-macros [lt.macros :refer [defui behavior]]))

(def plugin-data (plugins/by-name "{{name}}"))


(defn safe-get-file
  "Returns composed, absolute and valid (safe) path to a file system resource
  (file/folder) located within the plugin root folder, elsewise throws error."
  [s] {:pre [(string? s)]}
   (let [cap (files/join (:dir plugin-data) s)]
     (if (files/exists? cap) cap
       (throw (js/Error. (str "Could not locate a file system resource (file or folder) at path '"
                             cap "'. Please check for errors and see if the path exists."))))))


(defui hello-panel [this]
  [:h1 "Hello from {{name}}"])

(object/object* ::{{name}}.hello
                :tags [:{{name}}.hello]
                :behaviors [::on-close-destroy]
                :name "{{name}}"
                :init (fn [this]
                        (hello-panel this)))

(behavior ::on-close-destroy
          :triggers #{:close}
          :desc "{{title}}: Close tab and tabset as well if last tab"
          :reaction (fn [this]
                      (when-let [ts (:lt.objs.tabs/tabset @this)]
                        (when (= (count (:objs @ts)) 1)
                          (tabs/rem-tabset ts)))
                      (object/raise this :destroy)))

(def hello (object/create ::{{name}}.hello))

(cmd/command {:command ::say-hello
              :desc "{{title}}: Say Hello"
              :exec (fn []
                      (tabs/add-or-focus! hello))})
