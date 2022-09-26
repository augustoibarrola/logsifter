package com.ger.logsif.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ger.logsif.props.LogFileProperties;

@Service
public class SifterService {
	
	final Path fileStorageLocation;
	
	public SifterService(LogFileProperties logfileProperties)
	{
		this.fileStorageLocation = Paths.get(logfileProperties.getUploadRepo()).toAbsolutePath().normalize();
		
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
//            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
		}
	}
	
	public void useContainsMethod(MultipartFile file, String key) throws FileNotFoundException{
		
		try {
			
			Path f = this.fileStorageLocation.resolve(StringUtils.cleanPath(file.getOriginalFilename()));
			File logfile = new File(f.toString());
			
			FileInputStream inputStream = new FileInputStream(logfile);
			Scanner logfileScanner = new Scanner(inputStream, "UTF-8");

			int lines = 1;
			int foundOnLine = 0;
			
			System.out.println("\nReading from file located at " + f + "...\n");
			System.out.println("Searching for '" + key + "' keyword at " + f + "...\n");

			while (logfileScanner.hasNextLine()) {

				String line = logfileScanner.nextLine();

				String readableLine = new String(line);

				if (readableLine.contains(key)) {

					System.out.print(lines + " | " + readableLine + "\n");

				} 

				lines++;

			}

			System.out.println("Total lines: " + lines + "\n");
			System.out.println("Keyword found on a total of " + foundOnLine + " lines\n");
			
		} catch (FileNotFoundException exception) {
			exception.printStackTrace();
		}
		
		
	}
	
	public void useContainsMethod(String readableLine, String key, int lines) {
		if (readableLine.contains(key)) {

			System.out.print(lines + " | " + readableLine + "\n");

		} 
	}
	

}
