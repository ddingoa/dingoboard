package com.bbodek.meboard.controller.mast;

import com.bbodek.meboard.domain.eqpMast.MEEQPMAST;
import com.bbodek.meboard.domain.mast.MEFABSETTINGMAST;
import com.bbodek.meboard.service.mastService.mastFabSettingService;
import com.bbodek.meboard.util.commonMsg.MessageCode;
import com.bbodek.meboard.util.commonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class FabSettingController {

    @Autowired
    private mastFabSettingService mastFabSettingService;



    @GetMapping("/get/fabsetting/{code}")
    private  ResponseEntity<Map> GetFabSettingMast(@PathVariable String code) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            List<MEFABSETTINGMAST> mefabsettingmastList = mastFabSettingService.GetFABSettingMastDataList(code);
            resultMap.put("ResultValue", MessageCode.COM_GET_DATA_MSG);
            resultMap.put("data", mefabsettingmastList);
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }


    @PostMapping("/set/fabsetting")
    private ResponseEntity<Map> SetFabSettingMastDataList(@RequestBody List<MEFABSETTINGMAST> mefabsettingmastList) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            if (!mefabsettingmastList.isEmpty()) {
                for(int i=0; i<mefabsettingmastList.size(); i++){
                    mefabsettingmastList.get(i).setWorkStart(commonUtil.convertDateFabSetting());
                }
                mastFabSettingService.SetFABSettingMastDataList(mefabsettingmastList);
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


    @DeleteMapping("/del/fabsetting/{code}")
    private ResponseEntity<Map> DelFabSettingMastData(@PathVariable String code) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            if (!code.isEmpty()) {
                mastFabSettingService.DelFABSettingMastDataList(Long.parseLong(code));
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
