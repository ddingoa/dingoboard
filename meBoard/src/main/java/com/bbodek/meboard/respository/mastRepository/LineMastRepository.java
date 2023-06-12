package com.bbodek.meboard.respository.mastRepository;



import com.bbodek.meboard.domain.eqpMast.MELINEMAST;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LineMastRepository extends JpaRepository<MELINEMAST, Long> {
    List<MELINEMAST> findByFabCodeAndActive(String fabcode, boolean active);

    List<MELINEMAST> findAllByActive(boolean active);
}
