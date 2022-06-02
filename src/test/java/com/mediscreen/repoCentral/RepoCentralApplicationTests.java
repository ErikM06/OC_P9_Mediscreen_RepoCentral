package com.mediscreen.repoCentral;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations= "classpath:application-test.properties")
class RepoCentralApplicationTests {

	@Test
	void contextLoads() {
	}

}
