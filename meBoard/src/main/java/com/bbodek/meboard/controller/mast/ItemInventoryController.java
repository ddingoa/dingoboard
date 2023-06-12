package com.bbodek.meboard.controller.mast;


import com.bbodek.meboard.domain.mast.MEFABSETTINGMAST;
import com.bbodek.meboard.domain.mast.MEITEMINVENTORY;
import com.bbodek.meboard.dto.mastData.MEITEMINVENTORYDTO;
import com.bbodek.meboard.service.mastService.mastItemInventoryService;
import com.bbodek.meboard.service.orderImpl.ProductServiceImplRepository;
import com.bbodek.meboard.util.commonMsg.MessageCode;
import com.bbodek.meboard.util.commonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ItemInventoryController {

    @Autowired
    private mastItemInventoryService mastItemInventoryService;

    @Autowired
    private ProductServiceImplRepository productServiceImplRepository;

    @GetMapping("/get/iteminventory/{code}")
    private ResponseEntity<Map> GetItemInventoryMast(@PathVariable String code) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            ZonedDateTime nowSeoul = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
            LocalDate workDay = LocalDate.now(ZoneId.from(nowSeoul));
            List<MEITEMINVENTORYDTO> mefabsettingmastList = productServiceImplRepository.getItemInventoryData(code,workDay);
            resultMap.put("ResultValue", MessageCode.COM_GET_DATA_MSG);
            resultMap.put("data", mefabsettingmastList);
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }


    @PostMapping("/set/iteminventory")
    private ResponseEntity<Map> SetItemInventoryMastDataList(@RequestBody List<MEITEMINVENTORY> meiteminventoryList) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            if (!meiteminventoryList.isEmpty()) {
                LocalDate SetData = commonUtil.convertDateFabSetting();
                for (MEITEMINVENTORY m :meiteminventoryList) {
                    m.setWorkStart(SetData);
                }
                mastItemInventoryService.SetItemInventoryMastDataList(meiteminventoryList);
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
