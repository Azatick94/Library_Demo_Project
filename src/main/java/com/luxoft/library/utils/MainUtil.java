package com.luxoft.library.utils;

import com.luxoft.library.utils.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static String parserShortClassName(String text) {
        String regex = "\\.[\\w]+\\(";
        Matcher m = Pattern.compile(regex).matcher(text);
        String result = m.find() ? m.group() : "";
        return result.substring(1, result.length() - 1);
    }

    public static String parserLongClassName(String text) {
        String regex = "com[\\w\\.]+[\\w]+\\(";
        Matcher m = Pattern.compile(regex).matcher(text);
        String result = m.find() ? m.group() : "";
        int indexOf = result.lastIndexOf(".");
        return result.substring(0, indexOf);
    }
}
