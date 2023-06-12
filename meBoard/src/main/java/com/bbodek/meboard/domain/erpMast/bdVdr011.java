package com.bbodek.meboard.domain.erpMast;

import com.bbodek.meboard.domain.erpMast.ID.bdOdr011ID;
import com.bbodek.meboard.domain.erpMast.ID.bdVdr011ID;
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
@IdClass(bdVdr011ID.class)
@Table(name = "bdVdr011")
public class bdVdr011 implements Serializable {

    @Id
    @JsonProperty("vendorCd")
    @Column(length = 20, nullable = false)
    private String vendorCd;

    @Id
    @JsonProperty("deliveryNo")
    @Column(length = 3, nullable = false)
    private String deliveryNo;

    @Column(length = 50, nullable = false)
    @JsonProperty("deliveryNm")
    private String deliveryNm;

    @Column(length = 100)
    @JsonProperty("telno")
    private String telno;

    @Column(length = 7)
    @JsonProperty("deliberVendorCd")
    private String deliberVendorCd;

    @Column(length = 300)
    @JsonProperty("deliverRemark")
    private String deliverRemark;

    @Column(length = 300)
    @JsonProperty("parkingRemark")
    private String parkingRemark;

    @Column(length = 30)
    @JsonProperty("acceptNm")
    private String acceptNm;

    @Column(length = 20)
    @JsonProperty("regUserId")
    private String regUserId;

    @Column(length = 20)
    @JsonProperty("regDate")
    private String regDate;

    @Column(length = 20)
    @JsonProperty("modiUserId")
    private String modiUserId;

    @Column(length = 20)
    @JsonProperty("modiDate")
    private String modiDate;

    @Column(length = 6)
    @JsonProperty("zipCd")
    private String zipCd;

    @Column(length = 100)
    @JsonProperty("address1")
    private String address1;

    @Column(length = 100)
    @JsonProperty("address2")
    private String address2;

    @Column(length = 100)
    @JsonProperty("deliverVendorNm")
    private String deliverVendorNm;

}
