#FROM openjdk:11-jdk-alpine
FROM adoptopenjdk/openjdk11

CMD ["pwd"]

COPY ./L_LIFE/build/libs/L_LIFE-0.0.1-SNAPSHOT.jar l_life.jar

ENTRYPOINT ["java","-jar","l_life.jar"]
