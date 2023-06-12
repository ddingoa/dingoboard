package com.bbodek.meboard.controller.mast;

import com.bbodek.meboard.domain.eqpMast.MEEQPMAST;
import com.bbodek.meboard.domain.eqpMast.MELINEMAST;
import com.bbodek.meboard.domain.mast.MEFABMAST;
import com.bbodek.meboard.service.mastService.mastService;
import com.bbodek.meboard.util.commonMsg.MessageCode;
import com.bbodek.meboard.util.commonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Mast controller.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MastController {

    private final mastService mastService;


    //region EQPController
    /**
     * MastController
     *   EqpGetMast
     * 설명 :: 설비 데이터를 모두 가져 오는 로직
     *         Active True
     * @author :: ddinggo
     * @version :: 1.0.0
     * 작성일 :: 2022/05/13
     **/
    @GetMapping("/get/eqpmast")
    private ResponseEntity<Map> EqpGetMast() {
        HashMap resultMap = new HashMap<String, String>();
        try {
            List<MEEQPMAST> meeqpmastList = mastService.GetEQPMastData();
            resultMap.put("ResultValue", MessageCode.COM_GET_DATA_MSG);
            resultMap.put("data", meeqpmastList);
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }

    /**
     * MastController
     *   EqpGetLineMast
     * 설명 ::  작업등록에서 설비 콤보 박스 데이터를 출력 해주는 로직
     *      사용처 :: Vue = OpsWorkView.vue [작업등록]
     * @author :: ddinggo
     * @version :: 1.0.0
     * 작성일 :: 2022/05/13
     **/
    @GetMapping("/get/eqpmastline/{code}")
    private  ResponseEntity<Map> EqpGetLineMast(@PathVariable String code) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            List<MEEQPMAST> meeqpmastList = mastService.GetEQPMastLineData(code);
            resultMap.put("ResultValue", MessageCode.COM_GET_DATA_MSG);
            resultMap.put("data", meeqpmastList);
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }

    /**
     * MastController
     *   EqpSetMast
     * 설명 :: 설비 데이터 저장 로직
     * @author :: ddinggo
     * @version :: 1.0.0
     * 작성일 :: 2022/05/13
     **/
    @PostMapping("/set/eqpmast")
    private  ResponseEntity<Map> EqpSetMast(@RequestBody MEEQPMAST msEQP) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            if (!msEQP.getEqpName().isEmpty()) {
                msEQP.setEqpCode(commonUtil.convertDataKeyString("eqp"));
                mastService.SetEQPMastData(msEQP);
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

    /**
     * MastController
     *   EqpSetMastList
     * 설명 :: 설비 데이터를 한꺼번에 저정하는 로직
     * @author :: ddinggo
     * @version :: 1.0.0
     * 작성일 :: 2022/05/13
     **/
    @PostMapping("/set/eqpmastlist")
    private ResponseEntity<Map> EqpSetMastList(@RequestBody List<MEEQPMAST> msEQPList) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            if (msEQPList.size() > 0) {
                mastService.SetEQPMastListData(msEQPList);
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

    /**
     * MastController
     *   deleteEQPMast
     * 설명 :: 설비데이터를 id로 찾아서 삭제 하는 로직
     *       DB 삭제를 하지 않고 Active를 false로 변경하여 사용자에게 보여지지 않게 처리
     * @author :: ddinggo
     * @version :: 1.0.0
     * 작성일 :: 2022/05/13
     **/
    @PostMapping("/deleqp")
    private ResponseEntity<Map> deleteEQPMast(@RequestBody MEEQPMAST msEQP) {
        HashMap resultMap = new HashMap<String, String>();
        try {

            if (!msEQP.getFabCode().isEmpty()) {
                mastService.DelEQPMastData(msEQP);
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

    /**
     * MastController
     *   updateEQPMast
     * 설명 :: 설비 데이터를 id로 찾아 업데이트 하는 로직
     * @author :: ddinggo
     * @version :: 1.0.0
     * 작성일 :: 2022/05/13
     **/
    @PostMapping("/updateeqp/{id}")
    private ResponseEntity<Map> updateEQPMast(@PathVariable long id, @RequestBody MEEQPMAST msEQP) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            if (mastService.UpadateEQPMastData(id, msEQP)) {
                resultMap.put("ResultValue",MessageCode.COM_UP_MSG);
            } else {
                resultMap.put("ResultValue",MessageCode.COM_UP_ERROR_MSG);
            }
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }

    //endregion

    //region LineController

    /**
     * MastController
     * LineGetMast
     * 설명 :: 등록된 라인 데이터를 모두 가져 온다.
     * Active True
     *
     * @author :: ddinggo
     * @version :: 1.0.0
     * 작성일 :: 2022/05/12
     **/
    @GetMapping("/get/linemast")
    private ResponseEntity<Map> LineGetMast() {
        HashMap resultMap = new HashMap<String, String>();
        try {
            List<MELINEMAST> melinemastList = mastService.GetLineMastData();
            resultMap.put("ResultValue", MessageCode.COM_GET_DATA_MSG);
            resultMap.put("data", melinemastList);
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }


    /**
     * MastController
     * LineGetIDMastd
     * 설명 :: 등록된 라인 데이터를 ID를 통해 가져 온다.
     * Active True
     *
     * @author :: ddinggo
     * @version :: 1.0.0
     * 작성일 :: 2022/05/12
     **/
    @GetMapping("/get/linemast/{id}")
    private ResponseEntity<Map> LineGetIDMast(@PathVariable String id) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            if (!id.isEmpty()) {
                List<MELINEMAST> melinemastList = mastService.GetLineMastIDData(id);
                resultMap.put("ResultValue", MessageCode.COM_SAVE_MSG);
                resultMap.put("data", melinemastList);
            } else {
                resultMap.put("ResultValue", MessageCode.COM_CHECK_DATA_MSG);
            }

            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }

    /**
     * MastController
     * LineSetMast
     * 설명 :: 라인 데이터를 저장하는 로직
     *
     * @author :: ddinggo
     * @version :: 1.0.0
     * 작성일 :: 2022/05/12
     **/
    @PostMapping("/set/linemast")
    private ResponseEntity<Map> LineSetMast(@RequestBody MELINEMAST msLine) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            if (!msLine.getLineName().isEmpty()) {
                msLine.setLineCode(commonUtil.convertDataKeyString("line"));
                mastService.SetLineMastData(msLine);
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

    /**
     * MastController
     * LienSetMastList
     * 설명 :: 라인 데이터를 한꺼번에 업로드하여 저장하는 로직
     *
     * @author :: ddinggo
     * @version :: 1.0.0
     * 작성일 :: 2022/05/12
     **/
    @PostMapping("/set/linemastlist")
    private ResponseEntity<Map> LienSetMastList(@RequestBody List<MELINEMAST> msLineList) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            if (msLineList.size() > 0) {
                mastService.SetLineMastListData(msLineList);
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

    /**
     * MastController
     * deleteLineMast
     * 설명 :: 라인데이터를 삭제 하는 로직
     * 실직적으로 DB에서 삭제는 하지 않고 Active Flag를 false로 변경하여 노출만 되지 않도록 함
     *
     * @author :: ddinggo
     * @version :: 1.0.0
     * 작성일 :: 2022/05/12
     **/
    @PostMapping("/del/line")
    private ResponseEntity<Map> deleteLineMast(@RequestBody MELINEMAST melinemast) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            if (!melinemast.getFabCode().isEmpty()) {
                mastService.DelLineMastData(melinemast);
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

    /**
     * MastController
     * updateLineMast
     * 설명 :: 라인데이터를 ID로 찾아서 업데이트 하는 로직
     * !! 현재는 Save로직을 업데이트 로직으로 사용
     *
     * @author :: ddinggo
     * @version :: 1.0.0
     * 작성일 :: 2022/05/12
     **/
    @PostMapping("/updateline/{id}")
    private ResponseEntity<Map> updateLineMast(@PathVariable long id, @RequestBody MELINEMAST msLine) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            if (mastService.UpadateLineMastData(id, msLine)) {
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

    //endregion

    //region FabController

    /**
     * MastController
     * FabGetMast
     * 설명 :: 모든 허브 데이터를 가져오는 로직
     * Active 체크
     *
     * @author :: ddinggo
     * @version :: 1.0.0
     * 작성일 :: 2022/05/12
     **/
    @GetMapping("/get/fabmast")
    private ResponseEntity<Map> FabGetMast() {
        HashMap resultMap = new HashMap<String, String>();
        try {
            List<MEFABMAST> mefabmasts = mastService.Getmefabsettingmast();
            resultMap.put("ResultValue", MessageCode.COM_GET_DATA_MSG);
            resultMap.put("data", mefabmasts);
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }

    /**
     * MastController
     * FabSetMast
     * 설명 :: 허브 정보를 저장 하는 로직
     *
     * @author :: ddinggo
     * @version :: 1.0.0
     * 작성일 :: 2022/05/12
     **/
    @PostMapping("/set/fabmast")
    private ResponseEntity<Map> FabSetMast(
            @RequestBody MEFABMAST mefabmast) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            if (!mefabmast.getFabName().isEmpty()) {
                String set_fabCode = commonUtil.convertDataKeyString("hub");
                mefabmast.setFabCode(set_fabCode);
                mastService.Setmefabsettingmast(mefabmast);
                resultMap.put("ResultValue",MessageCode.COM_SAVE_MSG);
            } else {
                resultMap.put("ResultValue", MessageCode.COM_CHECK_DATA_MSG);
            }
            return ResponseEntity.ok(resultMap);

        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }

    /**
     * MastController
     * FabDelMast
     * 설명 :: 허브 정보를 삭제하는 로직
     * 실직적으로 DB에서 삭제는 하지 않고 Active Flag를 false로 변경하여 노출만 되지 않도록 함
     *
     * @author :: ddinggo
     * @version :: 1.0.0
     * 작성일 :: 2022/05/12
     **/
    @PostMapping("/del/fabmast")
    private ResponseEntity<Map> FabDelMast(
            @RequestBody MEFABMAST mefabmast) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            if (!mefabmast.getFabCode().isEmpty()) {
                mastService.Delmefabsettingmast(mefabmast);
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


    @PostMapping("/updatefab/{id}")
    private ResponseEntity<Map> updateFabMast(@PathVariable long id, @RequestBody MEFABMAST fabdata) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            if (mastService.Upadatemefabsettingmast(id, fabdata)) {
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
    //endregion

}
