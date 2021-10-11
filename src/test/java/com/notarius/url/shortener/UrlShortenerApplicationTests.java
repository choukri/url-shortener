package com.notarius.url.shortener;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UrlShortenerApplicationTests {

	@Test
	void check_that_the_application_can_run_in_test() {
		boolean status = false;
		try {
			UrlShortenerApplication.main(new String[] {});
			status = true;
		} catch (Exception e) {

		}
		assertTrue(status);
	}

}
