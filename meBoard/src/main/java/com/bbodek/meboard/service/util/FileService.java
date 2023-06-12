package com.bbodek.meboard.service.util;

import com.bbodek.meboard.domain.util.FileEntity;
import com.bbodek.meboard.dto.util.FileDto;
import com.bbodek.meboard.respository.util.FileRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    private FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Transactional
    public Long saveFile(FileDto fileDto) {
        return fileRepository.save(fileDto.toEntity()).getId();
    }

    @Transactional
    public FileDto getFile(Long id) throws SQLException {
        FileEntity file = fileRepository.findByBoardid(id);
        FileDto fileDto = FileDto.builder()
                .id(id)
                .origFilename(file.getOrigFilename())
                .filename(file.getFilename())
                .filePath(file.getFilePath())
                .build();
        return fileDto;
    }


    @Transactional
    public List<FileDto> getFileList(Long id) throws SQLException {
        List<FileEntity> fileList = fileRepository.findAllByBoardid(id);
        List<FileDto> fileDtoList = new ArrayList<>();
        for (FileEntity fileEntity: fileList
             ) {
            fileDtoList.add( FileDto.builder()
                    .id(id)
                    .origFilename(fileEntity.getOrigFilename())
                    .filename(fileEntity.getFilename())
                    .filePath(fileEntity.getFilePath())
                    .dfImg(fileEntity.getDfImg())
                    .build()
            );
        }

        return fileDtoList;
    }

    @Transactional
    public List<FileEntity> getAllFile() {
        List<FileEntity> file = fileRepository.findAll();
        return file;
    }


    @Transactional
    public void deleteFile(Long id) {
        fileRepository.deleteById(id);
    }
}
