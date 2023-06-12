package com.bbodek.meboard.respository.mastRepository;

import com.bbodek.meboard.domain.mast.MEFABMAST;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FabMastRepository extends JpaRepository<MEFABMAST, Long> {
    List<MEFABMAST> findAllByActiveOrderByFabNameAsc(boolean active);
}
