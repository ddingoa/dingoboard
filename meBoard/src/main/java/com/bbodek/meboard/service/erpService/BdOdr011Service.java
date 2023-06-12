package com.bbodek.meboard.service.erpService;



import com.bbodek.meboard.domain.erpMast.bdOdr010;
import com.bbodek.meboard.domain.erpMast.bdOdr011;
import com.bbodek.meboard.respository.erpRepository.bdOdr011Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;

@Service
public class BdOdr011Service {

    @Autowired
    private bdOdr011Repository bdOdr011Repository;


    @Transactional
    public void GetAccountData() throws SQLException {

    }

    @Transactional
    public void SetOdr011DataList(List<bdOdr011> bdOdr011List) throws SQLException {
        bdOdr011Repository.saveAll(bdOdr011List);
    }
}
