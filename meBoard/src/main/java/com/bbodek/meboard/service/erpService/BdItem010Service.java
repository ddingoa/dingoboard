package com.bbodek.meboard.service.erpService;

import com.bbodek.meboard.domain.erpMast.bdVdr010;
import com.bbodek.meboard.respository.erpRepository.bdItem010Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;

@Service
public class BdItem010Service {

    @Autowired
    private bdItem010Repository bdItem010Repository;


    @Transactional
    public void GetAccountData() throws SQLException {

    }



}
