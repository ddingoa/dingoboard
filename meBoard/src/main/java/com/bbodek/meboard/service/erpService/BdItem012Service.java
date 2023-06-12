package com.bbodek.meboard.service.erpService;

import com.bbodek.meboard.respository.erpRepository.bdItem012Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;

@Service
public class BdItem012Service {

    @Autowired
    private bdItem012Repository bdItem012Repository;


    @Transactional
    public void GetAccountData() throws SQLException {

    }
}
