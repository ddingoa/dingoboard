package com.bbodek.meboard.service.otherService;

import com.bbodek.meboard.dto.other.MEKIDSORDERMASTDTO;
import com.bbodek.meboard.dto.pkOrderData.MEKIDSDELIVERYDTO;

import java.time.LocalDate;
import java.util.List;

public interface KidsWmsModelServiceImplRepository {

    List<MEKIDSDELIVERYDTO> getKidsDeliveryData(String fabcode, LocalDate workDate,String dayCode);
    List<MEKIDSORDERMASTDTO> getKidsOrderMastData(String fabcode, String divisionCd);
}
