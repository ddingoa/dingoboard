package com.bbodek.meboard.dto.util;

import com.bbodek.meboard.domain.util.FileEntity;
import lombok.*;

import java.sql.Blob;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class FileDto {
    private Long id;
    private String origFilename;
    private String filename;
    private String filePath;
    private Long boardid;

    private Blob dfImg;

    public FileEntity toEntity() {
        FileEntity build = FileEntity.builder()
                .id(id)
                .origFilename(origFilename)
                .filename(filename)
                .filePath(filePath)
                .boardid(boardid)
                .dfImg(dfImg)
                .build();
        return build;
    }

    @Builder
    public FileDto(Long id, String origFilename, String filename, String filePath, Long boardid,Blob dfImg) {
        this.id = id;
        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
        this.boardid = boardid;
        this.dfImg = dfImg;
    }
}