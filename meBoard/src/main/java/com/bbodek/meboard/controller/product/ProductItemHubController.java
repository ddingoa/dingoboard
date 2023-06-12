package com.bbodek.meboard.controller.product;

import com.bbodek.meboard.domain.erpMast.bdItem010;
import com.bbodek.meboard.domain.mast.MEFABSETTINGMAST;
import com.bbodek.meboard.domain.product.MEPRODUCTHUBITEM;
import com.bbodek.meboard.service.mastService.mastFabSettingService;
import com.bbodek.meboard.service.orderImpl.ProductServiceImplRepository;
import com.bbodek.meboard.service.orderService.DayWorkOrderSerivce;
import com.bbodek.meboard.service.productSeervice.ProductItemHubService;
import com.bbodek.meboard.util.commonMsg.MessageCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ProductItemHubController {

    @Autowired
    private ProductServiceImplRepository productServiceImplRepository;

    @Autowired
    private DayWorkOrderSerivce dayWorkOrderSerivce;
    @Autowired
    private mastFabSettingService mastFabSettingService;

    @Autowired
    private ProductItemHubService productItemHubService;

    @GetMapping("/get/producthubitem/{code}")
    private ResponseEntity<Map> ProductItemHubGetData(@PathVariable String code) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            List<MEPRODUCTHUBITEM> meproducthubitemList = productServiceImplRepository.getHubItemList(code);
            resultMap.put("ResultValue", MessageCode.COM_GET_DATA_MSG);
            resultMap.put("data", meproducthubitemList);
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }

    @GetMapping("/get/producthubcodeitem/{code}")
    private ResponseEntity<Map> ProductItemHubGetCodeData(@PathVariable String code) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            List<MEPRODUCTHUBITEM> meproducthubitemList = productItemHubService.GetProductHubCodeData(code);
            resultMap.put("ResultValue", MessageCode.COM_GET_DATA_MSG);
            resultMap.put("data", meproducthubitemList);
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }


    @GetMapping("/get/hubcodeitemlist/{code}")
    private ResponseEntity<Map> ProductHubGetCodeItemList(@PathVariable String code) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            List<String> codelist = new ArrayList<>();
            List<MEPRODUCTHUBITEM> meproducthubitem = productItemHubService.GetProductHubCodeData(code);

            for (MEPRODUCTHUBITEM dt : meproducthubitem) {
                codelist.add(dt.getGoodsCd());
            }

            List<bdItem010> meproducthubitemList = productServiceImplRepository.getHubCodeItem(codelist);
            resultMap.put("ResultValue", MessageCode.COM_GET_DATA_MSG);
            resultMap.put("data", meproducthubitemList);
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }




    @PostMapping("/set/producthubitem")
    private ResponseEntity<Map> ProductItemHubSetData(@RequestBody List<MEPRODUCTHUBITEM> hubitem) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            if (!hubitem.isEmpty()) {
                productItemHubService.SetProductHubCodeDataList(hubitem);
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


    @DeleteMapping("/del/producthubitem/{code}")
    private ResponseEntity<Map> ProductItemHubDeletData(@PathVariable String code) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            ZonedDateTime nowSeoul = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
            LocalDate workDay = LocalDate.now(ZoneId.from(nowSeoul));
            if (!code.isEmpty()) {
                MEPRODUCTHUBITEM meproducthubitem = productItemHubService.GetProductHubId(Long.parseLong(code));
                if(!meproducthubitem.getFabCode().isEmpty()) {
                    productItemHubService.DeletProductHubCodeDataList(Long.parseLong(code));
                    //생산량 목록에서 제거
                    dayWorkOrderSerivce.delDayWorkOrderItemData(meproducthubitem.getGoodsCd(),meproducthubitem.getFabCode(),workDay);
                    MEFABSETTINGMAST itemData = mastFabSettingService.GetFABSettingMastItemData(meproducthubitem.getFabCode(),meproducthubitem.getGoodsCd(),workDay);
                    if(itemData != null) {
                        mastFabSettingService.DelFABSettingMastDataList(itemData.getId());
                    }
                    resultMap.put("ResultValue", MessageCode.COM_DEL_MSG);
                }else {
                    resultMap.put("ResultValue", MessageCode.COM_CHECK_DATA_MSG);
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

}
