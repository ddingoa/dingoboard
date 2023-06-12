package com.bbodek.meboard.controller.labelController;


import com.bbodek.meboard.domain.eqpMast.MEEQPMAST;
import com.bbodek.meboard.domain.erpMast.bdVdr010;
import com.bbodek.meboard.domain.label.bbodeklabel;
import com.bbodek.meboard.domain.orderWork.MEISDF;
import com.bbodek.meboard.dto.util.EnterCobDto;
import com.bbodek.meboard.service.labelService.labelService;
import com.bbodek.meboard.util.commonMsg.MessageCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class labelController {

    @Autowired
    private labelService labelservice;


    @GetMapping("/get/qrdata")
    private Map GetLabelData() {
        HashMap resultMap = new HashMap<String, String>();
        try {
            List<bbodeklabel> bbodeklabelList = labelservice.GetLabelDataList();
            resultMap.put("ResultValue", "조회에 성공하였습니다.");
            resultMap.put("data", bbodeklabelList);

        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
        }
        return resultMap;
    }


    @PostMapping("/set/qrdata")
    private ResponseEntity<Map> SetQRData(@RequestBody bbodeklabel bbodeklabel) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            if (!bbodeklabel.getQrCode().isEmpty()) {
                ZonedDateTime nowSeoul = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
                LocalDateTime dateTime = LocalDateTime.now(ZoneId.from(nowSeoul));
                bbodeklabel.setOutTime(dateTime);
                bbodeklabel.setCheckValue(false);
                labelservice.SetLabelData(bbodeklabel);
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

    @PostMapping("/label/updatacode/{code}")
    private ResponseEntity<Map> UpdateQRData(@PathVariable String code) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            if (!code.isEmpty()) {
                bbodeklabel bbodeklabel = labelservice.GetLabelData(code);
                if(!bbodeklabel.isCheckValue()) {
                    ZonedDateTime nowSeoul = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
                    LocalDateTime dateTime = LocalDateTime.now(ZoneId.from(nowSeoul));
                    bbodeklabel.setReturnTime(dateTime);
                    bbodeklabel.setCheckValue(true);
                    labelservice.SetLabelData(bbodeklabel);
                    resultMap.put("ResultValue", MessageCode.COM_LABEL_MSG);
                }else {
                    resultMap.put("ResultValue", MessageCode.COM_NON_LABEL_MSG);
                }
            } else {
                resultMap.put("ResultValue", MessageCode.COM_CHECK_DATA_MSG);
            }
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }


    @PostMapping("/dellabel/{code}")
    private ResponseEntity<Map> deleteLabelData(@PathVariable long code) {
        HashMap resultMap = new HashMap<String, String>();
        try {

            if (code > 0) {
                labelservice.DelLabelData(code);
                resultMap.put("ResultValue", MessageCode.COM_DEL_MSG);
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
