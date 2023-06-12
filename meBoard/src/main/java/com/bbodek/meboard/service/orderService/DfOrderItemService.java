package com.bbodek.meboard.service.orderService;

import com.bbodek.meboard.domain.orderWork.MEISDF;
import com.bbodek.meboard.respository.orderRepository.DfOrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class DfOrderItemService {

    @Autowired
    private DfOrderItemRepository dfOrderItemRepository;


    @Transactional
    public void SetDfOrderItemData(MEISDF meisdf) throws SQLException {
        dfOrderItemRepository.save(meisdf);
    }


    @Transactional
    public List<MEISDF> GetDfOrderItemData() throws SQLException {
       return dfOrderItemRepository.findAllByActive(true);
    }


    @Transactional
    public void DelDfOrderItemData(long id) throws SQLException {
        dfOrderItemRepository.deleteById(id);
    }
}
