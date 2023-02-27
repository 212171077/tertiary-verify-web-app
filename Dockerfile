FROM openjdk:8
EXPOSE 8081
ADD target/TertiaryVerifyWebApp-1.0-SNAPSHOT.war TertiaryVerifyWebApp-1.0-SNAPSHOT.war
ENTRYPOINT ["java", "-jar", "./TertiaryVerifyWebApp-1.0-SNAPSHOT.war"]