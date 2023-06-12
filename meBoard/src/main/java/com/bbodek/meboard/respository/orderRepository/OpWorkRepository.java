package com.bbodek.meboard.respository.orderRepository;


import com.bbodek.meboard.domain.orderWork.MEOPWORK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OpWorkRepository extends JpaRepository<MEOPWORK, Long> {
    List<MEOPWORK> findAllByActiveAndFabCodeAndItemCodeAndRegDtBetween(boolean active, String fabcode, String itemcode, LocalDateTime startTime, LocalDateTime endTime);
}
