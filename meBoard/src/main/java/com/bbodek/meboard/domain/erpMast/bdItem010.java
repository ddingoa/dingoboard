package com.bbodek.meboard.domain.erpMast;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Table(name = "bdItm010")
public class bdItem010 {
    //품목 마스터

    @Id
    @JsonProperty("goodsCd")
    @Column(length = 10, nullable = false)
    private String goodsCd;


    @Column(length = 50, nullable = false)
    @JsonProperty("goodsNm")
    private String goodsNm;

    @Column(length = 2, nullable = false)
    @JsonProperty("goodsType")
    private String goodsType;

    @Column(length = 4, nullable = false)
    @JsonProperty("goodsGroupCd")
    private String goodsGroupCd;

    @Column(length = 30)
    @JsonProperty("spec")
    private String spec;

    @Column(length = 30)
    @JsonProperty("material")
    private String material;

    @Column(length = 10)
    @JsonProperty("weight")
    private String weight;

    @Column(length = 20)
    @JsonProperty("color")
    private String color;

    @Column(length = 11)
    @JsonProperty("stdPrice")
    private int stdPrice;

    @Column(length = 11)
    @JsonProperty("purchasePrice")
    private int purchasePrice;

    @Column(length = 2, nullable = false)
    @JsonProperty("divisionCd")
    private String divisionCd;

    @Column(length = 2, nullable = false)
    @JsonProperty("assetType")
    private String assetType;

    @Column(length = 20)
    @JsonProperty("vendorCd")
    private String vendorCd;

    @Column(length = 200)
    @JsonProperty("remark")
    private String remark;

    @Column(length = 1, nullable = false)
    @JsonProperty("deleteYn")
    private String deleteYn;

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

}
