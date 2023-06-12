package com.bbodek.meboard.controller.orderworksetting;


import com.bbodek.meboard.domain.eqpMast.MEEQPMAST;
import com.bbodek.meboard.domain.product.MEPRODUCTHUBITEM;
import com.bbodek.meboard.service.mastService.mastService;
import com.bbodek.meboard.util.commonMsg.MessageCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class FabWorkingSetting {


    @Autowired
    private final mastService mastService;


    @GetMapping("/get/fabsettingeqplist/{code}")
    private ResponseEntity<Map> ProductItemHubGetData(@PathVariable String code) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            List<MEEQPMAST> fabeqplist = mastService.GetEQPMastFabData(code);
            resultMap.put("ResultValue", MessageCode.COM_GET_DATA_MSG);
            resultMap.put("data", fabeqplist);
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }


}
