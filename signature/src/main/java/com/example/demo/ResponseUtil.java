package com.example.demo;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;


    @Service
    public class ResponseUtil {


        public static ResponseEntity<String> getResponseEntity(Object object) {
            HttpHeaders headers = new HttpHeaders();
            MediaType mt = new MediaType("text", "html", Charset.forName("UTF-8"));
            headers.setContentType(mt);
            String body = JsonUtil.toJson(object);
            ResponseEntity<String> re = new ResponseEntity<String>(body, headers,
                    HttpStatus.OK);
            return re;
        }
    }
