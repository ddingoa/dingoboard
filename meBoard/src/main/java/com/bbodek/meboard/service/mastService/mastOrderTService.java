package com.bbodek.meboard.service.mastService;


import com.bbodek.meboard.domain.mast.MEACCOUNTORDER;
import com.bbodek.meboard.domain.mast.MEORDERTMAST;
import com.bbodek.meboard.respository.mastRepository.OrderTMastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class mastOrderTService {

    @Autowired
    private OrderTMastRepository orderTMastRepository;

    @Transactional
    public List<MEORDERTMAST> GetMastOrderTData() throws SQLException {
        return orderTMastRepository.findAll();
    }


    @Transactional
    public MEORDERTMAST GetMastOrderTSingleData(String account,String item) throws SQLException {
        return orderTMastRepository.findByAccountCodeAndItemCode(account,item);
    }


    @Transactional
    public void SetMastOrderTData(List<MEORDERTMAST> meordertmastList) throws SQLException {
         orderTMastRepository.saveAll(meordertmastList);
    }


}
