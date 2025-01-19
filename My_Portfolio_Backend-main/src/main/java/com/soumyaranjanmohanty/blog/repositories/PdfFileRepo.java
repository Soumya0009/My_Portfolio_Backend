package com.soumyaranjanmohanty.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soumyaranjanmohanty.blog.entites.PdfFile;

@Repository
public interface PdfFileRepo extends JpaRepository<PdfFile, Long>{
    PdfFile findByUniqueFileName(String uniqueFileName);
}
