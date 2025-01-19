package com.soumyaranjanmohanty.blog.payloads;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PdfFilesDto {
    
    private Long id;
    private String uniqueFileName;
    private String originalFileName;
    private LocalDateTime uploadTime;
}
