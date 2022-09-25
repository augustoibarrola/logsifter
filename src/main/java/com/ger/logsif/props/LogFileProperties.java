package com.ger.logsif.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "logfile")
public class LogFileProperties {
	
	private String uploadRepo;
	
	public String getUploadRepo() {
		return this.uploadRepo;
	}
	
	public void setUploadRepo(String repo) {
		this.uploadRepo = repo;
	}

}
