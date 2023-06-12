package com.bbodek.meboard.service.orderService;


import com.bbodek.meboard.domain.orderWork.MEENTERITEM;
import com.bbodek.meboard.respository.orderRepository.EnterItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Service
public class EnterService {

    @Autowired
    private EnterItemRepository enterItemRepository;

    @Transactional
    public List<MEENTERITEM> GetEnterItemDataAll() throws SQLException {
        return enterItemRepository.findAll();
    }

    @Transactional
    public void SetEnterItemData(MEENTERITEM meenteritem) throws SQLException {
        enterItemRepository.save(meenteritem);
    }

    @Transactional
    public void SetEnterItemDataList(List<MEENTERITEM> meenteritem) throws SQLException {
        enterItemRepository.saveAll(meenteritem);
    }

    @Transactional
    public MEENTERITEM SetEnterItemAccoutDataList(LocalDate setData, String acccode, String itemcode, String fabcode) throws SQLException {
      return  enterItemRepository.findByWorkStartAndAccountCodeAndItemCodeAndFabCode(setData,acccode,itemcode,fabcode);
    }

}
