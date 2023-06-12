package com.bbodek.meboard.domain.erpMast;

import com.bbodek.meboard.domain.erpMast.ID.bdItem012ID;
import com.bbodek.meboard.domain.erpMast.ID.bdOdr011ID;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@IdClass(bdOdr011ID.class)
@Table(name = "bdOdr011")
public class bdOdr011 implements Serializable {
    //주문 Detail
    @Id
    @JsonProperty("orderNo")
    @Column(length = 15, nullable = false)
    private String orderNo;

    @Id
    @JsonProperty("ordernoSeq")
    @Column(length = 15, nullable = false)
    private String ordernoSeq;

    @Id
    @JsonProperty("deliveryNo")
    @Column(length = 15, nullable = false)
    private String deliveryNo;


    @JsonProperty("classCd")
    @Column(length = 2, nullable = false)
    private String classCd;

    @Column(length = 10, nullable = false)
    @JsonProperty("goodsCd")
    private String goodsCd;

    @Column(length = 4, nullable = false)
    @JsonProperty("goodsGroupCd")
    private String goodsGroupCd;

    @Column(length = 10, nullable = false)
    @JsonProperty("bizUnit")
    private String bizUnit;

    @Column(length = 6, nullable = false)
    @JsonProperty("orderQty")
    private int orderQty;

    @Column(length = 6, nullable = false)
    @JsonProperty("boxInQty")
    private int boxInQty;

    @Column(length = 6, nullable = false)
    @JsonProperty("eaQty")
    private int eaQty;

    @Column(length = 6, nullable = false)
    @JsonProperty("packQty")
    private int packQty;

    @Column(length = 6, nullable = false)
    @JsonProperty("deliveryQty")
    private int deliveryQty;

    @Column(length = 6, nullable = false)
    @JsonProperty("pickupQty")
    private int pickupQty;

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


}
