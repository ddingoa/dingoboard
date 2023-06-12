package com.bbodek.meboard.controller.mast;

import com.bbodek.meboard.domain.eqpMast.MEEQPMAST;
import com.bbodek.meboard.domain.erpMast.bdOdr010;
import com.bbodek.meboard.domain.erpMast.bdOdr011;
import com.bbodek.meboard.domain.mast.MEORDERTMAST;
import com.bbodek.meboard.domain.product.MEPRODUCTHUBITEM;
import com.bbodek.meboard.dto.erpDTO.erpBizOdrDTO;
import com.bbodek.meboard.dto.mastData.MEORDERTDTO;
import com.bbodek.meboard.dto.orderData.MEORDERVALUEDTO;
import com.bbodek.meboard.dto.pkOrderData.MEPKORDERDTO;
import com.bbodek.meboard.service.erpService.BdOdr010Service;
import com.bbodek.meboard.service.erpService.BdOdr011Service;
import com.bbodek.meboard.service.mastService.mastOrderTService;
import com.bbodek.meboard.service.orderImpl.ProductServiceImplRepository;
import com.bbodek.meboard.service.productSeervice.ProductItemHubService;
import com.bbodek.meboard.util.commonMsg.MessageCode;
import com.bbodek.meboard.util.commonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class OrderTMastController {

    @Autowired
    private mastOrderTService mastOrdertservice;

    @Autowired
    private ProductServiceImplRepository productServiceImplRepository;

    @PostMapping("/get/ordertmast")
    private ResponseEntity<Map> OrderGetMast(@RequestBody Map<String, Object> param) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            String code = param.get("code").toString();
            String account = param.get("account").toString();

            ZonedDateTime nowSeoul = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
            LocalDate workDay = LocalDate.now(ZoneId.from(nowSeoul));
            LocalDate endDate = LocalDate.now(ZoneId.from(nowSeoul));
            LocalDate startDate = LocalDate.now(ZoneId.from(nowSeoul));

            List<MEORDERTDTO> meproducthubitemList = productServiceImplRepository.getOrderTHubItemORList(code, account,startDate,endDate);
          // List<MEORDERVALUEDTO> meproducthubitemList = productServiceImplRepository.getOrderTHubItemList(code, account,workDay);
            resultMap.put("data", meproducthubitemList);
            resultMap.put("ResultValue", MessageCode.COM_GET_DATA_MSG);
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }

    @PostMapping("/set/ordertmast")
    private  ResponseEntity<Map> OrderSetMast(@RequestBody List<MEORDERTMAST> meordertmastList) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            if (!meordertmastList.isEmpty()) {

                for(MEORDERTMAST me : meordertmastList){
                    if(me.getWorkStart() == null) {
                        me.setWorkStart(commonUtil.convertDateFabSetting());
                    }
                    if(me.getOrderKeyCode().isEmpty()) {
                        me.setOrderKeyCode(commonUtil.convertDataKeyString("Order"));
                    }
                }
                mastOrdertservice.SetMastOrderTData(meordertmastList);
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


    @Autowired
    private BdOdr010Service bdOdr010Service;

    @Autowired
    private BdOdr011Service bdOdr011Service;

    @PostMapping("/set/bizorderset")
    private ResponseEntity<Map> BizOrderSetDate(@RequestBody List<erpBizOdrDTO> bizOdrDTOList) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            if (!bizOdrDTOList.isEmpty()) {
                List<bdOdr010> odr010List = new ArrayList<>();
                List<bdOdr011> odr011List = new ArrayList<>();
                for (int i = 0; i < bizOdrDTOList.size(); i++) {
                    bdOdr010 bdOdr010Map = new bdOdr010();
                    bdOdr010Map.setDeliveryDate(bizOdrDTOList.get(i).getDeliveryDate());
                    bdOdr010Map.setOrderNo(bizOdrDTOList.get(i).getOrderNo());
                    bdOdr010Map.setOrderDate(bizOdrDTOList.get(i).getOrderDate());
                    bdOdr010Map.setModiDate(bizOdrDTOList.get(i).getModiDate());
                    bdOdr010Map.setOrderAmount(bizOdrDTOList.get(i).getOrderAmount());
                    bdOdr010Map.setChargeYn(bizOdrDTOList.get(i).getChargeYn());
                    bdOdr010Map.setDivisionCd(bizOdrDTOList.get(i).getDivisionCd());
                    bdOdr010Map.setKeyinType(bizOdrDTOList.get(i).getKeyinType());
                    bdOdr010Map.setModiUserId(bizOdrDTOList.get(i).getModiUserId());
                    bdOdr010Map.setPickupDate(bizOdrDTOList.get(i).getPickupDate());
                    bdOdr010Map.setRegDate(bizOdrDTOList.get(i).getRegDate());
                    bdOdr010Map.setRegUserId(bizOdrDTOList.get(i).getRegUserId());
                    bdOdr010Map.setRemark(bizOdrDTOList.get(i).getRemark());
                    bdOdr010Map.setUseDate(bizOdrDTOList.get(i).getUseDate());
                    bdOdr010Map.setVendorCd(bizOdrDTOList.get(i).getVendorCd());
                    bdOdr010Map.setCloseYn(bizOdrDTOList.get(i).getCloseYn());

                    bdOdr011 bdOdr011Map = new bdOdr011();
                    bdOdr011Map.setOrderNo(bizOdrDTOList.get(i).getOrderNo());
                    bdOdr011Map.setBizUnit(bizOdrDTOList.get(i).getBizUnit());
                    bdOdr011Map.setModiDate(bizOdrDTOList.get(i).getModiDate());
                    bdOdr011Map.setRegDate(bizOdrDTOList.get(i).getRegDate());
                    bdOdr011Map.setRemark(bizOdrDTOList.get(i).getRemark());
                    bdOdr011Map.setModiUserId(bizOdrDTOList.get(i).getModiUserId());
                    bdOdr011Map.setBoxInQty(bizOdrDTOList.get(i).getBoxInQty());
                    bdOdr011Map.setClassCd(bizOdrDTOList.get(i).getClassCd());
                    bdOdr011Map.setCloseYn(bizOdrDTOList.get(i).getCloseYn());
                    bdOdr011Map.setDeliveryNo(bizOdrDTOList.get(i).getDeliveryNo());
                    bdOdr011Map.setDeliveryQty(bizOdrDTOList.get(i).getDeliveryQty());
                    bdOdr011Map.setEaQty(bizOdrDTOList.get(i).getEaQty());
                    bdOdr011Map.setGoodsCd(bizOdrDTOList.get(i).getGoodsCd());
                    bdOdr011Map.setGoodsGroupCd(bizOdrDTOList.get(i).getGoodsGroupCd());
                    bdOdr011Map.setOrdernoSeq(bizOdrDTOList.get(i).getOrdernoSeq());
                    bdOdr011Map.setOrderQty(bizOdrDTOList.get(i).getOrderQty());
                    bdOdr011Map.setPackQty(bizOdrDTOList.get(i).getPackQty());
                    bdOdr011Map.setPickupQty(bizOdrDTOList.get(i).getPickupQty());
                    bdOdr011Map.setRegUserId(bizOdrDTOList.get(i).getRegUserId());

                    odr010List.add(bdOdr010Map);
                    odr011List.add(bdOdr011Map);
                }
                bdOdr010Service.SetOdr010DataList(odr010List);
                bdOdr011Service.SetOdr011DataList(odr011List);

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
