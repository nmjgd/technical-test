package com.example.technicaltest.service;

import java.math.BigDecimal;

import lombok.Data;

public interface ImageAnalysisService {

    AnalysisResultDto analyze(AnalysisDto dto);

    @Data
    public static class AnalysisDto {
        private String imagePath;
    }

    @Data
    public static class AnalysisResultDto {
        private boolean success;
        private String message;
        private Long requestTimestamp;
        private Long responseTimestamp;
        private EstimatedData estimatedData = new EstimatedData();

        @Data
        public static class EstimatedData {
            private Integer classValue;
            private BigDecimal confidence;
        }
    }

}
