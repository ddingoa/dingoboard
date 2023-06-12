package com.bbodek.meboard.service.pkOrderService;

import com.bbodek.meboard.domain.pkOrderWork.MEPKKIDSWORK;
import com.bbodek.meboard.domain.pkOrderWork.MEPKWORK;
import com.bbodek.meboard.respository.pkOrderRepository.pkKidsOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class pkKidsOrderService {
    @Autowired
    private pkKidsOrderRepository pkKidsOrderRepository;


    @Transactional
    public void SetPkOrderKidsItemDataList(List<MEPKKIDSWORK> mepkworkList) throws SQLException {
        pkKidsOrderRepository.saveAll(mepkworkList);
    }

    @Transactional
    public List<MEPKKIDSWORK> GetPkOrderKidsItemDataList() throws SQLException {
        return pkKidsOrderRepository.findAll();
    }

}
