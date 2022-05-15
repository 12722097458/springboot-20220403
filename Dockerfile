FROM openjdk:11.0.15-oraclelinux7
COPY springboot-20220403-0.0.1-SNAPSHOT.jar /app.jar

# EXPOSE 8888
CMD ["--server.port=9999"]
ENTRYPOINT ["java", "-jar", "/app.jar"]