package com.bbodek.meboard.dto.orderData;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MENONENTERITEMDTO {
    private String vendorCd;
    private String vendorNm;
    private String divisionCd;
    private String itemName;
    private String enterItem;
    private String enterCode;
    private String dfCount;
    private LocalDate workStart;
    private String nonitemcount;

}
