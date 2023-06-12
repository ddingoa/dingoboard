package com.bbodek.meboard.domain.erpMast;

import com.bbodek.meboard.domain.erpMast.ID.bdOdr012ID;
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
@IdClass(bdOdr012ID.class)
@Table(name = "bdOdr012")
public class bdOdr012 implements Serializable {
    //Kids 주문 상세
    @Id
    @JsonProperty("orderNo")
    @Column(length = 15, nullable = false)
    private String orderNo;

    @Id
    @JsonProperty("ordernoSeq")
    @Column(length = 15, nullable = false)
    private String ordernoSeq;

    @Id
    @JsonProperty("classCd")
    @Column(length = 2, nullable = false)
    private String classCd;

    @Id
    @JsonProperty("memberId")
    @Column(length = 7, nullable = false)
    private String memberId;


    @Column(length = 10, nullable = false)
    @JsonProperty("goodsCd")
    private String goodsCd;

    @Column(length = 5, nullable = false)
    @JsonProperty("bizUnit")
    private String bizUnit;

    @Column(length = 6, nullable = false)
    @JsonProperty("orderQty")
    private int orderQty;

    @Column(length = 6, nullable = false)
    @JsonProperty("eaQty")
    private int eaQty;


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
