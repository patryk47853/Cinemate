FROM openjdk:21-jdk

WORKDIR /app

COPY target/cinemate-0.0.1.jar /app/cinemate.jar

EXPOSE 8080

CMD ["java", "-jar", "cinemate.jar"]