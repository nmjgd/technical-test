package com.example.technicaltest;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties("app")
@Setter
@Getter
public class TechnicalTestProperties {
    private Api api;

    @Setter
    @Getter
    public static class Api {
        private ImageAnalysis imageAnalysis;

        @Setter
        @Getter
        public static class ImageAnalysis {
            private String endpointUrl;
        }
    }

}
