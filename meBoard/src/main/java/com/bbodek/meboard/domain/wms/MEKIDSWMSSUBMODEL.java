package com.bbodek.meboard.domain.wms;

import com.bbodek.meboard.domain.config.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Setter
@Table(name = "meKidsWmsSubModel")
public class MEKIDSWMSSUBMODEL extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    @JsonProperty("deliveryCode")
    private String deliveryCode;

    @Column(length = 80)
    @JsonProperty("address1")
    private String address1;

    @Column(length = 80)
    @JsonProperty("vendorCd")
    private String vendorCd;

    @Column(length = 80)
    @JsonProperty("vendorNm")
    private String vendorNm;

    @Column(length = 50, updatable = false)
    @JsonProperty("regCd")
    private String regCd;

    @Column(length = 50, insertable = false)
    @JsonProperty("modCd")
    private String modCd;

}
