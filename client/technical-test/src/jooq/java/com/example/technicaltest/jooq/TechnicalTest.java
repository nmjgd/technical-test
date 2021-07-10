/*
 * This file is generated by jOOQ.
 */
package com.example.technicaltest.jooq;


import com.example.technicaltest.jooq.tables.AiAnalysisLog;

import java.util.Arrays;
import java.util.List;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TechnicalTest extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>technical_test</code>
     */
    public static final TechnicalTest TECHNICAL_TEST = new TechnicalTest();

    /**
     * The table <code>technical_test.ai_analysis_log</code>.
     */
    public final AiAnalysisLog AI_ANALYSIS_LOG = AiAnalysisLog.AI_ANALYSIS_LOG;

    /**
     * No further instances allowed
     */
    private TechnicalTest() {
        super("technical_test", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.<Table<?>>asList(
            AiAnalysisLog.AI_ANALYSIS_LOG);
    }
}