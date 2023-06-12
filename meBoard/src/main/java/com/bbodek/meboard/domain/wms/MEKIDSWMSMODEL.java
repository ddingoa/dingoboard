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
@Table(name = "meKidsWmsModel")
public class MEKIDSWMSMODEL extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    @JsonProperty("deliveryCode")
    private String deliveryCode;

    @Column(length = 80)
    @JsonProperty("deliveryMd")
    private String deliveryMd;

    @Column(length = 10)
    @JsonProperty("dayCode")
    private String dayCode;

    @Column(length = 10)
    @JsonProperty("daySeq")
    private String daySeq;

    @Column(length = 50, updatable = false)
    @JsonProperty("regCd")
    private String regCd;

    @Column(length = 50, insertable = false)
    @JsonProperty("modCd")
    private String modCd;
}
