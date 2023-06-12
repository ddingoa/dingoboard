package com.bbodek.meboard.service.dashboardImpl;

import com.bbodek.meboard.dto.dashboardDTO.*;

import java.time.LocalDate;
import java.util.List;

public interface DashboardServiceImplRepository {
    List<MEDASHBOARDDAYWORKDTO> getDayWorkDfData(LocalDate date, String fabCode);
    List<MEDASHBOARDPKDTO> getDayPkData(LocalDate date, String fabCode);
    List<MEDSAHBOARDWORKCHARTDTO> getDayTimeWorkData(LocalDate date, String fabCode);
    List<MEDSAHBOARDWORKDFCHARTDTO> getDayTimeWorkDFData(LocalDate date, String fabCode);
    List<MEDASHBOARDPKCOUNTLISTDTO> getDayPkCountListData(LocalDate date, String fabCode);
    List<MEDASHBOARDPKCOUNTLISTKIDSDTO> getDayPkKidsCountListData(LocalDate date, String fabCode);
}
