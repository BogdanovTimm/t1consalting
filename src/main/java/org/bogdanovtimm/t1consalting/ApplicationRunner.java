package org.bogdanovtimm.t1consalting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// [v] Add swagger
// [ ] Save swagger as PDF
// [v] Integration test
// [v] Unit test for service
// [v] Different REST methods (for case sensitive and case insensetive)
// [ ] Docker compose

@SpringBootApplication
public class ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationRunner.class, args);
	}
}
