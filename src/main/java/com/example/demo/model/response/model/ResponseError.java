package com.example.demo.model.response.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"message", "error"})
@Data
public class ResponseError implements Serializable {


    @JsonProperty("message")
    private String message;

    @JsonProperty("error")
    private String error;

    @JsonProperty("errors")
    private Map<String, Object> errors = null;

    public ResponseError(ResponseErrorBuilder responseErrorBuilder) {
        this.message = responseErrorBuilder.message;
        this.error = responseErrorBuilder.error;
        this.errors = responseErrorBuilder.errors;
    }

    public static ResponseErrorBuilder builder() {
        return new ResponseErrorBuilder();
    }

    public static class ResponseErrorBuilder {
        private String message;
        private String error;
        private Map<String, Object> errors = null;

        public ResponseErrorBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ResponseErrorBuilder error(String error) {
            this.error = error;
            return this;
        }

        public ResponseErrorBuilder errors(Map<String, Object> errors) {
            this.errors = errors;
            return this;
        }

        public ResponseError build() {
            return new ResponseError(this);
        }
    }
}
