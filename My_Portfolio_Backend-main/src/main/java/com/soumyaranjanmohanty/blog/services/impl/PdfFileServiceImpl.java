package com.soumyaranjanmohanty.blog.services.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.soumyaranjanmohanty.blog.entites.PdfFile;
import com.soumyaranjanmohanty.blog.payloads.PdfFilesDto;
import com.soumyaranjanmohanty.blog.repositories.PdfFileRepo;
import com.soumyaranjanmohanty.blog.services.PdfFileService;

@Service
public class PdfFileServiceImpl implements PdfFileService {

    private final String basePath;
    private final PdfFileRepo pdfFileRepository;

    public PdfFileServiceImpl(@Value("${pdf.base-path}") String basePath, PdfFileRepo pdfFileRepository) {
        this.basePath = basePath;
        this.pdfFileRepository = pdfFileRepository;
    }

    @Override
    public PdfFilesDto createPdfFile(MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || !originalFileName.endsWith(".pdf")) {
            throw new IOException("Only PDF files are allowed.");
        }

        String uniqueFileName = UUID.randomUUID().toString() + ".pdf";
        Path uploadDir = Paths.get(basePath);
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        Path filePath = uploadDir.resolve(uniqueFileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        PdfFile pdfFile = new PdfFile();
        pdfFile.setUniqueFileName(uniqueFileName);
        pdfFile.setOriginalFileName(originalFileName);
        pdfFile.setUploadTime(LocalDateTime.now());

        PdfFile savedPdfFile = pdfFileRepository.save(pdfFile);
        return convertToDto(savedPdfFile);
    }

    @Override
    public PdfFilesDto updatePdfFile(MultipartFile file, Long id) throws IOException {
        PdfFile existingPdfFile = pdfFileRepository.findById(id)
                .orElseThrow(() -> new FileNotFoundException("File not found with ID: " + id));

        // Delete old file from storage
        deleteFileFromStorage(existingPdfFile.getUniqueFileName());

        // Upload the new file
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || !originalFileName.endsWith(".pdf")) {
            throw new IOException("Only PDF files are allowed.");
        }

        String uniqueFileName = UUID.randomUUID().toString() + ".pdf";
        Path uploadDir = Paths.get(basePath);
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        Path filePath = uploadDir.resolve(uniqueFileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Update entity fields
        existingPdfFile.setUniqueFileName(uniqueFileName);
        existingPdfFile.setOriginalFileName(originalFileName);
        existingPdfFile.setUploadTime(LocalDateTime.now());

        PdfFile updatedPdfFile = pdfFileRepository.save(existingPdfFile);
        return convertToDto(updatedPdfFile);
    }

    @Override
    public void deletePdfFile(Long id) throws IOException {
        PdfFile existingPdfFile = pdfFileRepository.findById(id)
                .orElseThrow(() -> new FileNotFoundException("File not found with ID: " + id));

        // Delete file from storage
        deleteFileFromStorage(existingPdfFile.getUniqueFileName());

        // Delete record from the database
        pdfFileRepository.delete(existingPdfFile);
    }

    @Override
    public PdfFilesDto getPdfFile(Long id) {
        PdfFile pdfFile = pdfFileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PDF file not found with ID: " + id));
        return convertToDto(pdfFile);
    }

    @Override
    public InputStream getPdf(String uniqueFileName) throws FileNotFoundException {
        Path filePath = Paths.get(basePath, uniqueFileName);
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("File not found: " + uniqueFileName);
        }
        return new FileInputStream(filePath.toFile());
    }

    private void deleteFileFromStorage(String uniqueFileName) throws IOException {
        Path filePath = Paths.get(basePath, uniqueFileName);
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        } else {
            throw new FileNotFoundException("File not found: " + uniqueFileName);
        }
    }

    private PdfFilesDto convertToDto(PdfFile pdfFile) {
        PdfFilesDto dto = new PdfFilesDto();
        dto.setId(pdfFile.getId());
        dto.setUniqueFileName(pdfFile.getUniqueFileName());
        dto.setOriginalFileName(pdfFile.getOriginalFileName());
        dto.setUploadTime(pdfFile.getUploadTime());
        return dto;
    }

    private PdfFile convertToEntity(PdfFilesDto pdfFilesDto) {
        PdfFile pdfFile = new PdfFile();
        pdfFile.setId(pdfFilesDto.getId());
        pdfFile.setUniqueFileName(pdfFilesDto.getUniqueFileName());
        pdfFile.setOriginalFileName(pdfFilesDto.getOriginalFileName());
        pdfFile.setUploadTime(pdfFilesDto.getUploadTime());
        return pdfFile;
    }
}
