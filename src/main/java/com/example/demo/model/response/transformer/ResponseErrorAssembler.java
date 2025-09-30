package com.example.demo.model.response.transformer;

import com.example.demo.model.response.model.ResponseError;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseErrorAssembler {

    public static ResponseError toResponseError(Map<String, Object> errors, String message, HttpStatus status) {
        return ResponseError.builder()
                .error(status.getReasonPhrase())
                .message(message)
                .errors(errors)
                .build();
    }

    public static ResponseError toResponseError(String message, HttpStatus status) {
        return ResponseError.builder()
                .error(status.getReasonPhrase())
                .message(message)
                .build();

    }
}
