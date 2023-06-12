package com.bbodek.meboard.service.orderImpl;


import com.bbodek.meboard.domain.erpMast.*;
import com.bbodek.meboard.domain.mast.QMEITEMINVENTORY;
import com.bbodek.meboard.domain.mast.QMEORDERTMAST;
import com.bbodek.meboard.domain.opsWork.QMEOPSWORK;
import com.bbodek.meboard.domain.orderWork.*;
import com.bbodek.meboard.domain.pkOrderWork.QMEPKWORK;
import com.bbodek.meboard.domain.product.MEPRODUCTHUBITEM;
import com.bbodek.meboard.domain.product.QMEPRODUCTHUBITEM;
import com.bbodek.meboard.dto.mastData.MEITEMINVENTORYDTO;
import com.bbodek.meboard.dto.mastData.MEORDERTDTO;
import com.bbodek.meboard.dto.orderData.MEDFORDERDASHDTO;
import com.bbodek.meboard.dto.orderData.MENONENTERITEMDTO;
import com.bbodek.meboard.dto.orderData.MEORDERDASHDTO;
import com.bbodek.meboard.dto.orderData.MEORDERVALUEDTO;
import com.bbodek.meboard.dto.pkOrderData.MEPKORDERDASHDTO;
import com.bbodek.meboard.dto.pkOrderData.MEPKORDERDTO;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductServiceImplRepository {
    private final JPAQueryFactory queryFactory;

    public List<String> getItemTypeData(String bucode) {
        return queryFactory
                .select(QbdItem010.bdItem010.goodsType)
                .from(QbdItem010.bdItem010)
                .where(QbdItem010.bdItem010.divisionCd.eq(bucode))
                .groupBy(QbdItem010.bdItem010.goodsType)
                .fetch();
    }


    public List<MENONENTERITEMDTO> getNonEnterItemData() {
        return queryFactory
                .select(Projections.bean(MENONENTERITEMDTO.class,
                        QMEENTERITEM.mEENTERITEM.workStart.as("workStart"),
                        QbdVdr010.bdVdr010.vendorCd.as("vendorCd"),
                        QbdVdr010.bdVdr010.vendorNm.as("vendorNm"),
                        QbdVdr010.bdVdr010.divisionCd.as("divisionCd"),
                        QMEENTERITEM.mEENTERITEM.itemName.as("itemName"),
                        QMEENTERITEM.mEENTERITEM.enterItem.stringValue().as("enterItem"),
                        QMEENTERITEM.mEENTERITEM.enterCode.stringValue().as("enterCode"),
                        QMEENTERITEM.mEENTERITEM.dfCount.stringValue().as("dfCount"),
                        QMEOPSAORDER.mEOPSAORDER.inItemCount.subtract(QMEENTERITEM.mEENTERITEM.enterCode.castToNum(Integer.class)).stringValue().as("nonitemcount")
                ))
                .from(QbdVdr010.bdVdr010)
                .leftJoin(QMEENTERITEM.mEENTERITEM).on(QbdVdr010.bdVdr010.vendorCd.eq(QMEENTERITEM.mEENTERITEM.accountCode))
                .leftJoin(QMEOPSAORDER.mEOPSAORDER).on(QbdVdr010.bdVdr010.vendorCd.eq(QMEOPSAORDER.mEOPSAORDER.accountCode))
                .fetch();
    }

    public List<MENONENTERITEMDTO> getNonEnterItemData(String bu, String fabcode, LocalDate startdate, LocalDate enddate, String accountCode) {
        return queryFactory
                .select(Projections.bean(MENONENTERITEMDTO.class,
                        QMEENTERITEM.mEENTERITEM.workStart.as("workStart"),
                        QbdVdr010.bdVdr010.vendorCd.as("vendorCd"),
                        QbdVdr010.bdVdr010.vendorNm.as("vendorNm"),
                        QbdVdr010.bdVdr010.divisionCd.as("divisionCd"),
                        QMEENTERITEM.mEENTERITEM.itemName.as("itemName"),
                        QMEENTERITEM.mEENTERITEM.enterItem.stringValue().as("enterItem"),
                        QMEENTERITEM.mEENTERITEM.enterCode.stringValue().as("enterCode"),
                        QMEENTERITEM.mEENTERITEM.dfCount.stringValue().as("dfCount"),
                        QMEOPSAORDER.mEOPSAORDER.inItemCount.subtract(QMEENTERITEM.mEENTERITEM.enterCode.castToNum(Integer.class)).stringValue().as("nonitemcount")
                ))

                .from(QbdVdr010.bdVdr010)
                .leftJoin(QMEENTERITEM.mEENTERITEM).on(QbdVdr010.bdVdr010.vendorCd.eq(QMEENTERITEM.mEENTERITEM.accountCode))
                .leftJoin(QMEOPSAORDER.mEOPSAORDER).on(QbdVdr010.bdVdr010.vendorCd.eq(QMEOPSAORDER.mEOPSAORDER.accountCode))
                .where(QbdVdr010.bdVdr010.divisionCd.eq(bu))
                .where(QMEENTERITEM.mEENTERITEM.fabCode.eq(fabcode))
                .where(QMEENTERITEM.mEENTERITEM.accountCode.eq(accountCode))
                .where(QMEENTERITEM.mEENTERITEM.workStart.between(startdate, enddate))
                .orderBy(QMEENTERITEM.mEENTERITEM.workStart.asc(), QMEENTERITEM.mEENTERITEM.accountCode.desc())
                .fetch();
    }


    public List<MEPRODUCTHUBITEM> getHubItemList(String bu) {
        return queryFactory
                .select(Projections.bean(MEPRODUCTHUBITEM.class,
                        QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.fabCode.as("fabcode"),
                        QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.fabName.as("fabName"),
                        QbdItem010.bdItem010.goodsCd.as("goodsCd"),
                        QbdItem010.bdItem010.goodsNm.as("goodsNm"),
                        QbdItem010.bdItem010.goodsType.as("goodsType"),
                        QbdItem010.bdItem010.material.as("material"),
                        QbdItem010.bdItem010.weight.as("weight"),
                        QbdItem010.bdItem010.divisionCd.as("divisionCd")
                ))
                .from(QbdItem010.bdItem010)
                .leftJoin(QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM).on(QbdItem010.bdItem010.goodsCd.eq(QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.goodsCd))
                .where(QbdItem010.bdItem010.deleteYn.eq("N"))
                .where(QbdItem010.bdItem010.divisionCd.eq(bu))
                .fetch();
    }

    public List<bdItem010> getHubCodeItem(List<String> code) {
        return queryFactory.select(Projections.bean(bdItem010.class,
                        QbdItem010.bdItem010.goodsCd.as("goodsCd"),
                        QbdItem010.bdItem010.goodsNm.as("goodsNm"),
                        QbdItem010.bdItem010.goodsType.as("goodsType"),
                        QbdItem010.bdItem010.material.as("material"),
                        QbdItem010.bdItem010.weight.as("weight"),
                        QbdItem010.bdItem010.divisionCd.as("divisionCd")
                ))
                .from(QbdItem010.bdItem010)
                .where(QbdItem010.bdItem010.deleteYn.eq("N"))
                .where(QbdItem010.bdItem010.goodsCd.notIn(code))
                .fetch();
    }


    public List<MEORDERDASHDTO> getOrderDashData(String fabcode, String itemcode, LocalDateTime startDate, LocalDateTime endDate) {
        return queryFactory.select(Projections.bean(MEORDERDASHDTO.class,
                        QMEOPWORK.mEOPWORK.eqpCode.as("eqpCode"),
                        QMEOPWORK.mEOPWORK.eqpName.as("eqpName"),
                        QMEOPWORK.mEOPWORK.itemCode.as("itemCode"),
                        QMEOPWORK.mEOPWORK.itemName.as("itemName"),
                        QMEOPWORK.mEOPWORK.inputTime.as("inputTime"),
                        QMEOPWORK.mEOPWORK.workItemCount.sum().as("itemCount")
                ))
                .from(QMEOPWORK.mEOPWORK)
                .where(QMEOPWORK.mEOPWORK.active.eq(true))
                .where(QMEOPWORK.mEOPWORK.fabCode.eq(fabcode))
                .where(QMEOPWORK.mEOPWORK.itemCode.eq(itemcode))
                .where(QMEOPWORK.mEOPWORK.regDt.between(startDate, endDate))
                .groupBy(QMEOPWORK.mEOPWORK.eqpName, QMEOPWORK.mEOPWORK.inputTime)
                .fetch();
    }


    public List<MEDFORDERDASHDTO> getOrderDashDFData(String fabcode, String itemcode, LocalDateTime startDate, LocalDateTime endDate) {
        return queryFactory.select(Projections.bean(MEDFORDERDASHDTO.class,
                        QMEOPDFWORK.mEOPDFWORK.workCode.as("workCode"),
                        QMEOPDFWORK.mEOPDFWORK.workName.as("workName"),
                        QMEOPDFWORK.mEOPDFWORK.itemCode.as("itemCode"),
                        QMEOPDFWORK.mEOPDFWORK.itemName.as("itemName"),
                        QMEOPDFWORK.mEOPDFWORK.inputTime.as("inputTime"),
                        QMEOPDFWORK.mEOPDFWORK.workItemCount.sum().as("itemCount")
                ))
                .from(QMEOPDFWORK.mEOPDFWORK)
                .where(QMEOPDFWORK.mEOPDFWORK.active.eq(true))
                .where(QMEOPDFWORK.mEOPDFWORK.fabCode.eq(fabcode))
                .where(QMEOPDFWORK.mEOPDFWORK.itemCode.eq(itemcode))
                .where(QMEOPDFWORK.mEOPDFWORK.regDt.between(startDate, endDate))
                .groupBy(QMEOPDFWORK.mEOPDFWORK.workName, QMEOPDFWORK.mEOPDFWORK.inputTime)
                .fetch();
    }


    public List<MEPKORDERDASHDTO> getPkWorkDashDFData(String fabcode, String itemcode, LocalDateTime startDate, LocalDateTime endDate) {
        return queryFactory.select(Projections.bean(MEPKORDERDASHDTO.class,
                        QMEPKWORK.mEPKWORK.itemCode.as("itemCode"),
                        QMEPKWORK.mEPKWORK.itemName.as("itemName"),
                        QMEPKWORK.mEPKWORK.inputTime.as("inputTime"),
                        QMEPKWORK.mEPKWORK.itemCount.sum().as("itemCount")
                ))
                .from(QMEPKWORK.mEPKWORK)
                .where(QMEPKWORK.mEPKWORK.active.eq(true))
                .where(QMEPKWORK.mEPKWORK.fabCode.eq(fabcode))
                .where(QMEPKWORK.mEPKWORK.itemCode.eq(itemcode))
                .where(QMEPKWORK.mEPKWORK.regDt.between(startDate, endDate))
                .groupBy(QMEPKWORK.mEPKWORK.inputTime)
                .fetch();
    }


    public List<MEITEMINVENTORYDTO> getItemInventoryData(String bucode, LocalDate workDay) {
        return queryFactory.select(Projections.bean(MEITEMINVENTORYDTO.class,
                        QMEITEMINVENTORY.mEITEMINVENTORY.id.as("id"),
                        QbdItem010.bdItem010.goodsCd.as("goodsCd"),
                        QbdItem010.bdItem010.goodsNm.as("goodsNm"),
                        QbdItem010.bdItem010.divisionCd.as("divisionCd"),
                        QMEDAYWORKORDER.mEDAYWORKORDER.totalWorkDfCount.as("wrokTotalItemPsCount"),
                        QMEDAYPKWORK.mEDAYPKWORK.totalPkWorkPsCount.as("pakingTotalItemPsCount"),
                        QMEDAYPKWORK.mEDAYPKWORK.totalOrderPsCount.as("orderTotalItemPsCount"),
                        QMEDAYWORKORDER.mEDAYWORKORDER.totalWorkDfCount.subtract( QMEDAYPKWORK.mEDAYPKWORK.totalPkWorkPsCount).as("saftItemCoun"),
                        QMEDAYWORKORDER.mEDAYWORKORDER.totalWorkDfCount.subtract( QMEDAYPKWORK.mEDAYPKWORK.totalPkWorkPsCount).as("saftItemPsCount"),
                        QMEITEMINVENTORY.mEITEMINVENTORY.itemCount.as("itemCount"),
                        QMEITEMINVENTORY.mEITEMINVENTORY.itemPsCount.as("itemPsCount"),
                        QMEITEMINVENTORY.mEITEMINVENTORY.itemDfPsCount.as("itemDfPsCount")

                ))
                .from(QbdItem010.bdItem010)
                .leftJoin(QMEITEMINVENTORY.mEITEMINVENTORY).on(QbdItem010.bdItem010.goodsCd.eq(QMEITEMINVENTORY.mEITEMINVENTORY.itemCode).and(QMEITEMINVENTORY.mEITEMINVENTORY.workStart.eq(workDay)))
                .leftJoin(QMEDAYWORKORDER.mEDAYWORKORDER).on(QbdItem010.bdItem010.goodsCd.eq(QMEDAYWORKORDER.mEDAYWORKORDER.goodsCd).and(QMEDAYWORKORDER.mEDAYWORKORDER.workStart.eq(workDay)))
                .leftJoin(QMEDAYPKWORK.mEDAYPKWORK).on(QbdItem010.bdItem010.goodsCd.eq(QMEDAYPKWORK.mEDAYPKWORK.goodsCd).and(QMEDAYPKWORK.mEDAYPKWORK.workStart.eq(workDay)))
                .where(QbdItem010.bdItem010.deleteYn.eq("N"))
                .where(QbdItem010.bdItem010.divisionCd.eq(bucode))
                .groupBy(QbdItem010.bdItem010.goodsCd, QbdItem010.bdItem010.goodsNm,
                        QbdItem010.bdItem010.divisionCd, QMEITEMINVENTORY.mEITEMINVENTORY.itemCount,
                        QMEITEMINVENTORY.mEITEMINVENTORY.itemPsCount, QMEITEMINVENTORY.mEITEMINVENTORY.itemDfPsCount)
                .fetch();
    }



    public List<MEORDERVALUEDTO> getOrderTHubItemList(String bucode, String accountCode, LocalDate workDate){
        return queryFactory
                .select(Projections.bean(MEORDERVALUEDTO.class,
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
                        QbdOdr011.bdOdr011.boxInQty.as("boxInQty")
                ))
                .from(QbdOdr010.bdOdr010)
                .innerJoin(QbdOdr011.bdOdr011).on(QbdOdr011.bdOdr011.orderNo.eq(QbdOdr010.bdOdr010.orderNo))
                .innerJoin(QbdItem010.bdItem010).on(QbdItem010.bdItem010.goodsCd.eq(QbdOdr011.bdOdr011.goodsCd))
                .innerJoin(QbdVdr010.bdVdr010).on(QbdVdr010.bdVdr010.vendorCd.eq(QbdOdr010.bdOdr010.vendorCd))
                .innerJoin(QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM).on(QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.goodsCd.eq(QbdOdr011.bdOdr011.goodsCd))
//                .where(QbdOdr010.bdOdr010.deliveryDate.eq(String.valueOf(workDate)))
                .where(QbdOdr010.bdOdr010.vendorCd.eq(accountCode))
                .where(QbdOdr010.bdOdr010.divisionCd.eq(bucode))
                .orderBy(QbdOdr010.bdOdr010.deliveryDate.asc(),QbdOdr010.bdOdr010.vendorCd.asc())
                .fetch();
    }


    public List<MEORDERTDTO> getOrderTHubItemORList(String bucode, String accountCode, LocalDate start, LocalDate end) {
        return queryFactory
                .select(Projections.bean(MEORDERTDTO.class,
                        QMEORDERTMAST.mEORDERTMAST.id.as("id"),
                        QMEORDERTMAST.mEORDERTMAST.orderKeyCode.as("orderKeyCode"),
                        QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.divisionCd.as("divisionCd"),
                        QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.fabCode.as("fabCode"),
                        QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.fabName.as("fabName"),
                        QMEORDERTMAST.mEORDERTMAST.accountCode.as("vendorCd"),
                        QMEORDERTMAST.mEORDERTMAST.accountName.as("vendorNm"),
                        QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.goodsCd.as("goodsCd"),
                        QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.goodsNm.as("goodsNm"),
                        QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.goodsType.as("goodsType"),
                        QMEORDERTMAST.mEORDERTMAST.workStart.as("workStart"),
                        QMEORDERTMAST.mEORDERTMAST.orderItemCount.as("orderItemCount"),
                        QMEORDERTMAST.mEORDERTMAST.regDt.as("regDt"),
                        QMEORDERTMAST.mEORDERTMAST.regCd.as("regCd"),
                        QMEORDERTMAST.mEORDERTMAST.modCd.as("modCd"),
                        QMEORDERTMAST.mEORDERTMAST.modDt.as("modDt")
                ))
                .from(QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM)
                .leftJoin(QMEORDERTMAST.mEORDERTMAST).on(QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.goodsCd.eq(QMEORDERTMAST.mEORDERTMAST.itemCode).and(QMEORDERTMAST.mEORDERTMAST.accountCode.eq(accountCode)).and(QMEORDERTMAST.mEORDERTMAST.workStart.between(start, end)))
                .where(QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.divisionCd.eq(bucode))
                .groupBy(QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.goodsCd, QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.goodsNm, QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.goodsType)
                .fetch();

    }







}
