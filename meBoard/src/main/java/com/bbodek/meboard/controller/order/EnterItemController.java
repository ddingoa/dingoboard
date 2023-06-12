package com.bbodek.meboard.controller.order;


import com.bbodek.meboard.domain.erpMast.bdVdr010;
import com.bbodek.meboard.domain.orderWork.MEENTERITEM;
import com.bbodek.meboard.domain.product.MEPRODUCTHUBITEM;
import com.bbodek.meboard.dto.mastData.MEENTERDTO;
import com.bbodek.meboard.dto.mastData.MEENTERREDTO;
import com.bbodek.meboard.dto.orderData.MENONENTERITEMDTO;
import com.bbodek.meboard.dto.util.EnterCobDto;
import com.bbodek.meboard.service.accountService.AccountService;
import com.bbodek.meboard.service.orderImpl.ProductServiceImplRepository;
import com.bbodek.meboard.service.orderService.EnterService;
import com.bbodek.meboard.service.productSeervice.ProductItemHubService;
import com.bbodek.meboard.service.productSeervice.ProductService;
import com.bbodek.meboard.util.commonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api")
public class EnterItemController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ProductService productService;

    @Autowired
    private EnterService enterService;

    @Autowired
    private ProductServiceImplRepository productServiceImplRepository;

    @Autowired
    private ProductItemHubService productItemHubService;

    //Account
    @GetMapping("/get/accountCobList/{bucode}")
    private Map AccountGetData(@PathVariable String bucode) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            List<bdVdr010> meaccountList = accountService.GetAccountBUData(bucode);
            List<String> itemTypeList = productServiceImplRepository.getItemTypeData(bucode);
            List<EnterCobDto> enterCobDtoList = new ArrayList<>();
            for (bdVdr010 state : meaccountList) {
                EnterCobDto enterCobDto = new EnterCobDto();
                enterCobDto.setValue(state.getVendorCd());
                enterCobDto.setText(state.getVendorNm());
                enterCobDtoList.add(enterCobDto);
            }
            resultMap.put("ResultValue", "조회에 성공하였습니다.");
            resultMap.put("accountStates", enterCobDtoList);
            resultMap.put("itmeTypes", itemTypeList);

        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
        }
        return resultMap;
    }



    @PostMapping("/get/accountdatas")
    private Map ProductAccountGetData(@RequestBody Map<String, Object> param) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            bdVdr010 meaccount = accountService.GetAccountDataS(param.get("accountCode").toString());
           // List<MEPRODUCT> meproductList = productService.GetProductBuDataList(meaccount.getBussiness());
            List<MEPRODUCTHUBITEM> meproducthubitemList = productItemHubService.GetProductHubCodeData(param.get("hubcode").toString());
            String UserId = param.get("username") == null ? "None" : param.get("username").toString();

            List<MEENTERDTO> meenterdtoList = new ArrayList<>();
            for (MEPRODUCTHUBITEM me : meproducthubitemList) {
                MEENTERDTO meenterdto = new MEENTERDTO();
                meenterdto.setAccountCode(meaccount.getVendorCd());
                meenterdto.setAccountName(meaccount.getVendorNm());
                meenterdto.setItemType(me.getGoodsType());
                meenterdto.setItemCode(me.getGoodsCd());
                meenterdto.setItemName(me.getGoodsNm());
                meenterdto.setFabCode(me.getFabCode());
                meenterdto.setFabName(me.getFabName());
                meenterdto.setInsManger(UserId);
                meenterdto.setEnterCode("");
                meenterdtoList.add(meenterdto);
            }

            resultMap.put("ResultValue", "조회에 성공하였습니다.");
            resultMap.put("data", meenterdtoList);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
        }
        return resultMap;
    }

    @PostMapping("/set/entersavedata")
    private Map SaveEnterData(@RequestBody MEENTERREDTO meenterdtoList){
        HashMap resultMap = new HashMap<String, String>();
        try {
            long systemTime = System.currentTimeMillis();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);
            String timekey = formatter.format(systemTime);
            List<MEENTERITEM> meenteritemList = new ArrayList<>();
            LocalDate SetData = commonUtil.convertDateFabSetting();


            for(MEENTERDTO me : meenterdtoList.getMeenteritemList()){
                MEENTERITEM getMeData = enterService.SetEnterItemAccoutDataList(SetData,me.getAccountCode(),me.getItemCode(),me.getFabCode());
                if(getMeData != null){
                    if(getMeData.getWorkStart().equals(commonUtil.convertDateFabSetting())) {
                        getMeData.setEnterItem(me.getEnterItem() + getMeData.getEnterItem());
                        getMeData.setDfCount(me.getDfCount() + getMeData.getDfCount());
                        getMeData.setModCd(meenterdtoList.getUsername());
                        meenteritemList.add(getMeData);
                    }else {
                        MEENTERITEM meenteritem = new MEENTERITEM();
                        String timekeyAC = timekey;
                        timekeyAC += me.getAccountCode();
                        meenteritem.setEnterItem(me.getEnterItem());
                        meenteritem.setEnterCode(timekeyAC);
                        meenteritem.setAccountCode(me.getAccountCode());
                        meenteritem.setAccountName(me.getAccountName());
                        meenteritem.setDfCount(me.getDfCount());
                        meenteritem.setItemCode(me.getItemCode());
                        meenteritem.setItemName(me.getItemName());
                        meenteritem.setFabCode(meenterdtoList.getEnterCobDto().getValue());
                        meenteritem.setFabName(meenterdtoList.getEnterCobDto().getText());
                        meenteritem.setModCd(meenterdtoList.getUsername());
                        meenteritem.setRegCd(meenterdtoList.getUsername());
                        meenteritem.setInsManager(me.getInsManger());
                        meenteritem.setWorkStart(SetData);
                        meenteritemList.add(meenteritem);
                    }
                }else {
                    MEENTERITEM meenteritem = new MEENTERITEM();
                    String timekeyAC = timekey;
                    timekeyAC += me.getAccountCode();
                    meenteritem.setEnterItem(me.getEnterItem());
                    meenteritem.setEnterCode(timekeyAC);
                    meenteritem.setAccountCode(me.getAccountCode());
                    meenteritem.setAccountName(me.getAccountName());
                    meenteritem.setDfCount(me.getDfCount());
                    meenteritem.setItemCode(me.getItemCode());
                    meenteritem.setItemName(me.getItemName());
                    meenteritem.setFabCode(meenterdtoList.getEnterCobDto().getValue());
                    meenteritem.setFabName(meenterdtoList.getEnterCobDto().getText());
                    meenteritem.setModCd(meenterdtoList.getUsername());
                    meenteritem.setRegCd(meenterdtoList.getUsername());
                    meenteritem.setInsManager(me.getInsManger());
                    meenteritem.setWorkStart(SetData);
                    meenteritemList.add(meenteritem);
                }
            }
            enterService.SetEnterItemDataList(meenteritemList);
            resultMap.put("ResultValue", "저장에 성공하였습니다.");
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
        }
        return resultMap;
    }


    @GetMapping("/get/nonenteritem")
    private Map GetNonEnterItemList(){
        HashMap resultMap = new HashMap<String, String>();
        try {
            List<MENONENTERITEMDTO> itemTypeList = productServiceImplRepository.getNonEnterItemData();
            resultMap.put("ResultValue", "조회에 성공하였습니다.");
            resultMap.put("accountStates", itemTypeList);
//            resultMap.put("itmeTypes", itemTypeList);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            resultMap.put("ResultValue", e.getMessage());
        }
        return resultMap;
    }

    @PostMapping("/get/nonenteritem")
    private Map GetNonEnterItemListBU(@RequestBody Map<String, Object> param){
        HashMap resultMap = new HashMap<String, String>();
        try {
            LocalDate StartDate =    LocalDate.parse(param.get("startdata").toString());
            LocalDate EndData = LocalDate.parse(param.get("enddata").toString());
            String accountCode = param.get("accountcode").toString();


            List<MENONENTERITEMDTO> itemTypeList = productServiceImplRepository.getNonEnterItemData(param.get("bucode").toString(),param.get("fabcode").toString(),StartDate,EndData,accountCode);
            resultMap.put("ResultValue", "조회에 성공하였습니다.");
            resultMap.put("accountStates", itemTypeList);
//            resultMap.put("itmeTypes", itemTypeList);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            resultMap.put("ResultValue", e.getMessage());
        }
        return resultMap;
    }
}
