(defproject poli-uploader "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.5.1"]
                 [clj-aws-s3 "0.3.10"]
                 [ring/ring-json "0.4.0"]
                 [digest "1.4.5"]
                 [jumblerg/ring.middleware.cors "1.0.1"]
                 [ring/ring-defaults "0.2.1"]]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler poli-uploader.handler/app
         :port 5000}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]}})
