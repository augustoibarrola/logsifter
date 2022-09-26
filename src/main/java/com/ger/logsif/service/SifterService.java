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
			this.testReader(file);
			return foundKeys;

		} catch (IOException exception) {
			exception.printStackTrace();
		}

		return foundKeys;
	}

	private void useContainsMethodWithString(MultipartFile file, String key) throws FileNotFoundException {
		System.out.println();
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("useContainsMethodWithString");
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
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
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

	public void testReader(MultipartFile file) throws IOException {
		
		Path f = this.fileStorageLocation.resolve(StringUtils.cleanPath(file.getOriginalFilename()));
		File logfile = new File(f.toString());
		LineIterator lineIterator = FileUtils.lineIterator(logfile, "UTF-8");
		
		try {
			while (lineIterator.hasNext()) {
				List<String> lineArrList = testReaderHelper1(lineIterator);
				for (int a = 0; a < lineArrList.size() - 1; a++) {
//					System.out.println(lineArrList.get(a));
					System.out.println(lineArrList.toString());
				}

			}
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		} finally {
			lineIterator.close();
		}

	}
	
	private List<String> testReaderHelper1(LineIterator lineIterator) {
		String line = lineIterator.nextLine().trim();
		char[] lineCharArr = line.toCharArray();
		List<String> lineArrList = new ArrayList<String>(lineCharArr.length);
		System.out.println(line);
		for(int i = 0; i < lineCharArr.length - 1; i++) {
			lineArrList.add(i, Character.toString(lineCharArr[i]));
		}
		
		return lineArrList;

	}

}
