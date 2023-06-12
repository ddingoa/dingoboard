package com.bbodek.meboard.service.opsWorkerImpl;


import com.bbodek.meboard.domain.mast.QMEFABSETTINGMAST;
import com.bbodek.meboard.domain.mast.QMEORDERTMAST;
import com.bbodek.meboard.domain.opsWork.QMEOPSWORK;
import com.bbodek.meboard.domain.opsWork.QMEOPSWORKER;
import com.bbodek.meboard.domain.orderWork.QMEENTERITEM;
import com.bbodek.meboard.domain.orderWork.QMEOPDFWORK;
import com.bbodek.meboard.domain.orderWork.QMEOPWORK;
import com.bbodek.meboard.domain.product.QMEPRODUCTHUBITEM;
import com.bbodek.meboard.dto.mastData.MEENTERCOUNTDTO;
import com.bbodek.meboard.dto.mastData.MEORDERCOUNTDTO;
import com.bbodek.meboard.dto.mastData.MEWORKDFDTO;
import com.bbodek.meboard.dto.mastData.MEWORKORDERDTO;
import com.bbodek.meboard.dto.opsworker.MEOPSWORKERDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OpsWorkerServiceImpl implements OpsWorkerServiceImplRepository {

    private final JPAQueryFactory queryFactory;


    public List<MEOPSWORKERDTO> getWorkerTreeData(){
        return queryFactory
                .select(Projections.bean(MEOPSWORKERDTO.class,
                        QMEOPSWORK.mEOPSWORK.fabCode.as("fabCode"),
                        QMEOPSWORK.mEOPSWORK.fabName.as("fabName"),
                        QMEOPSWORK.mEOPSWORK.lineCode.as("lineCode"),
                        QMEOPSWORKER.mEOPSWORKER.lineName.as("lineName"),
                        QMEOPSWORKER.mEOPSWORKER.workCode.as("workCode"),
                        QMEOPSWORKER.mEOPSWORKER.workName.as("workName"),
                        QMEOPSWORKER.mEOPSWORKER.opCode.as("opCode"),
                        QMEOPSWORKER.mEOPSWORKER.opName.as("opName")
                ))
                .from(QMEOPSWORKER.mEOPSWORKER)
                .leftJoin(QMEOPSWORK.mEOPSWORK).on(QMEOPSWORKER.mEOPSWORKER.workCode.eq(QMEOPSWORK.mEOPSWORK.workCode))
                .where(QMEOPSWORKER.mEOPSWORKER.active.eq(true))
                .fetch();
    }

    //생산량
    public List<MEWORKORDERDTO> getWrokTotalCountData(LocalDateTime start, LocalDateTime end,String fabcode){
        return queryFactory
                .select(Projections.bean(MEWORKORDERDTO.class,
                        QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.goodsCd.as("itemCode"),
                        QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.goodsNm.as("itemName"),
                        QMEOPWORK.mEOPWORK.workItemCount.sum().as("totalWorkCount")
                ))
                .from(QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM)
                .leftJoin(QMEOPWORK.mEOPWORK).on(QMEOPWORK.mEOPWORK.itemCode.eq(QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.goodsCd).and(QMEOPWORK.mEOPWORK.regDt.between(start,end)))
                .where(QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.fabCode.eq(fabcode))
                .groupBy(QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.goodsCd,QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.goodsNm)
                .fetch();
    }
    //발주량
    public List<MEORDERCOUNTDTO> getOrderTotalCountData(LocalDate start, LocalDate end,String fabcode){
        return queryFactory
                .select(Projections.bean(MEORDERCOUNTDTO.class,
                        QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.goodsCd.as("itemCode"),
                        QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.goodsNm.as("itemName"),
                        QMEORDERTMAST.mEORDERTMAST.orderItemCount.sum().as("totalOrderCount")
                ))
                .from(QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM)
                .leftJoin(QMEORDERTMAST.mEORDERTMAST).on(QMEORDERTMAST.mEORDERTMAST.itemCode.eq(QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.goodsCd).and(QMEORDERTMAST.mEORDERTMAST.workStart.between(start,end)))
                .where(QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.fabCode.eq(fabcode))
                .groupBy(QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.goodsCd,QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.goodsNm)
                .fetch();
    }

    //입고량
    public List<MEENTERCOUNTDTO> getEnterTotalCountData(LocalDate start, LocalDate end, String fabcode){
        return queryFactory
                .select(Projections.bean(MEENTERCOUNTDTO.class,
                        QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.goodsCd.as("itemCode"),
                        QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.goodsNm.as("itemName"),
                        QMEENTERITEM.mEENTERITEM.enterItem.sum().as("totalEnterCount")
                ))
                .from(QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM)
                .leftJoin(QMEENTERITEM.mEENTERITEM).on(QMEENTERITEM.mEENTERITEM.itemCode.eq(QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.goodsCd).and(QMEENTERITEM.mEENTERITEM.workStart.between(start,end)))
                .where(QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.fabCode.eq(fabcode))
                .groupBy(QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.goodsCd,QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.goodsNm)
                .fetch();
    }

    //검수량
    public List<MEWORKDFDTO> getWrokDfTotalCountData(LocalDateTime start, LocalDateTime end, String fabcode){
        return queryFactory
                .select(Projections.bean(MEWORKDFDTO.class,
                        QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.goodsCd.as("itemCode"),
                        QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.goodsNm.as("itemName"),
                        QMEOPDFWORK.mEOPDFWORK.workItemCount.sum().as("totalWorkDfCount")
                ))
                .from(QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM)
                .leftJoin(QMEOPDFWORK.mEOPDFWORK).on(QMEOPDFWORK.mEOPDFWORK.itemCode.eq(QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.goodsCd).and(QMEOPDFWORK.mEOPDFWORK.regDt.between(start,end)))
                .where(QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.fabCode.eq(fabcode))
                .groupBy(QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.goodsCd,QMEPRODUCTHUBITEM.mEPRODUCTHUBITEM.goodsNm)
                .fetch();
    }





}
