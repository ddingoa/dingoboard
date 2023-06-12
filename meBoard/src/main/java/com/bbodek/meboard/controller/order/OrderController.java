package com.bbodek.meboard.controller.order;

import com.bbodek.meboard.domain.mast.MEFABSETTINGMAST;
import com.bbodek.meboard.domain.orderWork.MEDAYPKWORK;
import com.bbodek.meboard.domain.orderWork.MEDAYWORKORDER;
import com.bbodek.meboard.domain.orderWork.MEOPDFWORK;
import com.bbodek.meboard.domain.orderWork.MEOPWORK;
import com.bbodek.meboard.dto.orderData.MEDFORDERDASHDTO;
import com.bbodek.meboard.dto.orderData.MEORDERDASHDTO;
import com.bbodek.meboard.dto.pkOrderData.MEPKORDERDASHDTO;
import com.bbodek.meboard.service.mastService.mastFabSettingService;
import com.bbodek.meboard.service.opsWorkerImpl.OpsWorkerServiceImplRepository;
import com.bbodek.meboard.service.orderImpl.ProductServiceImplRepository;
import com.bbodek.meboard.service.orderService.DayWorkOrderSerivce;
import com.bbodek.meboard.service.orderService.OpDFWorkService;
import com.bbodek.meboard.service.orderService.OpWorkService;
import com.bbodek.meboard.service.pkOrderService.DayPkWorkService;
import com.bbodek.meboard.util.commonMsg.MessageCode;
import com.bbodek.meboard.util.commonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    private OpsWorkerServiceImplRepository opsWorkerServiceImplRepository;
    @Autowired
    private mastFabSettingService mastFabSettingService;
    @Autowired
    private ProductServiceImplRepository productServiceImplRepository;
    @Autowired
    private OpWorkService opWorkService;

    @Autowired
    private DayWorkOrderSerivce dayWorkOrderSerivce;

    @Autowired
    private OpDFWorkService opDFWorkService;

    @Autowired
    private DayPkWorkService dayPkWorkService;

    @PostMapping("/set/opworkitemcount")
    private ResponseEntity<Map> OpWorkItemSet(@RequestBody List<MEOPWORK> meopwork) {
        HashMap resultMap = new HashMap<String, String>();
        String datekey = commonUtil.convertDataKeyString("workitem");
        int dateTimekey = commonUtil.dateTimeKey();
        LocalDate workData = commonUtil.convertDateFabSetting();
        try {
            if (!meopwork.isEmpty()) {
                for (int i = 0; i < meopwork.size(); i++) {
                    meopwork.get(i).setWorkStart(workData);
                    meopwork.get(i).setOpCode(datekey);
                    meopwork.get(i).setInputTime(dateTimekey);
                }
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

    @PostMapping("/get/opworkitemcount")
    private ResponseEntity<Map> OpWorkItemGet(@RequestBody Map<String, Object> param) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            String fabCode = param.get("fabCode").toString();
            String itemCode = param.get("itemCode").toString();
            ZonedDateTime nowSeoul = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
            LocalDateTime endDate = LocalDateTime.now(ZoneId.from(nowSeoul));
            LocalDate startDateDay = LocalDate.now(ZoneId.from(nowSeoul));
            String startDateString = startDateDay.toString() + "T00:00:00";
            LocalDateTime startDate = LocalDateTime.parse(startDateString);

            List<MEOPWORK> meopworkList = opWorkService.GetWorkDataAll(fabCode, itemCode, startDate, endDate);
            resultMap.put("ResultValue", MessageCode.COM_GET_DATA_MSG);
            resultMap.put("data", meopworkList);
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }


    /**
     * OrderController
     * OpWorkItemGetDashboard
     * 설명 ::
     *
     * @author :: ddinggo
     * @version :: 1.0.0
     * 작성일 :: 2022/06/03
     **/
    @PostMapping("/get/opworkitemlist")
    private ResponseEntity<Map> OpWorkItemGetDashboard(@RequestBody Map<String, Object> param) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            String fabCode = param.get("fabCode").toString();
            String itemCode = param.get("itemCode").toString();
            ZonedDateTime nowSeoul = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
            LocalDateTime endDate = LocalDateTime.now(ZoneId.from(nowSeoul));
            LocalDate startDateDay = LocalDate.now(ZoneId.from(nowSeoul));
            String startDateString = startDateDay.toString() + "T00:00:00";
            LocalDateTime startDate = LocalDateTime.parse(startDateString);

            List<MEORDERDASHDTO> meorderdashdtoList = productServiceImplRepository.getOrderDashData(fabCode, itemCode, startDate, endDate);
            // MEFABSETTINGMAST fabSettingSpec = mastFabSettingService.GetFABSettingMastItemData(fabCode, itemCode,startDateDay);
            MEFABSETTINGMAST fabSettingSpec = mastFabSettingService.GetFABSettingMastItemData(fabCode, itemCode);
            resultMap.put("ResultValue", MessageCode.COM_GET_DATA_MSG);
            resultMap.put("chartData", meorderdashdtoList);
            resultMap.put("chartSpecData", fabSettingSpec);
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }


    @PostMapping("/set/opworkDFitemcount")
    private ResponseEntity<Map> OpWorkItemDFSet(@RequestBody List<MEOPDFWORK> meopdfworkList) {
        HashMap resultMap = new HashMap<String, String>();
        String datekey = commonUtil.convertDataKeyString("workDFitem");
        int dateTimekey = commonUtil.dateTimeKey();
        LocalDate workData = commonUtil.convertDateFabSetting();
        try {
            if (!meopdfworkList.isEmpty()) {
                for (MEOPDFWORK me : meopdfworkList) {
                    me.setOpCode(datekey);
                    me.setWorkStart(workData);
                    me.setInputTime(dateTimekey);
                }
                opDFWorkService.SetDFWorkDataAll(meopdfworkList);
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

    @PostMapping("/get/opworkDFitemlist")
    private ResponseEntity<Map> OpWorkDFItemGetDashboard(@RequestBody Map<String, Object> param) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            String fabCode = param.get("fabCode").toString();
            String itemCode = param.get("itemCode").toString();
            ZonedDateTime nowSeoul = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

            LocalDateTime endDate = LocalDateTime.now(ZoneId.from(nowSeoul));
            LocalDate startDateDay = LocalDate.now(ZoneId.from(nowSeoul));
            String startDateString = startDateDay.toString() + "T00:00:00";
            LocalDateTime startDate = LocalDateTime.parse(startDateString);

            List<MEDFORDERDASHDTO> meorderdashdtoList = productServiceImplRepository.getOrderDashDFData(fabCode, itemCode, startDate, endDate);
            resultMap.put("ResultValue", MessageCode.COM_GET_DATA_MSG);
            resultMap.put("chartData", meorderdashdtoList);
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }


    @PostMapping("/get/pkworkitemlist")
    private ResponseEntity<Map> PkWorkItemGetDashboard(@RequestBody Map<String, Object> param) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            String fabCode = param.get("fabCode").toString();
            String itemCode = param.get("itemCode").toString();
            ZonedDateTime nowSeoul = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
            LocalDateTime endDate = LocalDateTime.now(ZoneId.from(nowSeoul));
            LocalDate startDateDay = LocalDate.now(ZoneId.from(nowSeoul));
            String startDateString = startDateDay.toString() + "T00:00:00";
            LocalDateTime startDate = LocalDateTime.parse(startDateString);
            List<MEPKORDERDASHDTO> meorderdashdtoList = productServiceImplRepository.getPkWorkDashDFData(fabCode, itemCode, startDate, endDate);
            resultMap.put("ResultValue", MessageCode.COM_GET_DATA_MSG);
            resultMap.put("chartData", meorderdashdtoList);
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }


    //생산실적
    @GetMapping("/get/workOrderEnterData/{code}")
    private ResponseEntity<Map> GetWorkOrderEnterDataList(@PathVariable String code) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            ZonedDateTime nowSeoul = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
            LocalDate StartData = LocalDate.now(ZoneId.from(nowSeoul));
            LocalDate EndData = LocalDate.now(ZoneId.from(nowSeoul));

            List<MEDAYWORKORDER> totalWorkData = dayWorkOrderSerivce.GetDayWorkOrderDataList(StartData, EndData, code);

            resultMap.put("ResultValue", MessageCode.COM_GET_DATA_MSG);
            resultMap.put("totalWorkOrderData", totalWorkData);
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }


    //포장 실적
    @GetMapping("/get/pkWorkOrderData/{code}")
    private ResponseEntity<Map> GetPkWorkOrderEnterDataList(@PathVariable String code) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            ZonedDateTime nowSeoul = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
            LocalDate StartData = LocalDate.now(ZoneId.from(nowSeoul));
            LocalDate EndData = LocalDate.now(ZoneId.from(nowSeoul));

            List<MEDAYPKWORK> totalWorkData = dayPkWorkService.GetDayWorkOrderDataList(StartData, EndData, code);

            resultMap.put("ResultValue", MessageCode.COM_GET_DATA_MSG);
            resultMap.put("totalWorkOrderData", totalWorkData);
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }


}
