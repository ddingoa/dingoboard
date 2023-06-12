package com.bbodek.meboard.service.otherService;

import com.bbodek.meboard.domain.erpMast.*;
import com.bbodek.meboard.domain.pkOrderWork.QMEPKKIDSWORK;
import com.bbodek.meboard.domain.product.QMEPRODUCTHUBITEM;
import com.bbodek.meboard.domain.wms.QMEKIDSWMSMODEL;
import com.bbodek.meboard.domain.wms.QMEKIDSWMSSUBMODEL;
import com.bbodek.meboard.dto.other.MEKIDSORDERMASTDTO;
import com.bbodek.meboard.dto.pkOrderData.MEKIDSDELIVERYDTO;
import com.bbodek.meboard.service.pkOrderImpl.PkOrderServiceImplRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class KidsWmsModelServiceImpl implements KidsWmsModelServiceImplRepository {

    private final JPAQueryFactory queryFactory;

    //Kids 포장 현황
    public List<MEKIDSDELIVERYDTO> getKidsDeliveryData(String fabcode, LocalDate workDate, String dayCode) {
        return queryFactory.select(Projections.bean(MEKIDSDELIVERYDTO.class,
                        QbdOdr010.bdOdr010.orderNo.as("orderNo"),
                        QbdOdr010.bdOdr010.vendorCd.as("vendorCd"),
                        QbdOdr010.bdOdr010.orderDate.as("orderDate"),
                        QbdOdr010.bdOdr010.deliveryDate.as("deliveryDate"),
                        QbdOdr010.bdOdr010.useDate.as("useDate"),
                        QbdOdr010.bdOdr010.pickupDate.as("pickupDate"),
                        QbdOdr012.bdOdr012.classCd.as("classCd"),
                        QbdVdr010.bdVdr010.vendorNm.as("vendorNm"),
                        QbdVdr010.bdVdr010.address1.as("address1"),
                        QbdVdr020.bdVdr020.className.as("className"),
                        QbdVdr020.bdVdr020.classNumber.as("classNumber"),
                        QMEKIDSWMSSUBMODEL.mEKIDSWMSSUBMODEL.deliveryCode.as("deliveryCode"),
                        QMEKIDSWMSMODEL.mEKIDSWMSMODEL.daySeq.as("daySeq"),
                        QMEPKKIDSWORK.mEPKKIDSWORK.pkingCheck.as("pkingCheck"),
                        QbdVdr020.bdVdr020.teacherQty.as("teacherQty"),
                        QbdOdr012.bdOdr012.bizUnit.as("bizUnit"),
                        QbdOdr012.bdOdr012.goodsCd.as("goodsCd"),
                        QbdOdr012.bdOdr012.eaQty.max().as("eaQty")
                ))
                .from(QbdOdr010.bdOdr010)
                .innerJoin(QbdOdr012.bdOdr012).on(QbdOdr012.bdOdr012.orderNo.eq(QbdOdr010.bdOdr010.orderNo))
                .innerJoin(QbdVdr010.bdVdr010).on(QbdVdr010.bdVdr010.vendorCd.eq(QbdOdr010.bdOdr010.vendorCd))
                .innerJoin(QbdVdr020.bdVdr020).on(QbdVdr020.bdVdr020.classCd.eq(QbdOdr012.bdOdr012.classCd).and(QbdVdr020.bdVdr020.vendorCd.eq(QbdOdr010.bdOdr010.vendorCd)))
                .innerJoin(QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM).on(QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.divisionCd.eq("K0").and(QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.goodsCd.eq(QbdOdr012.bdOdr012.goodsCd)))
                .innerJoin(QMEKIDSWMSSUBMODEL.mEKIDSWMSSUBMODEL).on(QMEKIDSWMSSUBMODEL.mEKIDSWMSSUBMODEL.vendorCd.eq(QbdOdr010.bdOdr010.vendorCd))
                .innerJoin(QMEKIDSWMSMODEL.mEKIDSWMSMODEL).on(QMEKIDSWMSMODEL.mEKIDSWMSMODEL.deliveryCode.eq(QMEKIDSWMSSUBMODEL.mEKIDSWMSSUBMODEL.deliveryCode))
                .leftJoin(QMEPKKIDSWORK.mEPKKIDSWORK).on(QMEPKKIDSWORK.mEPKKIDSWORK.vendorCd.eq(QbdOdr010.bdOdr010.vendorCd).and(QMEPKKIDSWORK.mEPKKIDSWORK.classCd.eq(QbdVdr020.bdVdr020.classCd)))
                .where(QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.fabCode.eq(fabcode))
                .where(QMEKIDSWMSMODEL.mEKIDSWMSMODEL.dayCode.eq(dayCode))
                .where(QbdOdr010.bdOdr010.regDate.eq(workDate.toString()))
                .groupBy(QbdOdr010.bdOdr010.orderNo, QbdOdr010.bdOdr010.vendorCd, QbdOdr012.bdOdr012.goodsCd, QbdOdr010.bdOdr010.orderDate, QbdOdr010.bdOdr010.deliveryDate, QbdOdr010.bdOdr010.useDate, QbdOdr010.bdOdr010.pickupDate
                        , QbdOdr012.bdOdr012.classCd, QbdVdr010.bdVdr010.vendorNm, QbdVdr020.bdVdr020.className, QbdVdr020.bdVdr020.classNumber,
                        QbdVdr010.bdVdr010.address1, QMEKIDSWMSSUBMODEL.mEKIDSWMSSUBMODEL.deliveryCode, QMEKIDSWMSMODEL.mEKIDSWMSMODEL.daySeq, QbdVdr020.bdVdr020.teacherQty,
                        QbdOdr012.bdOdr012.bizUnit)
                .orderBy(QbdOdr010.bdOdr010.vendorCd.asc(), QbdOdr012.bdOdr012.classCd.asc())
                .fetch();
    }

    public List<MEKIDSORDERMASTDTO> getKidsOrderMastData(String fabcode, String divisionCd) {
        return queryFactory.select(Projections.bean(MEKIDSORDERMASTDTO.class,
                        QbdVdr010.bdVdr010.vendorCd.as("vendorCd"),
                        QbdVdr010.bdVdr010.vendorNm.as("vendorNm"),
                        QbdVdr020.bdVdr020.classCd.as("classCd"),
                        QbdVdr020.bdVdr020.className.as("className"),
                        QbdVdr020.bdVdr020.classNumber.as("classNumber"),
                        QMEKIDSWMSSUBMODEL.mEKIDSWMSSUBMODEL.address1.as("address1"),
                        QMEKIDSWMSSUBMODEL.mEKIDSWMSSUBMODEL.deliveryCode.as("deliveryCode"),
                        QbdItem010.bdItem010.goodsCd.as("goodsCd"),
                        QbdItem010.bdItem010.goodsNm.as("goodsNm")
                ))
                .from(QbdVdr010.bdVdr010)
                .innerJoin(QbdVdr020.bdVdr020).on(QbdVdr020.bdVdr020.vendorCd.eq(QbdVdr010.bdVdr010.vendorCd))
                .innerJoin(QMEKIDSWMSSUBMODEL.mEKIDSWMSSUBMODEL).on(QMEKIDSWMSSUBMODEL.mEKIDSWMSSUBMODEL.vendorCd.eq(QbdVdr010.bdVdr010.vendorCd))
                .innerJoin(QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM).on(QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.fabCode.eq(fabcode).and(QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.divisionCd.eq(QbdVdr010.bdVdr010.divisionCd)))
                .innerJoin(QbdItem010.bdItem010).on(QbdItem010.bdItem010.goodsCd.eq(QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.goodsCd))
                .where(QbdVdr010.bdVdr010.divisionCd.eq(divisionCd))
                .groupBy(QbdVdr010.bdVdr010.vendorCd, QbdVdr010.bdVdr010.vendorNm, QbdVdr020.bdVdr020.classCd.castToNum(Integer.class), QbdVdr020.bdVdr020.className, QbdVdr020.bdVdr020.classNumber
                        , QMEKIDSWMSSUBMODEL.mEKIDSWMSSUBMODEL.address1, QMEKIDSWMSSUBMODEL.mEKIDSWMSSUBMODEL.deliveryCode, QbdItem010.bdItem010.goodsCd, QbdItem010.bdItem010.goodsNm)
                .fetch();
    }

}
