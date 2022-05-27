package com.mediscreen.repoCentral.customExceptions;

public class PatientAlreadyExistException extends Exception {
    public PatientAlreadyExistException (String msg){
        super (msg);
    }
}
