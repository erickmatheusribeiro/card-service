#
# Build stage
#
FROM maven:3.9.6-amazoncorretto-21 AS build

WORKDIR /card

COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src
RUN mvn -B package -DskipTests

#
# Package stage
#
FROM amazoncorretto:21-alpine-jdk

WORKDIR /card

COPY --from=build /card/target/*.jar ./card-service.jar

EXPOSE 7074

ENTRYPOINT ["java","-jar","card-service.jar"]