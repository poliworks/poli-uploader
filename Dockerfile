FROM java:8

WORKDIR /app
ADD target/poli-uploader-0.1.0-SNAPSHOT-standalone.jar /app/poli-uploader.jar
EXPOSE 5000
CMD ["java", "-jar", "/app/poli-uploader.jar"]
