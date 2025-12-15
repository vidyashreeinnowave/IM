FROM eclipse-temurin:17-jdk-jammy
 
ADD /proximus.jar //
 
ENTRYPOINT ["java", "-jar", "/proximus.jar"]