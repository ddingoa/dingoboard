package com.bbodek.meboard.controller.other;

import com.bbodek.meboard.domain.eqpMast.MEEQPMAST;
import com.bbodek.meboard.domain.erpMast.*;
import com.bbodek.meboard.domain.mast.MEFABMAST;
import com.bbodek.meboard.domain.orderWork.MEOPDFWORK;
import com.bbodek.meboard.domain.product.MEPRODUCTHUBITEM;
import com.bbodek.meboard.domain.wms.MEKIDSWMSMODEL;
import com.bbodek.meboard.domain.wms.MEKIDSWMSSUBMODEL;
import com.bbodek.meboard.dto.other.KIDSORDERMASTSAVE;
import com.bbodek.meboard.dto.other.MEKIDSORDERMASTDTO;
import com.bbodek.meboard.service.erpService.BdOdr010Service;
import com.bbodek.meboard.service.erpService.BdOdr012Service;
import com.bbodek.meboard.service.otherService.KidsWmsModelSerivce;
import com.bbodek.meboard.service.otherService.KidsWmsModelServiceImplRepository;
import com.bbodek.meboard.service.pkOrderImpl.PkOrderServiceImpl;
import com.bbodek.meboard.util.commonMsg.MessageCode;
import com.bbodek.meboard.util.commonUtil;
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
public class kidsWmsModelController {

    @Autowired
    private KidsWmsModelSerivce kidsWmsModelSerivce;

    @Autowired
    private KidsWmsModelServiceImplRepository kidsWmsModelServiceImplRepository;

