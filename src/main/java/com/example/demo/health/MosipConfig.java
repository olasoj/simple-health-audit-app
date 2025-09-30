package com.example.demo.health;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class MosipConfig {

    @Value(value = "${mosip.id.auth.someConfig}")
    private String someConfig;
}
