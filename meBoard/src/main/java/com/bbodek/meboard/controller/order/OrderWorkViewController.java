package com.bbodek.meboard.controller.order;


import com.bbodek.meboard.domain.orderWork.MEDAYWORKORDER;
import com.bbodek.meboard.domain.orderWork.MEOPWORK;
import com.bbodek.meboard.dto.orderData.MEORDERWORKVIEWDTO;
import com.bbodek.meboard.service.orderService.DayWorkOrderSerivce;
import com.bbodek.meboard.util.commonMsg.MessageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class OrderWorkViewController {

    @Autowired
    private DayWorkOrderSerivce dayWorkOrderSerivce;


    @PostMapping("/get/dayorderworkview")
    private ResponseEntity<Map> GetDayOrderWorkViewData(@RequestBody Map<String, Object> param) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            String fabCode = param.get("fabCode").toString();
            LocalDate startDate = LocalDate.parse(param.get("startDate").toString());
            LocalDate endDate = LocalDate.parse(param.get("endDate").toString());
            List<MEDAYWORKORDER> meorderworkviewdtoList = dayWorkOrderSerivce.GetDayWorkOrderViewDataList(fabCode, startDate, endDate);
            List<MEORDERWORKVIEWDTO> meorderworkviewresultdata = new ArrayList<>();
            for (MEDAYWORKORDER order : meorderworkviewdtoList
            ) {
                MEORDERWORKVIEWDTO data = new MEORDERWORKVIEWDTO();
                data.setWorkStart(order.getWorkStart());
                data.setGoodsCd(order.getGoodsCd());
                data.setGoodsNm(order.getGoodsNm());
                data.setTotalEnterCount(order.getTotalEnterCount());
                data.setTotalOrderPsCount(order.getTotalOrderPsCount());
                data.setTotalWorkCount(order.getTotalWorkCount());
                data.setTotalWorkDfCount(order.getTotalWorkDfCount());
                meorderworkviewresultdata.add(data);
            }
            resultMap.put("ResultValue", MessageCode.COM_GET_DATA_MSG);
            resultMap.put("data", meorderworkviewresultdata);
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }


}
