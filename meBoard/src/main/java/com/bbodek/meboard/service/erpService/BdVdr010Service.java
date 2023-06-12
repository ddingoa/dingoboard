package com.bbodek.meboard.service.erpService;

import com.bbodek.meboard.respository.erpRepository.bdVdr010Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;

@Service
public class BdVdr010Service {

    @Autowired
    private bdVdr010Repository bdVdr010Repository;


    @Transactional
    public void GetAccountData() throws SQLException {

    }

}
