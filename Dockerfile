FROM gradle:7.2-jdk11-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon 

FROM openjdk:11

EXPOSE 9182

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/titanic-ship-api.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseContainerSupport", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/titanic-ship-api.jar"]