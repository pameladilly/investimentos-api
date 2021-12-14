FROM alpine:3.14

USER root

RUN apk add --no-cache openjdk11

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src

EXPOSE 8080/tcp

CMD ["./mvnw", "spring-boot:run"]