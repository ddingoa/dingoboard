package com.bbodek.meboard.dto.pkOrderData;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MEKIDSDELIVERYDTO {
    private String orderNo;
    private String vendorCd;
    private String vendorNm;
    private String orderDate;
    private String deliveryDate;
    private String useDate;
    private String pickupDate;
    private String classCd;
    private String address1;
    private String className;
    private String bizUnit;
    private int classNumber;
    private int teacherQty;
    private String deliveryCode;
    private String daySeq;
    private boolean pkingCheck;
    private String goodsCd;
    private int eaQty;
//    private int RRKIS01;
//    private int RRKIS02;
//    private int RRKIS03;
//    private int RRKIS04;
//    private int RRKIS05;
//    private int RRKIS06;
//    private int RRKIS07;
//    private int RRKIS08;
//    private int RRKIS09;
}
