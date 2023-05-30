package com.skillCentre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SkillCentreApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkillCentreApplication.class, args);
	}

}
