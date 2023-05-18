FROM amazoncorretto:8-alpine-jdk
MAINTAINER EaVc
COPY target/eavc-0.0.1-SNAPSHOT.jar eavc-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/eavc-0.0.1-SNAPSHOT.jar"]
