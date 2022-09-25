package com.ger.logsif.service;

import org.springframework.stereotype.Service;

@Service
public class SifterService {
	
	public void useContainsMethod(String readableLine, String key, int lines) {
		if (readableLine.contains(key)) {

			System.out.print(lines + " | " + readableLine + "\n");

		} 
	}

}
