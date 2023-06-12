package com.bbodek.meboard.controller.account;

import com.bbodek.meboard.domain.erpMast.bdItem010;
import com.bbodek.meboard.domain.erpMast.bdVdr010;
import com.bbodek.meboard.service.accountService.AccountService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AccountController {
    Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    //Account
    @GetMapping("/get/accountdata")
    private Map AccountGetData() {
        HashMap resultMap = new HashMap<String, String>();
        try {
            logger.info("AccountDateGet-------------------");
            List<bdVdr010> meaccountList = accountService.GetAccountData();
            resultMap.put("ResultValue", "조회에 성공하였습니다.");
            resultMap.put("data", meaccountList);
        } catch (Exception e) {
            logger.info("AccountDateGet-------------------[Error]"+ e.getMessage());
            resultMap.put("ResultValue", e.getMessage());
        }
        return resultMap;
    }

    @PostMapping("/set/accountdata")
    private Map AccountSetData(@RequestBody bdVdr010 meaccount){
        HashMap resultMap = new HashMap<String, String>();
        try {
            accountService.SetAccountData(meaccount);
            resultMap.put("ResultValue", "저장에 성공하였습니다.");
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
        }
        return resultMap;
    }

    @PostMapping("/set/accountdatalist")
    private Map AccountSetData(@RequestBody List<bdVdr010> meaccount){
        HashMap resultMap = new HashMap<String, String>();
        try {
            accountService.SetAccountDataList(meaccount);
            resultMap.put("ResultValue", "저장에 성공하였습니다.");
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
        }
        return resultMap;
    }

    @PostMapping("/updateaccount/{id}")
    private Map updateAccountData(@PathVariable String id, @RequestBody bdVdr010 meaccount) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            if (accountService.UpadateAccountData(id, meaccount)) {
                resultMap.put("ResultValue", "업데이트 성공!");
            } else {
                resultMap.put("ResultValue", "업데이트의 실패하였습니다. [해당 거래처 Code가 존재 하지 않습니다.]");
            }
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
        }
        return resultMap;
    }


}
