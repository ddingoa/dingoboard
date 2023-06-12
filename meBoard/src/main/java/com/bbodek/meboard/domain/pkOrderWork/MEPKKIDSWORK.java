package com.bbodek.meboard.domain.pkOrderWork;

import com.bbodek.meboard.domain.config.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Setter
@Table(name = "mePkKidsWork")
public class MEPKKIDSWORK extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 80, nullable = false)
    @JsonProperty("pkoCode")
    private String pkoCode;

    @Column(length = 80, nullable = false)
    @JsonProperty("orderNo")
    private String orderNo;

    @Column(length = 80)
    @JsonProperty("fabCode")
    private String fabCode;

    @Column(length = 80)
    @JsonProperty("fabName")
    private String fabName;

    @Column
    @JsonProperty("workStart")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate workStart;

    @Column(nullable = false)
    @JsonProperty("inputTime")
    private int inputTime;

    @Column(length = 15, nullable = false)
    @JsonProperty("orderDate")
    private String orderDate;

    @Column(length = 15, nullable = false)
    @JsonProperty("deliveryDate")
    private String deliveryDate;

    @Column(length = 15, nullable = false)
    @JsonProperty("useDate")
    private String useDate;

    @Column(length = 15, nullable = false)
    @JsonProperty("pickupDate")
    private String pickupDate;

    @Column(length = 80, nullable = false)
    @JsonProperty("deliveryCode")
    private String deliveryCode;

    @Column(length = 80, nullable = false)
    @JsonProperty("bizUnit")
    private String bizUnit;

    @Column(length = 80, nullable = false)
    @JsonProperty("vendorCd")
    private String vendorCd;

    @Column(length = 80, nullable = false)
    @JsonProperty("address1")
    private String address1;

    @Column(length = 80, nullable = false)
    @JsonProperty("vendorNm")
    private String vendorNm;

    @Column(length = 80, nullable = false)
    @JsonProperty("classCd")
    private String classCd;

    @Column(length = 80, nullable = false)
    @JsonProperty("className")
    private String className;

    @Column(length = 80, nullable = false)
    @JsonProperty("goodsCd")
    private String goodsCd;

    @Column(length = 80)
    @JsonProperty("goodsNm")
    private String goodsNm;

    @Column(length = 80, nullable = false)
    @JsonProperty("itemPsCount")
    private int itemPsCount;

    @Column(length = 80)
    @JsonProperty("pkingCheck")
    private boolean pkingCheck;

    @Column(length = 10)
    @JsonProperty("daySeq")
    private String daySeq;

    @Builder.Default
    @JsonProperty("active")
    @Column(name = "active")
    private boolean active = true;

    @Column(length = 20, updatable = false)
    @JsonProperty("regCd")
    private String regCd;

    @Column(length = 20, insertable = false)
    @JsonProperty("modCd")
    private String modCd;
}
