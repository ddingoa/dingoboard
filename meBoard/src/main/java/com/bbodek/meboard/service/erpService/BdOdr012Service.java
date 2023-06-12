package com.bbodek.meboard.service.erpService;


import com.bbodek.meboard.domain.erpMast.bdOdr011;
import com.bbodek.meboard.domain.erpMast.bdOdr012;
import com.bbodek.meboard.respository.erpRepository.bdOdr012Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;

@Service
public class BdOdr012Service {

    @Autowired
    private bdOdr012Repository bdOdr012Repository;


    @Transactional
    public void GetAccountData() throws SQLException {

    }

    @Transactional
    public void SetOdr012DataList(List<bdOdr012> bdOdr012List) throws SQLException {
        bdOdr012Repository.saveAll(bdOdr012List);
    }

}
