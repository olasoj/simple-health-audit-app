package com.example.demo.audit;


import com.fasterxml.jackson.annotation.*;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "eventType",
        "description",
        "userId"
})
@Generated("jsonschema2pojo")
@Data
public class AuditRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -6598322844539752951L;
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new LinkedHashMap<>();
    @JsonProperty("eventType")
    @NotBlank(message = "Enter eventType")
    private String eventType;
    @JsonProperty("description")
    private String description;
    @JsonProperty("userId")
    @NotBlank(message = "Enter userId")
    private String userId;

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
