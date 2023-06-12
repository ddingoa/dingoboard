package com.bbodek.meboard.service.pkOrderImpl;

import com.bbodek.meboard.dto.pkOrderData.MEKIDSDELIVERYDTO;
import com.bbodek.meboard.dto.pkOrderData.MEPKORDERDTO;

import java.time.LocalDate;
import java.util.List;

public interface PkOrderServiceImplRepository {

    List<MEPKORDERDTO> getPkOrderData(String fabcode, LocalDate workDate);
}
