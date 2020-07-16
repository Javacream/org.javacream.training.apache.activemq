# Examples using Docker

## Base Image

An ActiveMQ image is not hosted on Docker Hub. So simply create it for yourself:

* switch to directory image
* execute `docker build -t javacream/activemq:1.0 .`

## Security

* switch to directory security
* execute `docker-compose up -d --build`
* use sample from package `org.javacream.training.activemq.security`
* open your hawtio and check destinations

## Dead Letter Queue and Redelivery

* switch to directory dlq
* execute `docker-compose up -d --build`
* use sample from package `org.javacream.training.activemq.redelivery`
* open your hawtio and check destinations

## Master Slave

* switch to directory master-slave
* execute `docker-compose up`
* use sample from package `org.javacream.training.activemq.masterslave`
  * take a closer look at the failover-URL!
* stop and start individual containers and check corresponding console output
* after examining the sample do some cleanup executing `docker-compose down -v`
