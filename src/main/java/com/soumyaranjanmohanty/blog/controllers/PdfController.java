package com.soumyaranjanmohanty.blog.controllers;

import com.soumyaranjanmohanty.blog.payloads.PdfFilesDto;
import com.soumyaranjanmohanty.blog.services.PdfFileService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    private final PdfFileService pdfFileService;

    public PdfController(PdfFileService pdfFileService) {
        this.pdfFileService = pdfFileService;
    }

    // Create PDF File
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/upload")
    public ResponseEntity<PdfFilesDto> uploadPdf(@RequestParam("file") MultipartFile file) {
        try {
            PdfFilesDto uploadedPdf = pdfFileService.createPdfFile(file);
            return ResponseEntity.ok(uploadedPdf);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // Update PDF File
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<PdfFilesDto> updatePdf(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            PdfFilesDto updatedPdf = pdfFileService.updatePdfFile(file, id);
            return ResponseEntity.ok(updatedPdf);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // Delete PDF File
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePdf(@PathVariable Long id) {
        try {
            pdfFileService.deletePdfFile(id);
            return ResponseEntity.ok("File deleted successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to delete file: " + e.getMessage());
        }
    }

    // Get PDF File Metadata
    @GetMapping("/{id}")
    public ResponseEntity<PdfFilesDto> getPdfMetadata(@PathVariable Long id) {
        PdfFilesDto pdfFileDto = pdfFileService.getPdfFile(id);
        return ResponseEntity.ok(pdfFileDto);
    }

    // Download PDF File
    @GetMapping("/download/{uniqueFileName}")
    public ResponseEntity<InputStreamResource> downloadPdf(@PathVariable String uniqueFileName) {
        try {
            InputStream pdfStream = pdfFileService.getPdf(uniqueFileName);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + uniqueFileName + "\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(pdfStream));
        } catch (IOException e) {
            return ResponseEntity.status(404).body(null);
        }
    }
}
