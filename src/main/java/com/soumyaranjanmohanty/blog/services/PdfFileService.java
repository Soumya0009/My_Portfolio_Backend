package com.soumyaranjanmohanty.blog.services;

import com.soumyaranjanmohanty.blog.payloads.PdfFilesDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface PdfFileService {
    
    // Create PDF File
    PdfFilesDto createPdfFile(MultipartFile file) throws IOException;
    
    // Update PDF File
    PdfFilesDto updatePdfFile(MultipartFile file, Long id) throws IOException;
    
    // Delete PDF File
    void deletePdfFile(Long id) throws IOException;
    
    // Get PDF File Metadata
    PdfFilesDto getPdfFile(Long id);
    
    // Get PDF File as Stream
    InputStream getPdf(String uniqueFileName) throws IOException;
}