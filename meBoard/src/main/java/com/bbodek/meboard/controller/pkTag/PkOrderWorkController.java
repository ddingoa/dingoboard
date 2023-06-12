package com.bbodek.meboard.controller.pkTag;

import com.bbodek.meboard.domain.opsWork.MEOPSWORK;
import com.bbodek.meboard.domain.pkOrderWork.MEPKKIDSWORK;
import com.bbodek.meboard.domain.pkOrderWork.MEPKWORK;
import com.bbodek.meboard.dto.pkOrderData.MEKIDSDELIVERYDTO;
import com.bbodek.meboard.dto.pkOrderData.MEPKORDERDTO;
import com.bbodek.meboard.respository.pkOrderRepository.pkOrderRepository;
import com.bbodek.meboard.service.otherService.KidsWmsModelServiceImplRepository;
import com.bbodek.meboard.service.pkOrderImpl.PkOrderServiceImplRepository;
import com.bbodek.meboard.service.pkOrderService.pkKidsOrderService;
import com.bbodek.meboard.service.pkOrderService.pkOrderService;
import com.bbodek.meboard.util.commonMsg.MessageCode;
import com.bbodek.meboard.util.commonUtil;
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

@RestController
@RequestMapping("/api")
public class PkOrderWorkController {

    @Autowired
    private PkOrderServiceImplRepository pkOrderServiceImplRepository;

    @Autowired
    private KidsWmsModelServiceImplRepository kidsWmsModelServiceImplRepository;

    @Autowired
    private pkOrderService pkOrderService;

    @Autowired
    private pkKidsOrderService pkKidsOrderService;

    @GetMapping("/get/pkorderdata/{fabcode}")
    private ResponseEntity<Map> GetPkOrderData(@PathVariable String fabcode) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            ZonedDateTime nowSeoul = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
            LocalDate workDay = LocalDate.now(ZoneId.from(nowSeoul));
            List<MEPKORDERDTO> mepkorderdtoList = pkOrderServiceImplRepository.getPkOrderData(fabcode, workDay);
            resultMap.put("ResultValue", MessageCode.COM_GET_DATA_MSG);
            resultMap.put("data", mepkorderdtoList);
            resultMap.put("toDate", workDay);
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }

    @PostMapping("/set/pkorderdatalist")
    private ResponseEntity<Map> PkOrderDataListSet(@RequestBody List<MEPKWORK> mepkworkList) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            if (!mepkworkList.isEmpty()) {
                ZonedDateTime nowSeoul = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
                LocalDate workDay = LocalDate.now(ZoneId.from(nowSeoul));
                int dateTimekey = commonUtil.dateTimeKey();
                for (MEPKWORK work : mepkworkList) {
                    work.setPkoCode(commonUtil.convertDataKeyString("pk"));
                    work.setWorkStart(workDay);
                    work.setInputTime(dateTimekey);
                    if (work.getPkItemCount() <= 0) {
                        work.setItemPsCount(work.getItemCount() * 1);
                    } else {
                        work.setItemPsCount(work.getItemCount() * work.getPkItemCount());
                    }
                }
                pkOrderService.SetPkOrderItemDataList(mepkworkList);
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

    @PostMapping("/set/pkorderdata")
    private ResponseEntity<Map> PkOrderDataListSet(@RequestBody MEPKWORK mepkworkList) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            if (!mepkworkList.getItemCode().isEmpty()) {
                ZonedDateTime nowSeoul = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
                LocalDate workDay = LocalDate.now(ZoneId.from(nowSeoul));
                int dateTimekey = commonUtil.dateTimeKey();
                mepkworkList.setPkoCode(commonUtil.convertDataKeyString("pk"));
                mepkworkList.setWorkStart(workDay);
                mepkworkList.setInputTime(dateTimekey);
                if (mepkworkList.getPkItemCount() <= 0) {
                    mepkworkList.setItemPsCount(mepkworkList.getItemCount() * 1);
                } else {
                    mepkworkList.setItemPsCount(mepkworkList.getItemCount() * mepkworkList.getPkItemCount());
                }
                pkOrderService.SetPkOrderItemData(mepkworkList);
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

    @PostMapping("/get/pkkidsdata")
    private ResponseEntity<Map> GetPkKidsData(@RequestBody Map<String, Object> param) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            ZonedDateTime nowSeoul = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
            LocalDate workDay = LocalDate.now(ZoneId.from(nowSeoul));
            String daySeq = param.get("daySep").toString();
            String fabcode = param.get("fabCode").toString();
            List<MEKIDSDELIVERYDTO> mepkorderdtoList = kidsWmsModelServiceImplRepository.getKidsDeliveryData(fabcode, workDay, daySeq);
            for (MEKIDSDELIVERYDTO dtodata : mepkorderdtoList
            ) {
                dtodata.setDaySeq(daySeq);
            }
            resultMap.put("ResultValue", MessageCode.COM_GET_DATA_MSG);
            resultMap.put("data", mepkorderdtoList);
            resultMap.put("toDate", workDay);
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }


    @PostMapping("/set/pkorderkidsdata")
    private ResponseEntity<Map> PkOrderKidsDataListSet(@RequestBody List<MEPKKIDSWORK> mepkworkList) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            if (!mepkworkList.isEmpty()) {
                ZonedDateTime nowSeoul = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
                LocalDate workDay = LocalDate.now(ZoneId.from(nowSeoul));
                int dateTimekey = commonUtil.dateTimeKey();

                for (MEPKKIDSWORK pk : mepkworkList
                ) {
                    pk.setWorkStart(workDay);
                    pk.setInputTime(dateTimekey);
                    pk.setPkoCode(commonUtil.convertDataKeyString("pkkids"));
                }
                pkKidsOrderService.SetPkOrderKidsItemDataList(mepkworkList);
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
