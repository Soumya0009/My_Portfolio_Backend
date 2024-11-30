package com.soumyaranjanmohanty.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.soumyaranjanmohanty.blog.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		
//		File Name
		String name = file.getOriginalFilename();
//		abc.png
		
//		Random name generated File 
		String randomID = UUID.randomUUID().toString();
		String fileName1 = randomID.concat(name.substring(name.lastIndexOf(".")));
		
		
//		Full path 
		String filePath = path + File.separator + fileName1;
		
//		Create Folder if not Created
		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}
		
//		File copy
		Files.copy(file.getInputStream(), Path.of(filePath));
		
		return fileName1;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		
		String fullPath = path+File.separator+fileName;
		InputStream is = new FileInputStream(fullPath);
//		DB logics to return input stream.
		return is;
	}

}
