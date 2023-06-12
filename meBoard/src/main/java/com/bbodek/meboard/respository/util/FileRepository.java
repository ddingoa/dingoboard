package com.bbodek.meboard.respository.util;

import com.bbodek.meboard.domain.util.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    FileEntity findByBoardid(Long boardid);
    List<FileEntity> findAllByBoardid(Long boardid);
}

