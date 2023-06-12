package com.bbodek.meboard.respository.opsRepository;


import com.bbodek.meboard.domain.opsWork.MEOPSWORK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpsWorkRepository  extends JpaRepository<MEOPSWORK, Long> {
     List<MEOPSWORK> findByLineCode(String linecode );
     List<MEOPSWORK> findAllByActive(boolean active);

     List<MEOPSWORK> findAllByActiveAndFabCodeAndLineCode(boolean active, String fabcode, String linecode);
}
