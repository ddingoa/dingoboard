package com.bbodek.meboard.respository.opsRepository;

import com.bbodek.meboard.domain.opsWork.MEOPSWORKTIME;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpsWorkerTimeRepository extends JpaRepository<MEOPSWORKTIME, Long> {
    MEOPSWORKTIME findByAndOpCode(String opCode);
}
