package com.bbodek.meboard.respository.orderRepository;

import com.bbodek.meboard.domain.orderWork.MEISDF;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DfOrderItemRepository extends JpaRepository<MEISDF, Long> {
    List<MEISDF> findAllByActive(boolean active);
    MEISDF findByDfCode(long dfcode);
 }
