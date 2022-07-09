FROM openjdk:11
WORKDIR /usr/app
COPY build/libs/repoCentral-0.0.1-SNAPSHOT.jar patient-repo-microService.jar
CMD  java -jar patient-repo-microService.jar