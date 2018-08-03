(defproject com.eoneq/time "0.2.10"

  :global-vars {*warn-on-reflection* true}
  
  :lein-tools-deps/config {:config-files [:install :user :project]}

  :middleware [lein-tools-deps.plugin/resolve-dependencies-with-deps-edn]
  
  :plugins [[lein-tools-deps "0.4.1"]])
