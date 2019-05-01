# garden watering server app
[![Build Status](https://travis-ci.org/codecentric/springboot-sample-app.svg?branch=master)](https://travis-ci.org/codecentric/springboot-sample-app)

Server based on [Spring Boot](http://projects.spring.io/spring-boot/) and [Pi4j](https://www.pi4j.com/)

Application created to control set of garden sprinklers grouped in sections. Accepts commands via http GET requests in local wifi network.

- control 8 sections of sprrinklers (group of 3 or 4)
- rain detector
- water flow sensor
- light indicator for system activation or error occurrence

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)
- [Raspberry Pi 3 Model 3](https://www.raspberrypi.org/)

additional hardware:
- 2x 8 Relay Modules
- rain detector
- water flow sensor
- 230 VAC to 24 VAC transformer


