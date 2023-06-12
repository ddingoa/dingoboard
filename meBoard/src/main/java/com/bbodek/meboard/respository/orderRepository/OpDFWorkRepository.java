package com.bbodek.meboard.respository.orderRepository;

import com.bbodek.meboard.domain.orderWork.MEOPDFWORK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpDFWorkRepository  extends JpaRepository<MEOPDFWORK, Long> {

    MEOPDFWORK findByActiveAndFabCodeAndLineCodeAndEqpCodeAndWorkCodeAndItemCode(boolean activce, String fabcode, String lineCode,String eqpCode,String workCode,String itemCode);
}
