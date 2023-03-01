FROM gradle:jdk11

WORKDIR /app
COPY build/libs/studyolle-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/app.jar"]