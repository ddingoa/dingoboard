package com.bbodek.meboard.service.dashboardImpl;

import com.bbodek.meboard.domain.opsWork.QMEOPSWORK;
import com.bbodek.meboard.domain.opsWork.QMEOPSWORKER;
import com.bbodek.meboard.domain.orderWork.QMEDAYPKWORK;
import com.bbodek.meboard.domain.orderWork.QMEDAYWORKORDER;
import com.bbodek.meboard.domain.orderWork.QMEOPDFWORK;
import com.bbodek.meboard.domain.orderWork.QMEOPWORK;
import com.bbodek.meboard.domain.pkOrderWork.QMEPKKIDSWORK;
import com.bbodek.meboard.domain.pkOrderWork.QMEPKWORK;
import com.bbodek.meboard.dto.dashboardDTO.*;
import com.bbodek.meboard.dto.opsworker.MEOPSWORKERDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardServiceImplRepository {
    private final JPAQueryFactory queryFactory;

    //세척량 & 검수량
    public List<MEDASHBOARDDAYWORKDTO> getDayWorkDfData(LocalDate date, String fabCode) {
        return queryFactory
                .select(Projections.bean(MEDASHBOARDDAYWORKDTO.class,
                        QMEDAYWORKORDER.mEDAYWORKORDER.totalWorkCount.sum().as("dayWorkCount"),
                        QMEDAYWORKORDER.mEDAYWORKORDER.totalWorkDfCount.sum().as("dayDfWorkCount")
                ))
                .from(QMEDAYWORKORDER.mEDAYWORKORDER)
                .where(QMEDAYWORKORDER.mEDAYWORKORDER.workStart.eq(date))
                .where(QMEDAYWORKORDER.mEDAYWORKORDER.fabCode.eq(fabCode))
                .fetch();
    }

    //포장 수량 (BOX)
    public List<MEDASHBOARDPKDTO> getDayPkData(LocalDate date, String fabCode) {
        return queryFactory
                .select(Projections.bean(MEDASHBOARDPKDTO.class,
                        QMEDAYPKWORK.mEDAYPKWORK.totalPkWorkCount.sum().as("dayPkCount")
                ))
                .from(QMEDAYPKWORK.mEDAYPKWORK)
                .where(QMEDAYPKWORK.mEDAYPKWORK.workStart.eq(date))
                .where(QMEDAYPKWORK.mEDAYPKWORK.fabCode.eq(fabCode))
                .fetch();
    }


    //시간대별 세척량
    public List<MEDSAHBOARDWORKCHARTDTO> getDayTimeWorkData(LocalDate date, String fabCode) {
        return queryFactory
                .select(Projections.bean(MEDSAHBOARDWORKCHARTDTO.class,
                        QMEOPWORK.mEOPWORK.workStart.as("workStart"),
                        QMEOPWORK.mEOPWORK.inputTime.as("inputTime"),
                        QMEOPWORK.mEOPWORK.itemName.as("itemName"),
                        QMEOPWORK.mEOPWORK.workItemCount.sum().as("workItemCount")
                ))
                .from(QMEOPWORK.mEOPWORK)
                .where(QMEOPWORK.mEOPWORK.workStart.eq(date))
                .where(QMEOPWORK.mEOPWORK.fabCode.eq(fabCode))
                .groupBy(QMEOPWORK.mEOPWORK.workStart, QMEOPWORK.mEOPWORK.inputTime,QMEOPWORK.mEOPWORK.itemName)
                .fetch();
    }

    //시간대별 검수량
    public List<MEDSAHBOARDWORKDFCHARTDTO> getDayTimeWorkDFData(LocalDate date, String fabCode) {
        return queryFactory
                .select(Projections.bean(MEDSAHBOARDWORKDFCHARTDTO.class,
                        QMEOPDFWORK.mEOPDFWORK.workStart.as("workStart"),
                        QMEOPDFWORK.mEOPDFWORK.inputTime.as("inputTime"),
                        QMEOPDFWORK.mEOPDFWORK.itemName.as("itemName"),
                        QMEOPDFWORK.mEOPDFWORK.workItemCount.sum().as("workItemCount")
                ))
                .from(QMEOPDFWORK.mEOPDFWORK)
                .where(QMEOPDFWORK.mEOPDFWORK.workStart.eq(date))
                .where(QMEOPDFWORK.mEOPDFWORK.fabCode.eq(fabCode))
                .groupBy(QMEOPDFWORK.mEOPDFWORK.workStart, QMEOPDFWORK.mEOPDFWORK.inputTime, QMEOPDFWORK.mEOPDFWORK.itemName)
                .fetch();
    }

    //포장 수량
    public List<MEDASHBOARDPKCOUNTLISTDTO> getDayPkCountListData(LocalDate date, String fabCode) {
        return queryFactory
                .select(Projections.bean(MEDASHBOARDPKCOUNTLISTDTO.class,
                        QMEPKWORK.mEPKWORK.itemName.as("itemName"),
                        QMEPKWORK.mEPKWORK.itemCount.sum().as("itemCount")
                ))
                .from(QMEPKWORK.mEPKWORK)
                .where(QMEPKWORK.mEPKWORK.workStart.eq(date))
                .where(QMEPKWORK.mEPKWORK.fabCode.eq(fabCode))
                .groupBy(QMEPKWORK.mEPKWORK.itemName)
                .fetch();
    }

    //키즈 포장 리스트
    public List<MEDASHBOARDPKCOUNTLISTKIDSDTO> getDayPkKidsCountListData(LocalDate date, String fabCode) {
        return queryFactory
                .select(Projections.bean(MEDASHBOARDPKCOUNTLISTKIDSDTO.class,
                        QMEPKKIDSWORK.mEPKKIDSWORK.vendorNm.as("vendorNm"),
                        QMEPKKIDSWORK.mEPKKIDSWORK.className.as("className")
                ))
                .from(QMEPKKIDSWORK.mEPKKIDSWORK)
                .where(QMEPKKIDSWORK.mEPKKIDSWORK.workStart.eq(date))
                .where(QMEPKKIDSWORK.mEPKKIDSWORK.fabCode.eq(fabCode))
                .groupBy(QMEPKKIDSWORK.mEPKKIDSWORK.vendorNm, QMEPKKIDSWORK.mEPKKIDSWORK.className)
                .fetch();
    }

}
