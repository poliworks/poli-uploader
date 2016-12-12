(ns poli-uploader.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.middleware.multipart-params :as mp]
            [aws.sdk.s3 :as s3]
            [ring.middleware.cors :as cors]
            [clojure.string :as str]
            [digest]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]))

(defn debug [v]
  (println v)
  v)

(def aws-cred {:access-key (debug (System/getenv "AWS_ACCESS_KEY"))
               :secret-key (debug (System/getenv "AWS_SECRET_KEY"))})

(defn upload-object [info file]
  (let [key (str (:type info) "/" (digest/digest "md5" file) "." (:extension info))]
    (s3/put-object aws-cred (:bucket info) key file {:content-type (str "image/" (:extension info))})
    (str "https://s3-sa-east-1.amazonaws.com/" (:bucket info) "/" key)))

(defn handle-upload [{:keys [params] :as request}]
  (println params)
  {:status 200 :body {:uri (upload-object (assoc params :extension (-> (get params "file") :filename (str/split #"\.") last)) (->> (get params "file") :tempfile))}})

(defroutes app-routes
  (POST "/upload/:bucket/:type" request (handle-upload request))
  (route/not-found "Not Found"))

(def app
  (-> app-routes
      (cors/wrap-cors #".*")
      (mp/wrap-multipart-params)
      (wrap-json-response)
      (wrap-defaults api-defaults)))
