package com.bbodek.meboard.service.erpService;

import com.bbodek.meboard.respository.erpRepository.bdVdr031Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;

@Service
public class BdVdr031Service {

    @Autowired
    private bdVdr031Repository bdVdr031Repository;


    @Transactional
    public void GetAccountData() throws SQLException {

    }
}
