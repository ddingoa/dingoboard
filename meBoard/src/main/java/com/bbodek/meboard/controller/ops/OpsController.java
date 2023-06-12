package com.bbodek.meboard.controller.ops;

import com.bbodek.meboard.domain.opsWork.MEOPSTAG;
import com.bbodek.meboard.domain.opsWork.MEOPSWORK;
import com.bbodek.meboard.domain.opsWork.MEOPSWORKER;
import com.bbodek.meboard.domain.opsWork.MEOPSWORKTIME;
import com.bbodek.meboard.service.opsService.opsService;
import com.bbodek.meboard.util.commonMsg.MessageCode;
import com.bbodek.meboard.util.commonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//작업자 등록
//작업자 작업 태그 등록 HIS

/**
 * The type OPS controller.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class OpsController {

    @Autowired
    private opsService opsService;


    //OPSWork
    @GetMapping("/get/opswork")
    private ResponseEntity<Map> OpsWorkGet() {
        HashMap resultMap = new HashMap<String, String>();
        try {
            List<MEOPSWORK> meopsworkList = opsService.GetOPSWorkData();
            resultMap.put("ResultValue", MessageCode.COM_GET_DATA_MSG);
            resultMap.put("data", meopsworkList);
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }


    @GetMapping("/get/opswork/{linecode}")
    private ResponseEntity<Map> OpsWorkIDGet(@PathVariable String linecode) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            List<MEOPSWORK> meopsworkList = opsService.GetOPSWorkLineCodeData(linecode);
            resultMap.put("ResultValue", MessageCode.COM_GET_DATA_MSG);
            resultMap.put("data", meopsworkList);
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }


    @PostMapping("/set/opswork")
    private  ResponseEntity<Map>  OpsWorkSet(@RequestBody MEOPSWORK meopswork) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            if (!meopswork.getEqpCode().isEmpty()) {
                meopswork.setWorkCode(commonUtil.convertDataKeyString("work"));
                opsService.SetOPSWorkData(meopswork);
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

    @PostMapping("/del/opswork")
    private ResponseEntity<Map> deleteLineMast(@RequestBody MEOPSWORK melinemast) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            if (!melinemast.getFabCode().isEmpty()) {
                opsService.DelOPSWorkData(melinemast);
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

    @PostMapping("/upopswork/{id}")
    private ResponseEntity<Map> updateLineMast(@PathVariable long id, @RequestBody MEOPSWORK msLine) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            if (opsService.UpadateWorkData(id, msLine)) {
                resultMap.put("ResultValue", MessageCode.COM_UP_MSG);
            } else {
                resultMap.put("ResultValue", MessageCode.COM_UP_ERROR_MSG);
            }
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }




    //OPSWorker
    @GetMapping("/get/opsworker")
    private ResponseEntity<Map> OpsWorkerGet() {
        HashMap resultMap = new HashMap<String, String>();
        try {
            List<MEOPSWORKER> meopsworkerList = opsService.GetOPSWorkerData();
            List<MEOPSWORKTIME> meopsworktimeList = new ArrayList<>();
            for (MEOPSWORKER worker : meopsworkerList) {
                MEOPSWORKTIME meopsworktime = opsService.GetOPSWorkerTimeData(worker.getOpCode());
                if(meopsworktime != null) {
                    meopsworktimeList.add(meopsworktime);
                }
            }
            resultMap.put("ResultValue", MessageCode.COM_GET_DATA_MSG);
            resultMap.put("workerData",meopsworkerList);
            resultMap.put("workerTimeData",meopsworktimeList);
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }


    @PostMapping("/set/opsworker")
    private ResponseEntity<Map> OpsWorkerSet(@RequestBody MEOPSWORKER meopsworker) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            if (!meopsworker.getFabCode().isEmpty()) {
                meopsworker.setOpCode(commonUtil.convertDataKeyString("opswork"));
                opsService.SetOPSWorkerData(meopsworker);
                MEOPSWORKTIME meopsworktime = new MEOPSWORKTIME();
                meopsworktime.setOpCode(meopsworker.getOpCode());
                opsService.SetOPSWorkerTimeData(meopsworktime);
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


    @PostMapping("/upopsworker/{id}")
    private ResponseEntity<Map> updateWorkerData(@PathVariable long id, @RequestBody MEOPSWORKER meopsworker) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            if (opsService.UpadateWorkerData(id, meopsworker)) {
                resultMap.put("ResultValue", MessageCode.COM_UP_MSG);
            } else {
                resultMap.put("ResultValue", MessageCode.COM_UP_ERROR_MSG);
            }
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }


    @PostMapping("/set/opsworkertime")
    private ResponseEntity<Map> OpsWorkerTimeSet(@RequestBody MEOPSWORKTIME meopsworktime) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            if (!meopsworktime.getOpCode().isEmpty()) {
                opsService.SetOPSWorkerTimeData(meopsworktime);
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


    //OPSTag
    @GetMapping("/get/opsworktag")
    private ResponseEntity<Map> OpsWorkTagGet() {
        HashMap resultMap = new HashMap<String, String>();
        try {
            List<MEOPSTAG> meopstagList = opsService.GetOPSWorkTagData();
            resultMap.put("ResultValue", MessageCode.COM_GET_DATA_MSG);
            resultMap.put("data", meopstagList);
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }



    /**
     * OpsController
     *   GetOpsWorkFabLineData
     * 설명 :: 작업 등록 콤보박스를 통한 작업 등록 데이터 출력
     *          Vue :: OpsWorkView.vue
     * @author :: ddinggo
     * @version :: 1.0.0
     * 작성일 :: 2022/05/13
     **/
    @PostMapping("/get/fablinework")
    private ResponseEntity<Map> GetOpsWorkFabLineData(@RequestBody HashMap<String,Object> cobodata){
        HashMap resultMap = new HashMap<String, String>();
        try {
            List<MEOPSWORK> meopstagList = opsService.GetOPSWorkFabLineData(cobodata);
            resultMap.put("ResultValue", MessageCode.COM_GET_DATA_MSG);
            resultMap.put("data", meopstagList);
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }



    /**
     * OpsController
     *   GetOpsWorkerFabLineWorkData
     * 설명 :: 작업자 등록 그리드 출력 데이터
     *       fabCode & LineCode & WorkCode를 이용하여 해당 작업자를 가져온다.
     * @author :: ddinggo
     * @version :: 1.0.0
     * 작성일 :: 2022/05/17
     **/
    @PostMapping("/get/fablineworker")
    private ResponseEntity<Map> GetOpsWorkerFabLineWorkData(@RequestBody HashMap<String,Object> cobodata){
        HashMap resultMap = new HashMap<String, String>();
        try {
            List<MEOPSWORKER> meopstagList = opsService.GetOPSWorkerFabLineData(cobodata);
            List<MEOPSWORKTIME> meopsworktimeList = new ArrayList<>();
            for (MEOPSWORKER worker : meopstagList) {
                MEOPSWORKTIME meopsworktime = opsService.GetOPSWorkerTimeData(worker.getOpCode());
                meopsworktimeList.add(meopsworktime);
            }
            resultMap.put("ResultValue", MessageCode.COM_GET_DATA_MSG);
            resultMap.put("workerData",meopstagList);
            resultMap.put("workerTimeData",meopsworktimeList);
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }






}
