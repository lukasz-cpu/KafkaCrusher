FROM openjdk:20-oraclelinux7
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} kafka-crusher.jar
ENTRYPOINT ["java","-jar","/kafka-crusher.jar"]