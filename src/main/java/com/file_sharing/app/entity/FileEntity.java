package com.file_sharing.app.entity;

import jakarta.persistence.*;
import lombok.Data;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.time.Instant;

@Data
@Entity
public class FileEntity{
    @Id
    private String fileId;
    private String fileName;
    private String fileType;
    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private UserEntity uploadBy;
    @CreatedDate
    private Instant uploadDate;
    @Lob
    private byte[] fileData;
}
