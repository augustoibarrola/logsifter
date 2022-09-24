package com.ger.logsif;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.ger.logsif.logfileprops.LogFileProperties;

@SpringBootApplication
@EnableConfigurationProperties({LogFileProperties.class})
public class LogSIfterApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(LogSIfterApplication.class, args);
		
	}

}
