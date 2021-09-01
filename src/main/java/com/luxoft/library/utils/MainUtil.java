package com.luxoft.library.utils;

import com.luxoft.library.utils.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class MainUtil {

    public static ResponseEntity<HttpStatus> processServiceStatus(boolean b) {
        if (b) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public static <T> T processOptional(Optional<T> t, Integer id) {
        if (t.isPresent()) {
            return t.get();
        } else {
            throw new NotFoundException("Id=" + id + " not found!!!");
        }
    }
}
