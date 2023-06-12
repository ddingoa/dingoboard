package com.bbodek.meboard.respository.mastRepository;

import com.bbodek.meboard.domain.mast.MEACCOUNTORDER;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MastOrderRepository extends JpaRepository<MEACCOUNTORDER, Long> {
}
