# OC_P9_Mediscreen_RepoCentral

P9_OC Développez une solution en microservices pour votre client

RepoCentral est le microservice dédié a l'ajout, la modification, la suppression d'info personnels de patients.

## Technologies
Java : 1.8 JDK
Gradle : 7.4.1
Spring : 2.6.6

## Specifications techniques
Mediscreen est composé de 2 microservice :

1. https://github.com/ErikM06/OC_P9_MEDISCREEN_CENTRAL.git

2. https://github.com/ErikM06/OC_P9_Mediscreen_RepoCentral.git

## Lancer l'application

### Gradle :
1. Builder l'application
`$ ./gradle build̀`

2. Run l'application
`$ ./gradle bootRun`

# Endpoints 

    GET - retourne un Patient par son Id 
    http://localhost:8081/patient/getById
    Param : id

    GET - retourne la liste de tous les patients
    http://localhost:8081/patient/getPatientList


    GET - retourne la liste de tous les patients de la famille donnée (famille=famille de   risque)
    http://localhost:8081/patient/getPatientByFamily
    Param : family

    POST - ajoute un Patient au la DB
    http://localhost:8081/patient/add
    Body: Patient

    POST - update un Patient 
    http://localhost:8081/patient/update
    Body : Patient
    
    DELETE - delete un Patient 
    http://localhost:8081/patient/deleteById
    Param : id



