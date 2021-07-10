package com.example.technicaltest.service.impl;

import com.example.technicaltest.jooq.tables.AiAnalysisLog;
import com.example.technicaltest.jooq.tables.records.AiAnalysisLogRecord;
import com.example.technicaltest.service.AnalysisLogService;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.types.UInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnalysisLogServiceImpl implements AnalysisLogService {
    @Autowired
    private DSLContext dsl;

    @Override
    @Transactional
    public void createLog(CreationLogDto dto) {
        dsl.transaction(configuration -> {
            AiAnalysisLogRecord rec = DSL.using(configuration).newRecord(AiAnalysisLog.AI_ANALYSIS_LOG);
            rec.setImagePath(dto.getImagePath());
            rec.setSuccess(String.valueOf(dto.isSuccess()));
            rec.setMessage(dto.getMessage());
            rec.setClass_(dto.getEstimatedData().getClassValue());
            rec.setConfidence(dto.getEstimatedData().getConfidence());
            rec.setRequestTimestamp(UInteger.valueOf(dto.getRequestTimestamp()));
            rec.setResponseTimestamp(UInteger.valueOf(dto.getResponseTimestamp()));
            rec.store();
        });
    }

}
