package com.bbodek.meboard.service.erpService;


import com.bbodek.meboard.domain.erpMast.bdOdr010;
import com.bbodek.meboard.respository.erpRepository.bdOdr010Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;

@Service
public class BdOdr010Service {

    @Autowired
    private bdOdr010Repository bdOdr010Repository;


    @Transactional
    public void GetAccountData() throws SQLException {

    }

    @Transactional
    public void SetOdr010DataList(List<bdOdr010> bdOdr010List) throws SQLException {
        bdOdr010Repository.saveAll(bdOdr010List);
    }
}
