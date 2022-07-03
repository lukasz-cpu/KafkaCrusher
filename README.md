# Kafka Crusher
## REST API for Kafka
![CI](https://github.com/lukasz-cpu/KafkaCrusher/actions/workflows/main.yml/badge.svg)

Kafka Crusher was created to learn Apache Kafka for my own purposes.

During creating this program, I wanted to learn the basic CRUD operations on Kafka. I decided to create REST Client for Apache Kafka with database
responsible for storing connection details.

## Additional features

- Register connection name
- Add topic for connection name
- Delete topic for connection name
- Multiple server addreses for one connection name
- Send message
- Read messages
- Connection details stored in database

## CI steps

- checkout 
- mvn clean package
- docker image build & push