package com.assessment.vmware.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class BaseController
{
    List<String> errorMessages = new ArrayList<>();

    public  <T> ResponseEntity<T> getApiResponse(T data){
        if(errorMessages.size()!=0){
            ResponseEntity<T>responseEntity = new ResponseEntity<T>(HttpStatus.BAD_REQUEST);
            return responseEntity;
        }

        ResponseEntity<T>responseEntity = new ResponseEntity<T>(data,HttpStatus.ACCEPTED);
        errorMessages.clear();
        return responseEntity;
    }
}
