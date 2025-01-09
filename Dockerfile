FROM timbru31/java-node:21-jre-20

ADD EspritDuMal.jar EspritDuMal.jar
ENTRYPOINT ["java", "-jar","EspritDuMal.jar"]
EXPOSE 8080
