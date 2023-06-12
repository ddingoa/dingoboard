package com.bbodek.meboard.domain.erpMast;

import com.bbodek.meboard.domain.erpMast.ID.bdOdr012ID;
import com.bbodek.meboard.domain.erpMast.ID.bdVdr020ID;
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
@IdClass(bdVdr020ID.class)
@Table(name = "bdVdr020")
public class bdVdr020 implements Serializable {
    //교육기관 반
    @Id
    @JsonProperty("vendorCd")
    @Column(length = 20, nullable = false)
    private String vendorCd;

    @Id
    @JsonProperty("classCd")
    @Column(length = 2, nullable = false)
    private String classCd;

    @Column(length = 30, nullable = false)
    @JsonProperty("className")
    private String className;

    @Column(length = 11)
    @JsonProperty("classNumber")
    private int classNumber;

    @Column(length = 11)
    @JsonProperty("useNumber")
    private int useNumber;

    @Column(length = 11)
    @JsonProperty("teacherQty")
    private int teacherQty;

    @Column(length = 8)
    @JsonProperty("freeOpendate")
    private String freeOpendate;

    @Column(length = 8)
    @JsonProperty("freeClosedate")
    private String freeClosedate;

    @Column(length = 8)
    @JsonProperty("chargeOpendate")
    private String chargeOpendate;

    @Column(length = 1, nullable = false)
    @JsonProperty("vendorSt")
    private String vendorSt;

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
