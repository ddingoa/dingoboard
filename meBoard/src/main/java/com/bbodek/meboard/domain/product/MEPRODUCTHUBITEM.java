package com.bbodek.meboard.domain.product;


import com.bbodek.meboard.domain.config.BaseTimeEntity;
import com.bbodek.meboard.historydomain.listener.FabMastSettingEntityListener;
import com.bbodek.meboard.historydomain.listener.ProductHubItemEntityListener;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@EntityListeners(value = ProductHubItemEntityListener.class)
@Table(name = "meItemHub")
public class MEPRODUCTHUBITEM extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("goodsCd")
    @Column(name = "goodsCd", length = 80, nullable = false)
    private String goodsCd;

    @Column
    @JsonProperty("divisionCd")
    private String divisionCd;

    @Column
    @JsonProperty("goodsType")
    private String goodsType;

    @Column(length = 150, nullable = false)
    @JsonProperty("goodsNm")
    private String goodsNm;

    @Column
    @JsonProperty("material")
    private String material;

    @Column
    @JsonProperty("weight")
    private String weight;

    @Column
    @JsonProperty("color")
    private String color;

    @Column
    @JsonProperty("packingQuantity")
    private String packingQuantity;

    @Column
    @JsonProperty("plasticWrap")
    private boolean plasticWrap;

    @Column(length = 100, nullable = false)
    @JsonProperty("fabCode")
    private String fabCode;

    @Column(length = 100, nullable = false)
    @JsonProperty("fabName")
    private String fabName;

    @JsonProperty("active")
    @Column(name = "active")
    private boolean active = true;

    @Column(length = 50, updatable = false)
    @JsonProperty("regCd")
    private String regCd;

    @Column(length = 50, insertable = false)
    @JsonProperty("modCd")
    private String modCd;
}
