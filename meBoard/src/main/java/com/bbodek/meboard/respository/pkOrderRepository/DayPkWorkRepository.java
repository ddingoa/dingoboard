package com.bbodek.meboard.respository.pkOrderRepository;

import com.bbodek.meboard.domain.orderWork.MEDAYPKWORK;
import com.bbodek.meboard.domain.orderWork.MEDAYWORKORDER;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DayPkWorkRepository extends JpaRepository<MEDAYPKWORK, Long> {

    MEDAYPKWORK findByFabCodeAndGoodsCdAndWorkStartBetween(String fabcode, String itemcode, LocalDate start, LocalDate end);
    List<MEDAYPKWORK> findAllByWorkStartBetweenAndFabCode(LocalDate start, LocalDate end, String fabcode);

    void deleteByGoodsCdAndFabCodeAndWorkStart(String itemCode,String fabcode,LocalDate workDay);
}
