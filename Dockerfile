FROM maven:3.8.3-jdk-11-slim AS build
RUN mkdir -p /app-occupancysimulator
COPY src /app-occupancysimulator/src
COPY pom.xml /app-occupancysimulator
RUN mvn -f /app-occupancysimulator/pom.xml clean package -DskipTests

FROM openjdk:11-jre-slim
COPY --from=build /app-occupancysimulator/target/miejscowka-occupancysimulator.jar /usr/local/lib/miejscowka-occupancysimulator.jar
EXPOSE 8070
ENTRYPOINT ["java","-jar","/usr/local/lib/miejscowka-occupancysimulator.jar"]
