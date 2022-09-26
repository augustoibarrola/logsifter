package com.ger.logsif.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ger.logsif.props.LogFileProperties;

@Service
public class SifterService {

	final Path fileStorageLocation;

	/*
	 * Constructor
	 * 
	 */
	public SifterService(LogFileProperties logfileProperties) {
		this.fileStorageLocation = Paths.get(logfileProperties.getUploadRepo()).toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
//            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
		}
	}

	public HashMap<Integer, String> findKeyInFile(MultipartFile file, String key) throws FileNotFoundException {
		HashMap<Integer, String> foundKeys = new HashMap<Integer, String>();

		try {
			this.useContainsMethodWithString(file, key);
			this.siftLog(file, key);
			return foundKeys;

		} catch (IOException exception) {
			exception.printStackTrace();
		}

		return foundKeys;
	}

	private void useContainsMethodWithString(MultipartFile file, String key) throws FileNotFoundException {
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
		} catch (FileNotFoundException exception) {
			exception.printStackTrace();
		}

	}

	private void useContainsMethodWithString(String readableLine, String key, int lines) {
		if (readableLine.contains(key)) {

			System.out.print(lines + " | " + readableLine + "\n");

		}
	}

	/*
	 * NOT COMPLETE
	 */
//	private void useContainsMethodWithStringBuilder(MultipartFile file, String key) throws FileNotFoundException {
//
//		try {
//
//			Path f = this.fileStorageLocation.resolve(StringUtils.cleanPath(file.getOriginalFilename()));
//			File logfile = new File(f.toString());
//			
//			/*
//			 * Switched from FileInputStream to FileReader as:
//			 * "FileInputStream is meant for reading streams of raw bytes such as image data. 
//			 * For reading streams of characters, consider using FileReader."
//			 */
//			FileReader inputReader = new FileReader(logfile);
//			Scanner logfileScanner = new Scanner(f, "UTF-8");
//
//			int lines = 1;
//			int foundOnLine = 0;
//
//			System.out.println("\nReading from file located at " + f + "...\n");
//			System.out.println("Searching for '" + key + "' keyword at " + f + "...\n");
//
//			while (logfileScanner.hasNextLine()) {
//
//				String line = logfileScanner.nextLine();
//				StringBuilder readableLine = new StringBuilder(line);
//
//				if (readableLine.contains(key)) { System.out.print(lines + " | " + readableLine + "\n");}
//
//				lines++;
//
//			}
//
//			System.out.println("Total lines: " + lines + "\n");
//			System.out.println("Keyword found on a total of " + foundOnLine + " lines\n");
//
//		} catch (FileNotFoundException exception) {
//			exception.printStackTrace();
//		}
//
//	}

	public void siftLog(MultipartFile file, String key) throws IOException {
		
		Path f = this.fileStorageLocation.resolve(StringUtils.cleanPath(file.getOriginalFilename()));
		File logfile = new File(f.toString());
		LineIterator lineIterator = FileUtils.lineIterator(logfile, "UTF-8");
		String[] keyArray = this.convertArray(key);
		
		String keyLetter = keyArray[0];
				
		
		try {
			while (lineIterator.hasNext()) {
				List<String> lineArrList = lineToList(lineIterator);
				
				for (int a = 0; a < lineArrList.size() - 1; a++) {
					/* TODO
					 * ~~Iterating over each character of the current line
					 * in the text file, at the current iteration
					 * the character matches the first character of the given 
					 * keyword, then go n index ahead (where n = to the difference 
					 * of the last index of the keyword with the first) and see if 
					 * the character found at that index matches the last 
					 * character of the keyword.
					 * If they do, iterate over this subsection to ensure they are the 
					 * same keyword
					 * if they are, do something!
					 * If they are not the same sequence of characters, move on to the following iteration. 
					 */
					
					if(lineArrList.get(a).equals(keyLetter)) {
						int el = a + (keyArray.length - 1);
						if(lineArrList.get(el).equals(keyArray[keyArray.length - 1])){
							//iterate over the strings in between the 
							// two matching strings at either end of the 
							// given key to make sure that it is indeed the right word. 
							
						}
						
					}
				}

			}
		} finally {
			lineIterator.close();
		}

	}
	
	private List<String> lineToList(LineIterator lineIterator) {

		char[] line = lineIterator.nextLine().trim().toCharArray();
		
		List<String> lineArrList = new ArrayList<String>();
		
		for(int i = 0; i < line.length - 1; i++) {lineArrList.add(i, Character.toString(line[i]));}
		
		return lineArrList;

	}
	
	private String[] convertArray(String key) {
		char[] keyCharArray = key.trim().toCharArray();
		String[] keyStringArray = new String[keyCharArray.length];
		
		for(int i = 0; i < keyCharArray.length -1; i++) {keyStringArray[i] = Character.toString(keyCharArray[i]);}
		
		return keyStringArray;
	}

}
