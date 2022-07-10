package com.mediscreen.repoCentral.customExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PatientLastNameNotFound extends RuntimeException{
    public PatientLastNameNotFound (String msg){
        super (msg);
    }
}
