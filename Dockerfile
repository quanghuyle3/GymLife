FROM eclipse-temurin:18 

COPY ./target/GymLife-0.0.1-SNAPSHOT.jar /app/ 

WORKDIR /app

CMD ["java", "-jar", "GymLife-0.0.1-SNAPSHOT.jar"]
