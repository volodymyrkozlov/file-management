FROM openjdk:17

ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,address=10201,server=y,suspend=n
ADD file-management-service/target/file-management-service.jar /app/file-management-service.jar

ENTRYPOINT ["java", "-jar", "/app/user-application-service.jar"]