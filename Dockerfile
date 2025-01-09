FROM rsunix/yourkit-openjdk17

ADD EspritDuMal.jar EspritDuMal.jar
ENTRYPOINT ["java", "-jar","EspritDuMal.jar"]
EXPOSE 8080
