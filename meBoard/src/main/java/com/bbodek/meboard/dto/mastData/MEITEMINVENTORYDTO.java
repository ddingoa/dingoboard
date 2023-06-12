package com.bbodek.meboard.dto.mastData;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MEITEMINVENTORYDTO {
    private long id;
    private String goodsCd;
    private String goodsNm;
    private String divisionCd;
    private int itemCount;
    private int itemPsCount;
    private int itemDfPsCount;
    private int wrokTotalItemPsCount;
    private int pakingTotalItemPsCount;
    private int orderTotalItemPsCount;
    private int saftItemCoun;
    private int saftItemPsCount;

}
