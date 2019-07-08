FROM openjdk:11.0.3

ADD build/libs/recipesRating-0.0.1-SNAPSHOT.jar /usr/src/app.jar
WORKDIR /usr/src/
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
