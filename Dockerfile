FROM openjdk:17-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} kafka-crusher.jar
ENTRYPOINT ["java","-jar","/kafka-crusher.jar"]