    @GetMapping("/get/kidsWmsModel")
    private ResponseEntity<Map> GetKidsWmsModelData() {
        HashMap resultMap = new HashMap<String, String>();
        try {
            List<MEKIDSWMSMODEL> mekidswmsmodels = kidsWmsModelSerivce.GetKidsWmsModelDataAll();
            resultMap.put("ResultValue", MessageCode.COM_GET_DATA_MSG);
            resultMap.put("data", mekidswmsmodels);
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }

    @PostMapping("/set/kidsWmsModel")
    private ResponseEntity<Map> SetKidsWmsModelData(@RequestBody MEKIDSWMSMODEL mekidswmsmodelList) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            if (!mekidswmsmodelList.getDeliveryCode().isEmpty()) {
                kidsWmsModelSerivce.SetKidsWmsModelData(mekidswmsmodelList);
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

    @PostMapping("/del/kidsWmsModel")
    private ResponseEntity<Map> DelKidsWmsModelData(
            @RequestBody MEKIDSWMSMODEL mekidswmsmodel) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            if (!mekidswmsmodel.getDeliveryCode().isEmpty()) {
                kidsWmsModelSerivce.DelKidsWmsModelData(mekidswmsmodel);
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


    @PostMapping("/up/kidsWmsModel/{id}")
    private ResponseEntity<Map> UpdateKidsWmsModelData(@PathVariable long id, @RequestBody MEKIDSWMSMODEL mekidswmsmodel) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            if (kidsWmsModelSerivce.UpadateKidsWmsModelData(id, mekidswmsmodel)) {
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


    @Autowired
    private PkOrderServiceImpl pkOrderService;

    @GetMapping("/get/kidsWmsModellist/{code}")
    private ResponseEntity<Map> KidsWmsModelDataeItemList(@PathVariable String code) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            List<String> codelist = new ArrayList<>();
            List<MEKIDSWMSSUBMODEL> mekidswmsmodels = kidsWmsModelSerivce.GetKidsWmsModelCodeData(code);

            for (MEKIDSWMSSUBMODEL dt : mekidswmsmodels) {
                codelist.add(dt.getVendorCd());
            }

            List<bdVdr010> mekidswmsDataList = pkOrderService.getKidsVenderData(codelist);
            resultMap.put("ResultValue", MessageCode.COM_GET_DATA_MSG);
            resultMap.put("data", mekidswmsDataList);
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }


    @PostMapping("/set/kidsWmsSubModel")
    private ResponseEntity<Map> SetKidsWmsSubModelData(@RequestBody List<MEKIDSWMSSUBMODEL> mekidswmsmodelList) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            if (!mekidswmsmodelList.isEmpty()) {
                kidsWmsModelSerivce.SetKidsWmsSubModelDataAll(mekidswmsmodelList);
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

    @GetMapping("/get/kidsWmsSubModel/{code}")
    private ResponseEntity<Map> GetKidsWmsSubModelData(@PathVariable String code) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            List<MEKIDSWMSSUBMODEL> mekidswmssubmodels = kidsWmsModelSerivce.GetKidsWmsModelCodeData(code);
            resultMap.put("ResultValue", MessageCode.COM_GET_DATA_MSG);
            resultMap.put("data", mekidswmssubmodels);
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }


    @PostMapping("/del/kidsWmsSubModel")
    private ResponseEntity<Map> DelKidsWmsSubModelData(
            @RequestBody MEKIDSWMSSUBMODEL mekidswmssubmodel) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            if (!mekidswmssubmodel.getDeliveryCode().isEmpty()) {
                kidsWmsModelSerivce.DelKidsWmsSubModelData(mekidswmssubmodel);
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

    @GetMapping("/get/kidsordermast/{code}")
    private ResponseEntity<Map> GetKidsWmsModelOrderData(@PathVariable String code) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            List<MEKIDSORDERMASTDTO> mekidsordermastdtoList = kidsWmsModelServiceImplRepository.getKidsOrderMastData(code, "K0");
            resultMap.put("ResultValue", MessageCode.COM_GET_DATA_MSG);
            resultMap.put("data", mekidsordermastdtoList);
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }

    @Autowired
    private BdOdr010Service bdOdr010Service;

    @Autowired
    private BdOdr012Service bdOdr012Service;

    @PostMapping("/set/kidsordermast")
    private ResponseEntity<Map> SetKidsWmsModelOrderData(@RequestBody List<KIDSORDERMASTSAVE> kidsordermastsaveList) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            if (!kidsordermastsaveList.isEmpty()) {

                List<bdOdr010> odr010List = new ArrayList<>();
                List<bdOdr012> odr012List = new ArrayList<>();
                String orderNu = commonUtil.convertDataKeyKIDSString("");
                ZonedDateTime nowSeoul = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
                for (int i = 0; i < kidsordermastsaveList.size(); i++) {
                    bdOdr010 bdOdr010Map = new bdOdr010();
                    LocalDate startDateDay = LocalDate.now(ZoneId.from(nowSeoul));
                    LocalDate workDateDay = LocalDate.now(ZoneId.from(nowSeoul));
                    bdOdr010Map.setOrderDate(startDateDay.toString());
                    bdOdr010Map.setModiDate(workDateDay.toString());
                    bdOdr010Map.setRegDate(workDateDay.toString());
                    startDateDay = startDateDay.plusDays(1);
                    bdOdr010Map.setDeliveryDate(startDateDay.toString());
                    startDateDay = startDateDay.plusDays(1);
                    bdOdr010Map.setUseDate(startDateDay.toString());
                    startDateDay = startDateDay.plusDays(1);
                    bdOdr010Map.setPickupDate(startDateDay.toString());
                    bdOdr010Map.setOrderNo(kidsordermastsaveList.get(i).getVendorCd()+orderNu);
                    bdOdr010Map.setOrderAmount(kidsordermastsaveList.get(i).getOrderAmount());
                    bdOdr010Map.setChargeYn(kidsordermastsaveList.get(i).getChargeYn());
                    bdOdr010Map.setDivisionCd(kidsordermastsaveList.get(i).getDivisionCd());
                    bdOdr010Map.setKeyinType(kidsordermastsaveList.get(i).getKeyinType());
                    bdOdr010Map.setModiUserId(kidsordermastsaveList.get(i).getModiUserId());
                    bdOdr010Map.setRegUserId(kidsordermastsaveList.get(i).getRegUserId());
                    bdOdr010Map.setRemark(kidsordermastsaveList.get(i).getRemark());
                    bdOdr010Map.setVendorCd(kidsordermastsaveList.get(i).getVendorCd());
                    bdOdr010Map.setCloseYn(kidsordermastsaveList.get(i).getCloseYn());

                    bdOdr012 bdOdr012Map = new bdOdr012();
                    bdOdr012Map.setOrderNo(kidsordermastsaveList.get(i).getVendorCd()+orderNu);
                    bdOdr012Map.setBizUnit(kidsordermastsaveList.get(i).getBizUnit());
                    bdOdr012Map.setCloseYn(kidsordermastsaveList.get(i).getCloseYn());
                    bdOdr012Map.setClassCd(kidsordermastsaveList.get(i).getClassCd());
                    bdOdr012Map.setOrderQty(kidsordermastsaveList.get(i).getOrderQty());
                    bdOdr012Map.setGoodsCd(kidsordermastsaveList.get(i).getGoodsCd());
                    bdOdr012Map.setEaQty(kidsordermastsaveList.get(i).getEaQty());
                    bdOdr012Map.setMemberId(kidsordermastsaveList.get(i).getMemberId());
                    bdOdr012Map.setOrdernoSeq(kidsordermastsaveList.get(i).getOrdernoSeq());
                    bdOdr012Map.setModiDate(workDateDay.toString());
                    bdOdr012Map.setModiUserId(kidsordermastsaveList.get(i).getModiUserId());
                    bdOdr012Map.setRegDate(workDateDay.toString());
                    bdOdr012Map.setRegUserId(kidsordermastsaveList.get(i).getRegUserId());

                    odr010List.add(bdOdr010Map);
                    odr012List.add(bdOdr012Map);
                }
                bdOdr010Service.SetOdr010DataList(odr010List);
                bdOdr012Service.SetOdr012DataList(odr012List);

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


//    @PostMapping("/set/kidswmsupload")
//    privateResponseEntity<Map> SetKidsWmsSubModelUploadData(@RequestBodyList<kidswmsDTO>kidswmsDTOList) {
//        HashMapresultMap = newHashMap < String, String > ();
//        try {
//            if (!kidswmsDTOList.isEmpty()) {
//                List<MEKIDSWMSMODEL> mekidswmsmodelList = newArrayList <>();
//                List<MEKIDSWMSSUBMODEL> mekidswmssubmodelList = newArrayList <>();
//                booleancodeCheck = true;
//                for (kidswmsDTOkd:
//                     kidswmsDTOList
//                ) {
//                    bdVdr010mekidswmssubmodel = bdVdr010Service.GetVdData(kd.getAddress1(), kd.getVendorNm());
//                    MEKIDSWMSMODELmodel = newMEKIDSWMSMODEL();
//                    if (mekidswmsmodelList.size() > 0) {
//                        for (MEKIDSWMSMODELsu:
//                             mekidswmsmodelList) {
//                            if (su.getDeliveryCode().equals(kd.getDeliveryCode())) {
//                                codeCheck = false;
//                            } else {
//                                codeCheck = true;
//                            }
//                        }
//
//                        if (codeCheck) {
//                            model.setDeliveryCode(kd.getDeliveryCode());
//                            model.setDeliveryMd(kd.getDeliveryMd());
//                            model.setModCd(kd.getModCd());
//                            model.setRegCd(kd.getRegCd());
//                            mekidswmsmodelList.add(model);
//                        }
//
//                    } else {
//                        model.setDeliveryCode(kd.getDeliveryCode());
//                        model.setDeliveryMd(kd.getDeliveryMd());
//                        model.setModCd(kd.getModCd());
//                        model.setRegCd(kd.getRegCd());
//                        mekidswmsmodelList.add(model);
//                    }
//                    MEKIDSWMSSUBMODELsubModel = newMEKIDSWMSSUBMODEL();
//                    subModel.setDeliveryCode(kd.getDeliveryCode());
//                    subModel.setVendorCd(mekidswmssubmodel.getVendorCd());
//                    subModel.setVendorNm(kd.getVendorNm());
//                    subModel.setAddress1(kd.getAddress1());
//                    subModel.setModCd(kd.getModCd());
//                    subModel.setRegCd(kd.getRegCd());
//                    mekidswmssubmodelList.add(subModel);
//                }
//                kidsWmsModelSerivce.SetKidsWmsModelDataAll(mekidswmsmodelList);
//                kidsWmsModelSerivce.SetKidsWmsSubModelDataAll(mekidswmssubmodelList);
//                resultMap.put("ResultValue", MessageCode.COM_SAVE_MSG);
//            } else {
//                resultMap.put("ResultValue", MessageCode.COM_CHECK_DATA_MSG);
//            }
//            returnResponseEntity.ok(resultMap);
//        } catch (Exceptione) {
//            resultMap.put("ResultValue", e.getMessage());
//            returnResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
//        }
//    }


}
