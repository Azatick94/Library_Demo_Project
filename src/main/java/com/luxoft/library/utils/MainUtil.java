package com.luxoft.library.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class MainUtil {

    public static ResponseEntity<HttpStatus> processServiceStatus(boolean b) {
        if (b) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
