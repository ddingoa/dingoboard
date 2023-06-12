package com.bbodek.meboard.controller.dashboard;

import com.bbodek.meboard.dto.dashboardDTO.*;
import com.bbodek.meboard.dto.pkOrderData.MEPKORDERDTO;
import com.bbodek.meboard.service.dashboardImpl.DashboardServiceImplRepository;
import com.bbodek.meboard.service.opsWorkerImpl.OpsWorkerServiceImplRepository;
import com.bbodek.meboard.service.orderService.DayWorkOrderSerivce;
import com.bbodek.meboard.service.orderService.OpDFWorkService;
import com.bbodek.meboard.service.pkOrderService.DayPkWorkService;
import com.bbodek.meboard.util.commonMsg.MessageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class dashboardController {

    @Autowired
    private DashboardServiceImplRepository dashboardServiceImplRepository;

    @GetMapping("/get/dashboard/{fabcode}")
    private ResponseEntity<Map> GetDashBoardData(@PathVariable String fabcode) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            ZonedDateTime nowSeoul = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
            LocalDate workDay = LocalDate.now(ZoneId.from(nowSeoul));
            //총 세척량 &총 검수량
            List<MEDASHBOARDDAYWORKDTO> workdfData = dashboardServiceImplRepository.getDayWorkDfData(workDay, fabcode);

            //총 포장 수량
            List<MEDASHBOARDPKDTO> workpkData = dashboardServiceImplRepository.getDayPkData(workDay, fabcode);

            //제품 시간별 생산량
            List<MEDSAHBOARDWORKCHARTDTO> workTiemData = dashboardServiceImplRepository.getDayTimeWorkData(workDay, fabcode);

            //제품 시간별 검수량
            List<MEDSAHBOARDWORKDFCHARTDTO> workTimeDfData = dashboardServiceImplRepository.getDayTimeWorkDFData(workDay, fabcode);

            //포장 실적
            List<MEDASHBOARDPKCOUNTLISTDTO> pkCountListData = dashboardServiceImplRepository.getDayPkCountListData(workDay, fabcode);

            //키즈 포장 실적
            List<MEDASHBOARDPKCOUNTLISTKIDSDTO> pkKidsCountListData = dashboardServiceImplRepository.getDayPkKidsCountListData(workDay, fabcode);

            resultMap.put("ResultValue", MessageCode.COM_GET_DATA_MSG);
            resultMap.put("dayWorkData", workdfData);
            resultMap.put("dayWorkPkData", workpkData);
            resultMap.put("dayWorkTimeData", workTiemData);
            resultMap.put("dayWorkTimeDfData", workTimeDfData);
            resultMap.put("pkCountList", pkCountListData);
            resultMap.put("pkKidsCountList", pkKidsCountListData);
            resultMap.put("toDate", workDay);
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }


}
