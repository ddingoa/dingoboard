package com.bbodek.meboard.dto.erpDTO;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class erpBizOdrDTO {
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
        private String deliveryNo;
        private String classCd;
        private String goodsCd;
        private String goodsGroupCd;
        private String bizUnit;
        private int orderQty;
        private int boxInQty;
        private int eaQty;
        private int packQty;
        private int deliveryQty;
        private int pickupQty;
        private String remark;
        private String regUserId;
        private String regDate;
        private String modiUserId;
        private String modiDate;
        private String closeYn;
        private String keyinType;
}
