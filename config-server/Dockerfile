FROM amazoncorretto:21

COPY target/*.jar /config-server.jar

ENTRYPOINT ["java", "-jar", "config-server.jar"]