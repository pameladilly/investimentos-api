#FROM alpine:3.14
FROM public.ecr.aws/ubuntu/ubuntu:21.04

#RUN addgroup -S spring && adduser -S spring -G spring
#USER spring:spring
#RUN apt-get update && apt-get install -y \
#    software-properties-common

RUN apt-get update && \
    apt-get install -y openjdk-11-jdk && \
    apt-get install -y ant && \
    apt-get install -y --no-install-recommends curl && \
    apt-get clean;


#RUN apt-get update && \
#    apt-get install ca-certificates-java && \
#    apt-get clean && \
#    update-ca-certificates -f;

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8081

ENTRYPOINT ["java","-jar","/app.jar"]

#USER root
#
#RUN apk add --no-cache openjdk11
#
#WORKDIR /app
#
#COPY .mvn/ .mvn
#COPY mvnw pom.xml ./
#RUN ./mvnw dependency:go-offline
#
#COPY src ./src
#
#EXPOSE 8080/tcp
#
#CMD ["./mvnw", "spring-boot:run"]
