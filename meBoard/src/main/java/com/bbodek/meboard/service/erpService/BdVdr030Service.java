package com.bbodek.meboard.service.erpService;

import com.bbodek.meboard.respository.erpRepository.bdVdr030Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;

@Service
public class BdVdr030Service {

    @Autowired
    private bdVdr030Repository bdVdr030Repository;


    @Transactional
    public void GetAccountData() throws SQLException {

    }
}
