FROM openjdk:20
WORKDIR /opt
ENV PORT 8080
EXPOSE 8080
COPY target/*.jar /opt/app.jar
ENTRYPOINT exec java -jar app.jar --spring.profiles.active=prod