package com.bbodek.meboard.service.opsWorkerImpl;

import com.bbodek.meboard.dto.mastData.MEENTERCOUNTDTO;
import com.bbodek.meboard.dto.mastData.MEORDERCOUNTDTO;
import com.bbodek.meboard.dto.mastData.MEWORKDFDTO;
import com.bbodek.meboard.dto.mastData.MEWORKORDERDTO;
import com.bbodek.meboard.dto.opsworker.MEOPSWORKERDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OpsWorkerServiceImplRepository {

    List<MEOPSWORKERDTO> getWorkerTreeData();
    List<MEWORKORDERDTO> getWrokTotalCountData(LocalDateTime start, LocalDateTime end, String fabcode);
    List<MEORDERCOUNTDTO> getOrderTotalCountData(LocalDate start, LocalDate end, String fabcode);
    List<MEENTERCOUNTDTO> getEnterTotalCountData(LocalDate start, LocalDate end, String fabcode);

    List<MEWORKDFDTO> getWrokDfTotalCountData(LocalDateTime start, LocalDateTime end, String fabcode);
}
