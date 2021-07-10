package com.example.technicaltest.service.impl;

import java.math.BigDecimal;

import com.example.technicaltest.TechnicalTestProperties;
import com.example.technicaltest.service.ImageAnalysisService;
import com.example.technicaltest.service.impl.ImageAnalysisServiceImpl.AnalysisLogic.AnalysisResponseBody.EstimatedData;
import com.example.technicaltest.util.Utils;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.Data;

@Service
public class ImageAnalysisServiceImpl implements ImageAnalysisService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private TechnicalTestProperties properties;

    public AnalysisResultDto analyze(AnalysisDto dto) {
        return new AnalysisLogic(this).execute(dto);
    }

    public static class AnalysisLogic {
        private final static Logger logger = LoggerFactory.getLogger(AnalysisLogic.class);
        private final ImageAnalysisServiceImpl enclosing;

        private AnalysisLogic(ImageAnalysisServiceImpl enclosing) {
            this.enclosing = enclosing;
        }

        private AnalysisResultDto execute(AnalysisDto dto) {
            HttpEntity<AnalysisRequestBody> req = createRequest(dto);

            logger.debug("Request : {}", req.toString());

            long reqTs = Utils.now();

            ResponseEntity<AnalysisResponseBody> res = request(req);

            long resTs = Utils.now();

            logger.debug("Response : {}", res.toString());

            return createResult(res, reqTs, resTs);
        }

        private ResponseEntity<AnalysisResponseBody> request(HttpEntity<AnalysisRequestBody> request) {
            String url = enclosing.properties.getApi().getImageAnalysis().getEndpointUrl();
            return enclosing.restTemplate.postForEntity(url, request, AnalysisResponseBody.class);
        }

        private HttpEntity<AnalysisRequestBody> createRequest(AnalysisDto dto) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            AnalysisRequestBody reqBody = new AnalysisRequestBody();
            reqBody.setImagePath(dto.getImagePath());

            return new HttpEntity<>(reqBody, headers);
        }

        private AnalysisResultDto createResult(ResponseEntity<AnalysisResponseBody> res, long reqTs, long resTs) {
            AnalysisResultDto result = new AnalysisResultDto();

            AnalysisResponseBody resBody = res.getBody();
            EstimatedData estData = resBody.getEstimatedData();

            result.setSuccess(resBody.isSuccess());
            result.setMessage(resBody.getMessage());
            result.setRequestTimestamp(reqTs);
            result.setResponseTimestamp(resTs);
            result.getEstimatedData().setClassValue(estData.getClassValue());
            result.getEstimatedData().setConfidence(estData.getConfidence());

            return result;
        }

        @Data
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        public static class AnalysisRequestBody {
            private String imagePath;
        }

        @Data
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        public static class AnalysisResponseBody {
            private boolean success;
            private String message;
            private EstimatedData estimatedData;

            @Data
            public static class EstimatedData {
                @JsonProperty("class")
                private Integer classValue;
                private BigDecimal confidence;
            }
        }
    }

}
