package com.mediscreen.repoCentral.controller;

import com.mediscreen.repoCentral.customExceptions.PatientAlreadyExistException;
import com.mediscreen.repoCentral.customExceptions.PatientIdNotFoundException;
import com.mediscreen.repoCentral.customExceptions.PatientLastNameNotFound;
import com.mediscreen.repoCentral.model.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler {

    private String BAD_REQUEST = "BAD_REQUEST";

    private String INCORRECT_REQUEST ="INCORRECT_REQUEST";

    @ExceptionHandler (PatientAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handlePatientAlreadyExist (PatientAlreadyExistException ex){
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(BAD_REQUEST, details);
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler (PatientIdNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePatientNotFound (PatientIdNotFoundException ex){
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(INCORRECT_REQUEST, details);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler (PatientLastNameNotFound.class)
    public ResponseEntity<ErrorResponse> handlePatientLastNameNotFound (PatientLastNameNotFound ex){
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(INCORRECT_REQUEST, details);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
