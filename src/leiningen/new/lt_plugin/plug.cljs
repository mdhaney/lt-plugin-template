(ns lt.plugins.{{name}}
  (:require [lt.object :as object]
            [lt.objs.tabs :as tabs]
            [lt.objs.command :as cmd])
  (:require-macros [lt.macros :refer [defui behavior]]))

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
          :desc "{{name}}: Close tab and tabset as well if last tab"
          :reaction (fn [this]
                      (when-let [ts (:lt.objs.tabs/tabset @this)]
                        (when (= (count (:objs @ts)) 1)
                          (tabs/rem-tabset ts)))
                      (object/raise this :destroy)))

(def hello (object/create ::{{name}}.hello))

(cmd/command {:command ::say-hello
              :desc "{{name}}: Say Hello"
              :exec (fn []
                      (tabs/add-or-focus! hello))})
