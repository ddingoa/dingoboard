package com.bbodek.meboard.service.pkOrderImpl;


import com.bbodek.meboard.domain.erpMast.*;
import com.bbodek.meboard.domain.pkOrderWork.QMEPKWORK;
import com.bbodek.meboard.domain.product.QMEPRODUCTHUBITEM;
import com.bbodek.meboard.domain.wms.QMEKIDSWMSSUBMODEL;
import com.bbodek.meboard.dto.pkOrderData.MEKIDSDELIVERYDTO;
import com.bbodek.meboard.dto.pkOrderData.MEPKORDERDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PkOrderServiceImpl implements PkOrderServiceImplRepository {

    private final JPAQueryFactory queryFactory;

    public List<MEPKORDERDTO> getPkOrderData(String fabcode, LocalDate workDate) {
        return queryFactory
                .select(Projections.bean(MEPKORDERDTO.class,
                        QbdOdr010.bdOdr010.orderNo.as("orderNo"),
                        QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.fabCode.as("fabCode"),
                        QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.fabName.as("fabName"),
                        QbdOdr010.bdOdr010.divisionCd.as("divisionCd"),
                        QbdOdr010.bdOdr010.vendorCd.as("vendorCd"),
                        QbdVdr010.bdVdr010.vendorNm.as("vendorNm"),
                        QbdOdr010.bdOdr010.orderDate.as("orderDate"),
                        QbdOdr010.bdOdr010.deliveryDate.as("deliveryDate"),
                        QbdOdr010.bdOdr010.useDate.as("useDate"),
                        QbdOdr010.bdOdr010.pickupDate.as("pickupDate"),
                        QbdOdr011.bdOdr011.goodsCd.as("goodsCd"),
                        QbdItem010.bdItem010.goodsNm.as("goodsNm"),
                        QbdOdr011.bdOdr011.orderQty.as("orderQty"),
                        QbdOdr011.bdOdr011.eaQty.as("eaQty"),
                        QbdOdr011.bdOdr011.eaQty.as("eaQty"),
                        QMEPKWORK.mEPKWORK.pkingCheck.as("pkingCheck")
                ))
                .from(QbdOdr010.bdOdr010)
                .innerJoin(QbdOdr011.bdOdr011).on(QbdOdr011.bdOdr011.orderNo.eq(QbdOdr010.bdOdr010.orderNo))
                .innerJoin(QbdItem010.bdItem010).on(QbdItem010.bdItem010.goodsCd.eq(QbdOdr011.bdOdr011.goodsCd))
                .innerJoin(QbdVdr010.bdVdr010).on(QbdVdr010.bdVdr010.vendorCd.eq(QbdOdr010.bdOdr010.vendorCd))
                .leftJoin(QMEPKWORK.mEPKWORK).on(QMEPKWORK.mEPKWORK.pkoCode.eq(QbdOdr010.bdOdr010.orderNo))
                .innerJoin(QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM).on(QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.goodsCd.eq(QbdOdr011.bdOdr011.goodsCd).and(QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.fabCode.eq(fabcode)))
//                .where(QbdOdr010.bdOdr010.deliveryDate.eq(String.valueOf(workDate)))
                .orderBy(QbdOdr010.bdOdr010.deliveryDate.asc(), QbdOdr010.bdOdr010.vendorCd.asc(), QMEPKWORK.mEPKWORK.pkingCheck.asc())
                .fetch();
    }


    //Kids 배송 마스터 Setting
    public List<bdVdr010> getKidsVenderData(List<String> code) {
        return queryFactory.select(Projections.bean(bdVdr010.class,
                        QbdVdr010.bdVdr010.vendorCd.as("vendorCd"),
                        QbdVdr010.bdVdr010.vendorNm.as("vendorNm"),
                        QbdVdr010.bdVdr010.address1.as("address1"),
                        QbdVdr010.bdVdr010.divisionCd.as("divisionCd")
                ))
                .from(QbdVdr010.bdVdr010)
                .where(QbdVdr010.bdVdr010.vendorCd.notIn(code))
                .where(QbdVdr010.bdVdr010.divisionCd.eq("K0"))
                .fetch();
    }


}
