FROM arm64v8/openjdk:20-slim-buster
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} kafka-crusher.jar
ENTRYPOINT ["java","-jar","/kafka-crusher.jar"]