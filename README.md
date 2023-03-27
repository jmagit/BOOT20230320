# Curso de Java (Spring, Maven, JavaScript, NodeJS, ReactJS)

## Instalaciones

### Back end

- [JDK](https://www.oracle.com/es/java/technologies/downloads/)
- [Eclipse IDE for Enterprise Java and Web Developers](https://www.eclipse.org/downloads/packages/)
- [Spring Tools 4](https://spring.io/tools)
- [Git](https://git-scm.com/)
- [Maven](https://maven.apache.org/download.cgi)
- [WSL2](https://learn.microsoft.com/en-us/windows/wsl/install)
- [Docker](https://www.docker.com/get-started/)

### Front end

- [Visual Studio Code](https://code.visualstudio.com/download)
- [Node.js](https://nodejs.org/es)

## Documentación

- https://docs.spring.io/spring-boot/docs/current/reference/html/
- https://docs.spring.io/spring-data/commons/docs/current/reference/html/
- https://docs.spring.io/spring-data/jpa/docs/current/reference/html/
- https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/
- https://docs.spring.io/spring-data/redis/docs/current/reference/html/
- https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#spring-web
- https://docs.spring.io/spring-data/rest/docs/current/reference/html/
- https://docs.spring.io/spring-cloud-commons/docs/current/reference/html/#spring-cloud-loadbalancer
- https://docs.spring.io/spring-cloud-config/docs/current/reference/html/
- https://docs.spring.io/spring-security/reference/index.html
- [PATRONES de DISEÑO](https://refactoring.guru/es/design-patterns)
- [Markdown](https://markdown.es/sintaxis-markdown/)

## Ejemplos

- https://github.com/spring-projects/spring-data-examples
- https://github.com/spring-projects/spring-data-rest-webmvc
- https://github.com/spring-projects/spring-hateoas-examples

## Ejercicios

- https://github.com/emilybache/GildedRose-Refactoring-Kata

## Base de datos de ejemplos

- [Página principal Sakila](https://dev.mysql.com/doc/sakila/en/)
- [Diagrama de la BD Sakila](http://trifulcas.com/wp-content/uploads/2018/03/sakila-er.png)

## Paquetes Java

- https://downloads.mysql.com/archives/get/p/3/file/mysql-connector-java-5.1.49.zip  
- https://sourceforge.net/projects/hibernate/files/hibernate-orm/5.6.5.Final/hibernate-release-5.6.5.Final.zip/download

## Servidores en Docker

### Bases de datos

- docker run -d --name mysql-sakila -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 jamarton/mysql-sakila
- docker run -d --name mongodb -p 27017:27017 mongo
- docker run --name redis -p 6379:6379 -d redis

### Mensajería (Rabbitmq/Kafka)

- docker run -d --hostname rabbitmq --name rabbitmq -p 4369:4369 -p 5671:5671 -p 5672:5672 -p 15671:15671 -p 15672:15672 -p 25672:25672 -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=curso rabbitmq:management-alpine

#### Kafka (docker compose)

    services:
    zookeeper:
        image: confluentinc/cp-zookeeper:latest
        container_name: zookeeper
        environment:
        ZOOKEEPER_CLIENT_PORT: 2181
        ZOOKEEPER_TICK_TIME: 2000
        ports:
        - 2181:2181
    
    kafka:
        image: confluentinc/cp-kafka:latest
        container_name: kafka
        depends_on:
        - zookeeper
        ports:
        - 9092:9092
        environment:
        KAFKA_BROKER_ID: 1
        KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
        KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka:29092,PLAINTEXT_HOST://localhost:9092
        KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT
        KAFKA_INTER_BROKER_LISTENER_NAME: PLAINTEXT
        KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
    
    kafka-ui:
        image: provectuslabs/kafka-ui
        container_name: kafka-ui
        depends_on:
        - kafka
        ports:
        - 9091:8080
        environment:
        - KAFKA_CLUSTERS_0_NAME=local
        - KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS=kafka:29092
        - KAFKA_CLUSTERS_0_ZOOKEEPER=localhost:2181

### Monitorización (Prometheus/Grafana)

- docker run -d -p 9090:9090 --name prometheus -v $PWD/config-dir/prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus
- docker run -d -p 9090:9090 --name prometheus -v "%cd%"\config-dir\prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus
- docker run -d -p 3000:3000 --name grafana grafana/grafana

### Trazabilidad (Zipkin)

- docker run -d -p 9411:9411 --name zipkin openzipkin/zipkin-slim

### ELK

- docker run -d -p 9200:9200 -p 9300:9300 --name=elasticsearch -h elasticsearch -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:7.12.0
- docker run -d -p 5601:5601 --name kibana -h kibana --link elasticsearch:elasticsearch docker.elastic.co/kibana/kibana:7.12.0
