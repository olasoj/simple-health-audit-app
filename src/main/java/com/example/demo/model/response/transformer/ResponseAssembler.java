package com.example.demo.model.response.transformer;

import com.example.demo.model.response.model.Response;
import com.example.demo.model.response.model.ResponseStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.Instant;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseAssembler {

    public static <T extends Serializable> Response<T> toResponse(HttpStatus httpStatus, T data) {
        ResponseStatus responseStatus = responseState(httpStatus);

        Response.ResponseBuilder<T> builder = Response.builder();
        return builder
                .withResponseStatus(responseStatus)
                .withTimestamp(getTimeStamp())
                .withStatusCode(httpStatus.value())
                .withData(data)
                .build();
    }

    private static ResponseStatus responseState(HttpStatus httpStatus) {
        if (HttpStatus.ACCEPTED.equals(httpStatus)) return ResponseStatus.PENDING;
        if (HttpStatus.BAD_GATEWAY.equals(httpStatus)) return ResponseStatus.UNKNOWN;
        return httpStatus.is2xxSuccessful() ? ResponseStatus.SUCCESS : ResponseStatus.FAILED;
    }

    private static String getTimeStamp() {
        return Instant.now().toString();
    }
}
