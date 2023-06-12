package com.bbodek.meboard.respository.orderRepository;

import com.bbodek.meboard.domain.orderWork.MEDAYWORKORDER;
import com.bbodek.meboard.dto.orderData.MEORDERWORKVIEWDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DayWorkOrderRepository extends JpaRepository<MEDAYWORKORDER, Long> {

    MEDAYWORKORDER findByFabCodeAndGoodsCdAndWorkStartBetween(String fabcode, String itemcode, LocalDate start, LocalDate end);
    List<MEDAYWORKORDER> findAllByWorkStartBetweenAndFabCode(LocalDate start, LocalDate end, String fabcode);

    void deleteByGoodsCdAndFabCodeAndWorkStart(String itemCode,String fabcode,LocalDate workDay);

    List<MEDAYWORKORDER> findAllByFabCodeAndWorkStartBetween(String fabcode, LocalDate start, LocalDate end);

}
