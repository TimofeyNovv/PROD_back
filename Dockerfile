#FROM maven:4.0.0 AS build
#RUN mkdir -p /usr/app
#WORKDIR /usr/app
#COPY ../ /usr/app/
#RUN ls -la ../
#RUN mvn -f ./pom.xml clean package

FROM eclipse-temurin:17-jdk-jammy AS build
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD . $HOME
RUN --mount=type=cache,target=/root/.m2 ./mvnw -f $HOME/pom.xml clean package -Dmaven.test.skip=true

FROM eclipse-temurin:17-jre-jammy
ARG JAR_FILE=/usr/app/target/*.jar
COPY --from=build $JAR_FILE /app/application.jar

ENTRYPOINT java -jar /app/application.jar
