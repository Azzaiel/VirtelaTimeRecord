package net.virtela.TimeRecord;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TimeRecordApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimeRecordApplication.class, args);
	}
	
}
