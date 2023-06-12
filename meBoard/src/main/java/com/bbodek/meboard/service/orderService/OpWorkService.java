package com.bbodek.meboard.service.orderService;

import com.bbodek.meboard.domain.orderWork.MEOPWORK;
import com.bbodek.meboard.respository.orderRepository.OpWorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OpWorkService {


    @Autowired
    private OpWorkRepository opWorkRepository;


    @Transactional
    public void SetWorkDataAll(List<MEOPWORK> meopworkList) throws SQLException {
        opWorkRepository.saveAll(meopworkList);
    }

    @Transactional
    public List<MEOPWORK> GetWorkDataAll(String fabcode, String itemcode, LocalDateTime startTime, LocalDateTime endTime) throws SQLException {
       return opWorkRepository.findAllByActiveAndFabCodeAndItemCodeAndRegDtBetween(true, fabcode, itemcode,startTime,endTime);
    }

}
