package com.bbodek.meboard.dto.other;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KIDSORDERMASTSAVE {
    private String orderNo;
    private String orderDate;
    private String vendorCd;
    private String divisionCd;
    private String chargeYn;
    private int orderAmount;
    private String deliveryDate;
    private String useDate;
    private String pickupDate;
    private String ordernoSeq;
    private String classCd;
    private String memberId;
    private String goodsCd;
    private String bizUnit;
    private int orderQty;
    private int eaQty;
    private String remark;
    private String regUserId;
    private String modiUserId;
    private String closeYn;
    private String keyinType;

}
