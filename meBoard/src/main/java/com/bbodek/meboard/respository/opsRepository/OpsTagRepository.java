package com.bbodek.meboard.respository.opsRepository;


import com.bbodek.meboard.domain.opsWork.MEOPSTAG;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpsTagRepository  extends JpaRepository<MEOPSTAG, Long> {
    List<MEOPSTAG> findAllByActive(boolean active);
}
