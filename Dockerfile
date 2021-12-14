#FROM alpine:3.14
FROM public.ecr.aws/amazonlinux/amazonlinux:latest

#RUN addgroup -S spring && adduser -S spring -G spring
#USER spring:spring

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

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
