package com.bbodek.meboard.respository.opsRepository;

import com.bbodek.meboard.domain.opsWork.MEOPSWORK;
import com.bbodek.meboard.domain.opsWork.MEOPSWORKER;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpsWorkerRepository  extends JpaRepository<MEOPSWORKER, Long> {
    List<MEOPSWORKER> findAllByActive(boolean active);
    List<MEOPSWORKER> findAllByActiveAndFabCodeAndLineCodeAndWorkCode(boolean active,String fabcode, String linecode, String workcode);
}
