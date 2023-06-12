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
@Table(name = "bdVdr010")
public class bdVdr010 {
    //거래처 마스터
    @Id
    @JsonProperty("vendorCd")
    @Column(length = 20, nullable = false)
    private String vendorCd;

    @Column(length = 40, nullable = false)
    @JsonProperty("vendorNm")
    private String vendorNm;

    @Column(length = 40)
    @JsonProperty("ceoNm")
    private String ceoNm;

    @Column(length = 10, nullable = false)
    @JsonProperty("regno")
    private String regno;

    @Column(length = 30)
    @JsonProperty("bizStatus")
    private String bizStatus;

    @Column(length = 30)
    @JsonProperty("bizClass")
    private String bizClass;

    @Column(length = 6)
    @JsonProperty("zipCd")
    private String zipCd;

    @Column(length = 100)
    @JsonProperty("address1")
    private String address1;

    @Column(length = 100)
    @JsonProperty("address2")
    private String address2;

    @Column(length = 20)
    @JsonProperty("telno")
    private String telno;

    @Column(length = 20)
    @JsonProperty("faxno")
    private String faxno;

    @Column(length = 2, nullable = false)
    @JsonProperty("custmerType")
    private String custmerType;

    @Column(length = 2)
    @JsonProperty("divisionCd")
    private String divisionCd;

    @Column(length = 2, nullable = false)
    @JsonProperty("payType")
    private String payType;

    @Column(length = 6)
    @JsonProperty("bdEmpNo")
    private String bdEmpNo;

    @Column(length = 30)
    @JsonProperty("orderMan")
    private String orderMan;

    @Column(length = 30)
    @JsonProperty("orderDesk")
    private String orderDesk;

    @Column(length = 30)
    @JsonProperty("adjDesk")
    private String adjDesk;

    @Column(length = 30)
    @JsonProperty("adjMan")
    private String adjMan;

    @Column(length = 30)
    @JsonProperty("taxRcpPlace")
    private String taxRcpPlace;

    @Column(length = 2, nullable = false)
    @JsonProperty("adjType")
    private String adjType;

    @Column(length = 8)
    @JsonProperty("adjDate")
    private String adjDate;

    @Column(length = 2)
    @JsonProperty("custmerJob")
    private String custmerJob;

    @Column(length = 2)
    @JsonProperty("saleType")
    private String saleType;

    @Column(length = 200)
    @JsonProperty("remark")
    private String remark;

    @Column(length = 2, nullable = false)
    @JsonProperty("vendorSt")
    private String vendorSt;

    @Column(length = 8)
    @JsonProperty("chargeOpendate")
    private String chargeOpendate;

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

    @Column(length = 2)
    @JsonProperty("centerCd")
    private String centerCd;

    @Column(length = 2)
    @JsonProperty("dlvVendorType")
    private String dlvVendorType;

    @Column(length = 20)
    @JsonProperty("cmsNo")
    private String cmsNo;

    @Column(length = 2, nullable = false)
    @JsonProperty("orderPrType")
    private String orderPrType;

    @Column(length = 2, nullable = false)
    @JsonProperty("deliveryType")
    private String deliveryType;

    @Column(length = 10)
    @JsonProperty("vendorCmsCd")
    private String vendorCmsCd;


}
