package com.example.demo.exception;

import com.example.demo.model.response.ResponseModel;
import com.example.demo.model.response.model.Response;
import com.example.demo.model.response.model.ResponseError;
import com.example.demo.model.response.transformer.ResponseAssembler;
import com.example.demo.model.response.transformer.ResponseErrorAssembler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@AllArgsConstructor
public class ValidationExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationExceptionHandler.class);
    private final ResponseModel responseModel;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void handleMethodArgumentNotValid(HttpServletRequest request, HttpServletResponse response, MethodArgumentNotValidException ex) {
        var errors = getErrors(ex);
        ResponseError responseError = ResponseErrorAssembler.toResponseError(errors, "Validation failed", HttpStatus.BAD_REQUEST);
        Response<ResponseError> errorResponse = ResponseAssembler.toResponse(HttpStatus.BAD_REQUEST, responseError);
        responseModel.writeResponse(response, errorResponse);
    }

    private Map<String, Object> getErrors(MethodArgumentNotValidException ex) {
        var errors = new HashMap<String, Object>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public void handleHttpMessageNotReadableException(HttpServletRequest request, HttpServletResponse response, HttpMessageNotReadableException ex) {
        LOGGER.error(ex.getMessage(), ex);
        ResponseError responseError = ResponseErrorAssembler.toResponseError("No/Invalid Request body provided", HttpStatus.BAD_REQUEST);
        Response<ResponseError> errorResponse = ResponseAssembler.toResponse(HttpStatus.BAD_REQUEST, responseError);
        responseModel.writeResponse(response, errorResponse);
    }
}
