FROM openjdk:11
WORKDIR /usr/app
COPY build/libs/repoCentral-0.0.1-SNAPSHOT.jar mediscreen-patient-repo-microService.jar
CMD  java -jar mediscreen-patient-repo-microService.jar