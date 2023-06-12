package com.bbodek.meboard.domain.erpMast;

import com.bbodek.meboard.domain.erpMast.ID.bdVdr020ID;
import com.bbodek.meboard.domain.erpMast.ID.bdVdr031ID;
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
@IdClass(bdVdr031ID.class)
@Table(name = "bdVdr031")
public class bdVdr031 implements Serializable {
    //kids회원
    @Id
    @JsonProperty("memberId")
    @Column(length = 7, nullable = false)
    private String memberId;

    @Id
    @JsonProperty("vendorCd")
    @Column(length = 20, nullable = false)
    private String vendorCd;

    @Id
    @JsonProperty("classCd")
    @Column(length = 2, nullable = false)
    private String classCd;

    @Id
    @JsonProperty("goodsCd")
    @Column(length = 10, nullable = false)
    private String goodsCd;

    @Column(length = 4, nullable = false)
    @JsonProperty("goodsGroupCd")
    private String goodsGroupCd;

    @Column(length = 2, nullable = false)
    @JsonProperty("bizUnit")
    private String bizUnit;

    @Column(length = 11, nullable = false)
    @JsonProperty("cntractPrice")
    private int cntractPrice;

    @Column(length = 11, nullable = false)
    @JsonProperty("cntractQty")
    private int cntractQty;

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
