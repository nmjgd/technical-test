package com.example.technicaltest;

import com.example.technicaltest.service.AnalysisLogService;
import com.example.technicaltest.service.AnalysisLogService.CreationLogDto;
import com.example.technicaltest.service.ImageAnalysisService;
import com.example.technicaltest.service.ImageAnalysisService.AnalysisDto;
import com.example.technicaltest.service.ImageAnalysisService.AnalysisResultDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

@Component
public class TechnicalTest {
    @Autowired
    private ImageAnalysisService imageAnalysisService;
    @Autowired
    private AnalysisLogService analysisLogService;

    public void execute(ApplicationArguments args) throws Exception {
        String imagePath = args.getNonOptionArgs().get(0);
        AnalysisResultDto resultDto = analyzeImage(imagePath);
        createAnalysisLog(imagePath, resultDto);
    }

    private AnalysisResultDto analyzeImage(String imagePath) {
        AnalysisDto dto = new AnalysisDto();
        dto.setImagePath(imagePath);
        return imageAnalysisService.analyze(dto);
    }

    private void createAnalysisLog(String imagePath, AnalysisResultDto resultDto) {
        CreationLogDto createLog = new CreationLogDto();
        createLog.setImagePath(imagePath);
        createLog.setSuccess(resultDto.isSuccess());
        createLog.setMessage(resultDto.getMessage());
        createLog.getEstimatedData().setClassValue(resultDto.getEstimatedData().getClassValue());
        createLog.getEstimatedData().setConfidence(resultDto.getEstimatedData().getConfidence());
        createLog.setRequestTimestamp(resultDto.getRequestTimestamp());
        createLog.setResponseTimestamp(resultDto.getResponseTimestamp());
        analysisLogService.createLog(createLog);
    }

}
