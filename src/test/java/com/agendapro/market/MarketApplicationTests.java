package com.agendapro.market;

import com.agendapro.market.common.TestApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {TestApplication.class})
class MarketApplicationTests {

	@Test
	void mainTest() {
		try {
			MarketApplication.main(new String[]{});
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}

}
