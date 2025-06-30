package com.file_sharing.app.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
public class FileEntity{
    @Id
    private String fileId;
    private String fileName;
    private String fileType;
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity uploadBy;
    private Instant uploadDate;
    private Instant expiryDate;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "data")
    private byte[] fileData;
}
