package com.example.demo.health;

import com.fasterxml.jackson.annotation.*;
import jakarta.annotation.Generated;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "status",
        "timestamp",
        "someConfig",
        "metadata"
})
@Generated("jsonschema2pojo")

@Data
public class HealthDetailsResponse implements Serializable {

    @Serial
    private final static long serialVersionUID = 6798549695119061947L;
    @JsonProperty("status")
    private String status;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("someConfig")
    private String someConfig;
    @JsonProperty("metadata")
    private Metadata metadata;
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonPropertyOrder({
            "service-name",
            "version",
            "environment"
    })
    @Generated("jsonschema2pojo")

    @Data
    public static class Metadata implements Serializable {

        @Serial
        private final static long serialVersionUID = -781085023521159456L;
        @JsonProperty("service-name")
        private String serviceName;
        @JsonProperty("version")
        private String version;
        @JsonProperty("environment")
        private String environment;
        @JsonIgnore
        private final Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

        @JsonAnyGetter
        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        @JsonAnySetter
        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

}
