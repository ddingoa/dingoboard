package com.bbodek.meboard.service.pkOrderService;


import com.bbodek.meboard.domain.orderWork.MEDAYPKWORK;
import com.bbodek.meboard.domain.orderWork.MEDAYWORKORDER;
import com.bbodek.meboard.respository.pkOrderRepository.DayPkWorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Service
public class DayPkWorkService {

    @Autowired
    private DayPkWorkRepository pkWorkRepository;


    @Transactional
    public MEDAYPKWORK GetDayPkWorkOrderData(String fabcode, String itemcode, LocalDate start, LocalDate end) throws SQLException {
        return pkWorkRepository.findByFabCodeAndGoodsCdAndWorkStartBetween(fabcode,itemcode, start,end);
    }

    @Transactional
    public List<MEDAYPKWORK> GetDayWorkOrderDataList(LocalDate start, LocalDate end, String fabcode) throws SQLException {
        return pkWorkRepository.findAllByWorkStartBetweenAndFabCode(start,end,fabcode);
    }

    @Transactional
    public void SetDayPkWorkOrderData(List<MEDAYPKWORK> medayworkorderList) throws SQLException {
        pkWorkRepository.saveAll(medayworkorderList);
    }


}
