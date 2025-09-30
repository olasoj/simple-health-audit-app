package com.example.demo.model.response.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.annotation.Generated;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"status", "timestamp", "statusCode", "data"})
@Generated("jsonschema2pojo")
public class Response<T extends Serializable> implements Serializable {

    @Serial
    private static final  long serialVersionUID = -4886351106258078331L;

    @JsonProperty("status")
    private ResponseStatus responseStatus;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("statusCode")
    private Integer statusCode;

    @JsonProperty("data")
    private T data;

    public Response(ResponseBuilder<T> responseBuilder) {
        this.responseStatus = responseBuilder.responseStatus;
        this.timestamp = responseBuilder.timestamp;
        this.statusCode = responseBuilder.statusCode;
        this.data = responseBuilder.data;
    }

    public static <T extends Serializable> ResponseBuilder<T> builder(){
        return new ResponseBuilder<>();
    }

    public static class ResponseBuilder<B extends Serializable> {
        private ResponseStatus responseStatus;
        private String timestamp;
        private Integer statusCode;
        private B data;

        public ResponseBuilder<B> withResponseStatus(ResponseStatus responseStatus) {
            this.responseStatus = responseStatus;
            return this;
        }

        public ResponseBuilder<B> withTimestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ResponseBuilder<B> withStatusCode(Integer statusCode) {
            this.statusCode = statusCode;
            return this;
        }


        public ResponseBuilder<B> withData(B data) {
            this.data = data;
            return this;
        }

        public Response<B> build() {
            return new Response<>(this);
        }
    }
}
