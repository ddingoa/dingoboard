package com.bbodek.meboard.service.orderService;

import com.bbodek.meboard.domain.orderWork.MEDAYWORKORDER;
import com.bbodek.meboard.domain.orderWork.MEENTERITEM;
import com.bbodek.meboard.dto.orderData.MEORDERWORKVIEWDTO;
import com.bbodek.meboard.respository.orderRepository.DayWorkOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Service
public class DayWorkOrderSerivce {

    @Autowired
    private DayWorkOrderRepository dayWorkOrderRepository;

    @Transactional
    public List<MEDAYWORKORDER> GetDayWorkOrderDataAll() throws SQLException {
        return dayWorkOrderRepository.findAll();
    }

    @Transactional
    public MEDAYWORKORDER GetDayWorkOrderData(String fabcode, String itemcode, LocalDate start, LocalDate end) throws SQLException {
        return dayWorkOrderRepository.findByFabCodeAndGoodsCdAndWorkStartBetween(fabcode,itemcode, start,end);
    }


    @Transactional
    public List<MEDAYWORKORDER> GetDayWorkOrderDataList(LocalDate start, LocalDate end, String fabcode) throws SQLException {
        return dayWorkOrderRepository.findAllByWorkStartBetweenAndFabCode(start,end,fabcode);
    }

    @Transactional
    public void SetDayWorkOrderData(List<MEDAYWORKORDER> medayworkorderList) throws SQLException {
        dayWorkOrderRepository.saveAll(medayworkorderList);
    }

    @Transactional
    public void delDayWorkOrderItemData(String itemCode,String fabCode,LocalDate workDay) throws SQLException {
        dayWorkOrderRepository.deleteByGoodsCdAndFabCodeAndWorkStart(itemCode, fabCode,workDay);
    }



    @Transactional
    public List<MEDAYWORKORDER> GetDayWorkOrderViewDataList(String fabcode,LocalDate start, LocalDate end) throws SQLException {
        return dayWorkOrderRepository.findAllByFabCodeAndWorkStartBetween(fabcode,start,end);
    }


}
