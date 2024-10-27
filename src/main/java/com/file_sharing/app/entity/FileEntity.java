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
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private UserEntity uploadBy;
    private Instant uploadDate;
    private Instant expiryDate;
    @Lob
    @Column(name = "data",columnDefinition = "LONGBLOB")
    private byte[] fileData;
}
