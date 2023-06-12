package com.bbodek.meboard.controller.order;

import com.bbodek.meboard.domain.orderWork.MEOPWORK;
import com.bbodek.meboard.service.orderService.OpWorkService;
import com.bbodek.meboard.util.commonMsg.MessageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class OrderEditController {

    @Autowired
    private OpWorkService opWorkService;

    @PostMapping("/up/opworkitemcount")
    private ResponseEntity<Map> OpWorkItemUpdate(@RequestBody List<MEOPWORK> meopwork) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            if (!meopwork.isEmpty()) {
                opWorkService.SetWorkDataAll(meopwork);
                resultMap.put("ResultValue", MessageCode.COM_SAVE_MSG);
            } else {
                resultMap.put("ResultValue", MessageCode.COM_CHECK_DATA_MSG);
            }
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }

}
