package com.bbodek.meboard.service.mastService;


import com.bbodek.meboard.domain.eqpMast.MEEQPMAST;
import com.bbodek.meboard.domain.mast.MEACCOUNTORDER;
import com.bbodek.meboard.respository.mastRepository.MastOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class mastOrderService {

    @Autowired
    private MastOrderRepository mastOrderRepository;

    @Transactional
    public List<MEACCOUNTORDER> GetMastOrderData() throws SQLException {
        return mastOrderRepository.findAll();
    }

}
