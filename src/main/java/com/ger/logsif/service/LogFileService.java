package com.ger.logsif.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.HashMap;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ger.logsif.props.LogFileProperties;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

@Service
public class LogFileService {

	private final SifterService sifterService;

//	private final Path fileStorageLocation;

	public LogFileService(LogFileProperties logfileProperties) {

		this.sifterService = new SifterService(logfileProperties);

	}

	public String storeLogFile(MultipartFile file) {

		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
//                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = this.sifterService.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return fileName;
		} catch (IOException ex) {
//            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
			return null;
		}
	}

	public String storeLogFile(MultipartFile file, String key) throws FileNotFoundException {

		HashMap<Integer, String> foundKeys = this.sifterService.findKeyInFile(file, key);

		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
//                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = this.sifterService.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return fileName;
		} catch (IOException ex) {
//            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
			return null;
		}
	}

	public Resource loadFileAsResource(String fileName) {
		try {
			Path filePath = this.sifterService.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
//                throw new MyFileNotFoundException("File not found " + fileName);
				return null;
			}
		} catch (Exception ex) {
//        } catch (MalformedURLException ex) {
//            throw new MyFileNotFoundException("File not found " + fileName, ex);
			return null;
		}
	}



}
