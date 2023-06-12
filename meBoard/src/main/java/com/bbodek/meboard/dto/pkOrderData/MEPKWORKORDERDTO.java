package com.bbodek.meboard.dto.pkOrderData;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MEPKWORKORDERDTO {
    private String goodsCd;
    private String goodsNm;
    private int totalPkWorkCount;
    private int totalPkWorkPsCount;
}
