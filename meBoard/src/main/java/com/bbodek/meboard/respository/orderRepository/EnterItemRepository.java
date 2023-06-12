package com.bbodek.meboard.respository.orderRepository;


import com.bbodek.meboard.domain.orderWork.MEENTERITEM;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EnterItemRepository extends JpaRepository<MEENTERITEM, Long> {
    MEENTERITEM  findByWorkStartAndAccountCodeAndItemCodeAndFabCode(LocalDate setData,String account, String itemcode, String fabcode);
}
