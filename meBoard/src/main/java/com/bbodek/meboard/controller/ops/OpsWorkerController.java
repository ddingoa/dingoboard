package com.bbodek.meboard.controller.ops;


import com.bbodek.meboard.domain.opsWork.MEOPSWORK;
import com.bbodek.meboard.dto.opsworker.MEOPSWORKERDTO;
import com.bbodek.meboard.service.opsWorkerImpl.OpsWorkerServiceImplRepository;
import com.bbodek.meboard.util.commonMsg.MessageCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class OpsWorkerController {


    @Autowired
    private OpsWorkerServiceImplRepository opsWorkerServiceImplRepository;


    @GetMapping("/get/opsworkertree")
    private ResponseEntity<Map> OpsWorkGet() {
        HashMap resultMap = new HashMap<String, String>();
        try {
            List<MEOPSWORKERDTO> meopsworkList = opsWorkerServiceImplRepository.getWorkerTreeData();
            resultMap.put("ResultValue", MessageCode.COM_GET_DATA_MSG);
            resultMap.put("data", meopsworkList);
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }


}
