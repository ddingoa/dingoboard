package com.bbodek.meboard.service.orderImpl;

import com.bbodek.meboard.domain.erpMast.bdItem010;
import com.bbodek.meboard.domain.product.MEPRODUCTHUBITEM;
import com.bbodek.meboard.dto.mastData.MEITEMINVENTORYDTO;
import com.bbodek.meboard.dto.mastData.MEORDERTDTO;
import com.bbodek.meboard.dto.orderData.MEDFORDERDASHDTO;
import com.bbodek.meboard.dto.orderData.MENONENTERITEMDTO;
import com.bbodek.meboard.dto.orderData.MEORDERDASHDTO;
import com.bbodek.meboard.dto.orderData.MEORDERVALUEDTO;
import com.bbodek.meboard.dto.pkOrderData.MEPKORDERDASHDTO;
import com.bbodek.meboard.dto.pkOrderData.MEPKORDERDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ProductServiceImplRepository {
    List<String> getItemTypeData(String bucode);
    List<MENONENTERITEMDTO> getNonEnterItemData();
    List<MENONENTERITEMDTO> getNonEnterItemData(String bu, String fabcode, LocalDate startdate, LocalDate enddate,String accountCode);

    List<MEPRODUCTHUBITEM> getHubItemList(String bu);

    List<bdItem010> getHubCodeItem(List<String> code);

    List<MEORDERDASHDTO> getOrderDashData(String fabcode, String itemcode, LocalDateTime startDate , LocalDateTime endDate);

    List<MEDFORDERDASHDTO> getOrderDashDFData(String fabcode, String itemcode, LocalDateTime startDate , LocalDateTime endDate);


    List<MEPKORDERDASHDTO> getPkWorkDashDFData(String fabcode, String itemcode, LocalDateTime startDate , LocalDateTime endDate);



    List<MEITEMINVENTORYDTO> getItemInventoryData(String bucode,LocalDate workDay);


    List<MEORDERVALUEDTO> getOrderTHubItemList(String bucode, String accountCode, LocalDate workDay);

    List<MEORDERTDTO> getOrderTHubItemORList(String bucode, String accountCode, LocalDate start, LocalDate end);
}
