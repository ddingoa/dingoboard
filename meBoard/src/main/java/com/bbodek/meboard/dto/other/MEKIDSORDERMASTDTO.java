package com.bbodek.meboard.dto.other;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MEKIDSORDERMASTDTO {
    private String vendorCd;
    private String vendorNm;
    private String classCd;
    private String className;
    private int classNumber;
    private String address1;
    private String deliveryCode;
    private String goodsCd;
    private String goodsNm;
}
