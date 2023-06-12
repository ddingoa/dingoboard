package com.bbodek.meboard.dto.orderData;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MEORDERVALUEDTO {
    private String orderNo;
    private String fabCode;
    private String fabName;
    private String divisionCd;
    private String vendorCd;
    private String vendorNm;
    private String orderDate;
    private String deliveryDate;
    private String useDate;
    private String pickupDate;
    private String goodsCd;
    private String goodsNm;
    private int orderQty;
    private int eaQty;
    private int boxInQty;
}