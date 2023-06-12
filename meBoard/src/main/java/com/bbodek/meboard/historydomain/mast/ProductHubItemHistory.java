package com.bbodek.meboard.historydomain.mast;

import com.bbodek.meboard.domain.config.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Table(name = "meItemHubHistory")
public class ProductHubItemHistory extends BaseTimeEntity {

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

    @Builder
    public ProductHubItemHistory (String goodsCd, String divisionCd, String goodsType,String goodsNm, String material, String weight
    ,String color, String packingQuantity, boolean plasticWrap, String fabCode, String fabName, boolean active, String regCd, String modCd) {
        this.goodsCd = goodsCd;
        this.divisionCd = divisionCd;
        this.goodsType = goodsType;
        this.goodsNm =goodsNm;
        this.material = material;
        this.weight = weight;
        this.color = color;
        this.packingQuantity = packingQuantity;
        this.plasticWrap = plasticWrap;
        this.fabCode = fabCode;
        this.fabName = fabName;
        this.active = active;
        this.regCd = regCd;
        this.modCd = modCd;
    }
}
