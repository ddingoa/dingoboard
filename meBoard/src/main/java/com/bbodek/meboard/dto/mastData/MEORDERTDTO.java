package com.bbodek.meboard.dto.mastData;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MEORDERTDTO {
    private long id;
    private LocalDate workStart;
    private String orderKeyCode;
    private String vendorCd;
    private String vendorNm;
    private String goodsType;
    private String goodsCd;
    private String goodsNm;
    private String divisionCd;
    private int orderItemCount;
    private String insManger;
}
