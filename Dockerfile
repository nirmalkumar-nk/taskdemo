FROM amazoncorretto:17.0.5
LABEL authors="nirmalkumarp"
ARG JAR_FILE=target/*.jar
COPY "./target/taskdemo-0.0.1-SNAPSHOT.jar" taskdemo.jar
ENTRYPOINT ["java", "-jar", "/taskdemo.jar"]