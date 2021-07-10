package com.example.technicaltest;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import com.example.technicaltest.jooq.tables.AiAnalysisLog;
import com.example.technicaltest.jooq.tables.records.AiAnalysisLogRecord;
import com.example.technicaltest.util.Utils;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.types.UInteger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpServerErrorException;

/**
 * テスト対象機能が小規模のため、レイヤー毎のテストはせずに、一気通貫でテストを実施。
 */
@SpringBootTest
@ActiveProfiles("test")
class TechnicalTestTest {
	@Autowired
	private TechnicalTest technicalTest;
	@Autowired
	private DSLContext dsl;

	@Test
	void testSuccess() throws Exception {
		UInteger nowBefore = UInteger.valueOf(Utils.now());
		int countBefore = countAiAnalysisLog();
		String imagePath = "/dir/analyzable.jpg";

		execute(imagePath);

		assertThat(countAiAnalysisLog()).isEqualTo(countBefore + 1);

		AiAnalysisLogRecord record = selectAiAnalysisLog();
		assertThat(record.getImagePath()).isEqualTo(imagePath);
		assertThat(record.getSuccess()).isEqualTo("true");
		assertThat(record.getMessage()).isEqualTo("success");
		assertThat(record.getClass_()).isEqualTo(3);
		assertThat(record.getConfidence()).isEqualTo(new BigDecimal("0.8683"));
		assertThat(record.getRequestTimestamp()).isGreaterThanOrEqualTo(nowBefore);
		assertThat(record.getResponseTimestamp()).isGreaterThanOrEqualTo(nowBefore);
		assertThat(record.getResponseTimestamp()).isGreaterThanOrEqualTo(record.getRequestTimestamp());
	}

	@Test
	void testFailure() throws Exception {
		UInteger nowBefore = UInteger.valueOf(Utils.now());
		int countBefore = countAiAnalysisLog();
		String imagePath = "/dir/analyzable.txt";

		execute(imagePath);

		assertThat(countAiAnalysisLog()).isEqualTo(countBefore + 1);

		AiAnalysisLogRecord record = selectAiAnalysisLog();
		assertThat(record.getImagePath()).isEqualTo(imagePath);
		assertThat(record.getSuccess()).isEqualTo("false");
		assertThat(record.getMessage()).isEqualTo("Error:E50012");
		assertThat(record.getClass_()).isNull();
		assertThat(record.getConfidence()).isNull();
		assertThat(record.getRequestTimestamp()).isGreaterThanOrEqualTo(nowBefore);
		assertThat(record.getResponseTimestamp()).isGreaterThanOrEqualTo(nowBefore);
		assertThat(record.getResponseTimestamp()).isGreaterThanOrEqualTo(record.getRequestTimestamp());
	}

	@Test
	void testServerError() throws Exception {
		int countBefore = countAiAnalysisLog();
		String imagePath = "500";

		ApplicationArguments args = new DefaultApplicationArguments(imagePath);
		try {
			technicalTest.execute(args);
		} catch (HttpServerErrorException e) {
			assertThat(e.getRawStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());

			assertThat(countAiAnalysisLog()).isEqualTo(countBefore);
		}
	}

	private void execute(String imagePath) throws Exception {
		ApplicationArguments args = new DefaultApplicationArguments(imagePath);
		technicalTest.execute(args);
	}

	private int countAiAnalysisLog() {
		return dsl.fetchCount(DSL.selectFrom(AiAnalysisLog.AI_ANALYSIS_LOG));
	}

	private AiAnalysisLogRecord selectAiAnalysisLog() {
		return dsl.select(AiAnalysisLog.AI_ANALYSIS_LOG.fields()).from(AiAnalysisLog.AI_ANALYSIS_LOG)
				.orderBy(AiAnalysisLog.AI_ANALYSIS_LOG.REQUEST_TIMESTAMP.desc()).limit(1)
				.fetchOneInto(AiAnalysisLogRecord.class);
	}

}
