FROM amazoncorretto:21

WORKDIR /app

COPY target/*.jar /front.jar

ENTRYPOINT ["java", "-jar", "front.jar"]
