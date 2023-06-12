package com.bbodek.meboard.service.pkOrderService;


import com.bbodek.meboard.domain.orderWork.MEISDF;
import com.bbodek.meboard.domain.pkOrderWork.MEPKWORK;
import com.bbodek.meboard.respository.pkOrderRepository.pkOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class pkOrderService {

    @Autowired
    private pkOrderRepository pkOrderRepository;

    @Transactional
    public void SetPkOrderItemData(MEPKWORK mepkwork) throws SQLException {
        pkOrderRepository.save(mepkwork);
    }


    @Transactional
    public void SetPkOrderItemDataList(List<MEPKWORK> mepkworkList) throws SQLException {
        pkOrderRepository.saveAll(mepkworkList);
    }

    @Transactional
    public List<MEPKWORK> GetPkOrderItemDataList() throws SQLException {
        return pkOrderRepository.findAll();
    }
}
