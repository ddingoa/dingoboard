package com.bbodek.meboard.service.erpService;

import com.bbodek.meboard.respository.erpRepository.bdVdr020Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;

@Service
public class BdVdr020Service {

    @Autowired
    private bdVdr020Repository bdVdr020Repository;


    @Transactional
    public void GetAccountData() throws SQLException {

    }
}
