package com.bbodek.meboard.domain.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.sql.Blob;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Setter
@Table(name="meBoardfile")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String origFilename;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private Long boardid;

    @Lob
    @Column
    @JsonProperty("dfImg")
    private Blob dfImg;

    @Builder
    public FileEntity(Long id, String origFilename, String filename, String filePath,Long boardid,Blob dfImg) {
        this.id = id;
        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
        this.boardid = boardid;
        this.dfImg = dfImg;
    }
}