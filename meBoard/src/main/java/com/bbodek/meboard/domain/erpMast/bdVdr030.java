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
@Table(name = "bdVdr030")
public class bdVdr030 {
    //kids회원
    @Id
    @JsonProperty("memberId")
    @Column(length = 7, nullable = false)
    private String memberId;

    @Column(length = 20, nullable = false)
    @JsonProperty("vendorCd")
    private String vendorCd;

    @Column(length = 2, nullable = false)
    @JsonProperty("classCd")
    private String classCd;

    @Column(length = 45, nullable = false)
    @JsonProperty("memberName")
    private String memberName;

    @Column(length = 20)
    @JsonProperty("telno")
    private String telno;

    @Column(length = 20, nullable = false)
    @JsonProperty("parentsName")
    private String parentsName;

    @Column(length = 20, nullable = false)
    @JsonProperty("cmsNo")
    private String cmsNo;

    @Column(length = 11, nullable = false)
    @JsonProperty("monthAmount")
    private int monthAmount;

    @Column(length = 8, nullable = false)
    @JsonProperty("requestDate")
    private String requestDate;

    @Column(length = 8, nullable = false)
    @JsonProperty("openDate")
    private String openDate;

    @Column(length = 8)
    @JsonProperty("closeDate")
    private String closeDate;

    @Column(length = 1, nullable = false)
    @JsonProperty("vendorSt")
    private String vendorSt;

    @Column(length = 1, nullable = false)
    @JsonProperty("cmsSt")
    private String cmsSt;

    @Column(length = 30)
    @JsonProperty("kidsnoteCd")
    private String kidsnoteCd;

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
