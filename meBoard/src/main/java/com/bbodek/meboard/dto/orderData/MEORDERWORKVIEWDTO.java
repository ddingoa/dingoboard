package com.bbodek.meboard.dto.orderData;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MEORDERWORKVIEWDTO {
    private LocalDate workStart;
    private String goodsCd;
    private String goodsNm;
    private int totalEnterCount;
    private int totalOrderPsCount;
    private int totalWorkCount;
    private int totalWorkDfCount;
}
