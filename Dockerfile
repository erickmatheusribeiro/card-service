#
# Build stage
#
FROM maven:3.9.6-amazoncorretto-21 AS build

WORKDIR /payment-system

COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src
RUN mvn -B package -DskipTests

#
# Package stage
#
FROM amazoncorretto:21-alpine-jdk

WORKDIR /payment-system

COPY --from=build /payment-system/target/*.jar ./payment-service.jar

EXPOSE 7074

ENTRYPOINT ["java","-jar","payment-service.jar"]