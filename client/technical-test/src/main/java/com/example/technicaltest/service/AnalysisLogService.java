package com.example.technicaltest.service;

import java.math.BigDecimal;

import lombok.Data;

public interface AnalysisLogService {

    void createLog(CreationLogDto dto);

    @Data
    public static class CreationLogDto {
        private String imagePath;
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
