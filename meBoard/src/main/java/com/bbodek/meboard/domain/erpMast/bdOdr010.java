package com.bbodek.meboard.domain.erpMast;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Table(name = "bdOdr010")
public class bdOdr010 {
    //주문Head
    @Id
    @JsonProperty("orderNo")
    @Column(length = 15, nullable = false)
    private String orderNo;

    @Column(length = 15, nullable = false)
    @JsonProperty("orderDate")
    private String orderDate;

    @Column(length = 20, nullable = false)
    @JsonProperty("vendorCd")
    private String vendorCd;

    @Column(length = 2, nullable = false)
    @JsonProperty("divisionCd")
    private String divisionCd;

    @Column(length = 1, nullable = false)
    @JsonProperty("chargeYn")
    private String chargeYn;

    @Column(length = 8, nullable = false)
    @JsonProperty("orderAmount")
    private int orderAmount;

    @Column(length = 15, nullable = false)
    @JsonProperty("deliveryDate")
    private String deliveryDate;

    @Column(length = 15, nullable = false)
    @JsonProperty("useDate")
    private String useDate;

    @Column(length = 15, nullable = false)
    @JsonProperty("pickupDate")
    private String pickupDate;

    @Column(length = 200)
    @JsonProperty("remark")
    private String remark;

    @Column(length = 20, nullable = false)
    @JsonProperty("regUserId")
    private String regUserId;

    @Column(length = 20, nullable = false)
    @JsonProperty("regDate")
    private String regDate;

    @Column(length = 20, nullable = false)
    @JsonProperty("modiUserId")
    private String modiUserId;

    @Column(length = 20, nullable = false)
    @JsonProperty("modiDate")
    private String modiDate;

    @Column(length = 1, nullable = false)
    @JsonProperty("closeYn")
    private String closeYn;

    @Column(length = 5, nullable = false)
    @JsonProperty("keyinType")
    private String keyinType;


}
