package com.bbodek.meboard.historydomain.mast;

import com.bbodek.meboard.domain.config.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Table(name = "meFabSettingMastHistory")
public class FabMastSettingHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @JsonProperty("workStart")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate workStart;

    @Column(length = 100, nullable = false)
    @JsonProperty("fabCode")
    private String fabCode;

    @Column(length = 100, nullable = false)
    @JsonProperty("fabName")
    private String fabName;

    @JsonProperty("goodsCd")
    @Column(name = "goodsCd", length = 80, nullable = false)
    private String goodsCd;

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

    @Column(length = 20)
    @JsonProperty("color")
    private String color;

    @Column
    @JsonProperty("packingQuantity")
    private String packingQuantity;

    @Column(length = 80)
    @JsonProperty("fubItemCount")
    private int fubItemCount;

    @Column(length = 80)
    @JsonProperty("fubItemPsCount")
    private int fubItemPsCount;

    @Column
    @JsonProperty("plasticWrap")
    private boolean plasticWrap;

    @JsonProperty("active")
    @Column(name = "active")
    private boolean active = true;

    @Column(length = 80)
    @JsonProperty("workTime20")
    private int workTime20;

    @Column(length = 80)
    @JsonProperty("workTime21")
    private int workTime21;

    @Column(length = 80)
    @JsonProperty("workTime22")
    private int workTime22;

    @Column(length = 80)
    @JsonProperty("workTime23")
    private int workTime23;

    @Column(length = 80)
    @JsonProperty("workTime00")
    private int workTime00;

    @Column(length = 80)
    @JsonProperty("workTime01")
    private int workTime01;

    @Column(length = 80)
    @JsonProperty("workTime02")
    private int workTime02;

    @Column(length = 80)
    @JsonProperty("workTime03")
    private int workTime03;

    @Column(length = 80)
    @JsonProperty("workTime04")
    private int workTime04;

    @Column(length = 80)
    @JsonProperty("workTime05")
    private int workTime05;

    @Column(length = 80)
    @JsonProperty("workTime06")
    private int workTime06;

    @Column(length = 80)
    @JsonProperty("workTime07")
    private int workTime07;

    @Column(length = 80)
    @JsonProperty("workTime08")
    private int workTime08;

    @Column(length = 80)
    @JsonProperty("workTime09")
    private int workTime09;

    @Column(length = 80)
    @JsonProperty("workTime10")
    private int workTime10;

    @Column(length = 80)
    @JsonProperty("workTime11")
    private int workTime11;

    @Column(length = 80)
    @JsonProperty("workTime12")
    private int workTime12;

    @Column(length = 80)
    @JsonProperty("workTime13")
    private int workTime13;

    @Column(length = 80)
    @JsonProperty("workTime14")
    private int workTime14;

    @Column(length = 80)
    @JsonProperty("workTime15")
    private int workTime15;

    @Column(length = 80)
    @JsonProperty("workTime16")
    private int workTime16;

    @Column(length = 80)
    @JsonProperty("workTime17")
    private int workTime17;

    @Column(length = 80)
    @JsonProperty("workTime18")
    private int workTime18;

    @Column(length = 80)
    @JsonProperty("workTime19")
    private int workTime19;

    @Column(length = 50, updatable = false)
    @JsonProperty("regCd")
    private String regCd;

    @Column(length = 50, insertable = false)
    @JsonProperty("modCd")
    private String modCd;

    @Builder
    public FabMastSettingHistory(LocalDate workStart, String fabCode, String fabName, String goodsCd, String goodsType, String goodsNm, String material, String weight, String color, String packingQuantity, int fubItemCount, int fubItemPsCount, boolean plasticWrap, boolean active
            , int workTime20, int workTime21, int workTime22, int workTime23, int workTime00, int workTime01
            , int workTime02, int workTime03, int workTime04, int workTime05, int workTime06, int workTime07
            , int workTime08, int workTime09, int workTime10, int workTime11, int workTime12, int workTime13, int workTime14, int workTime15, int workTime16
            , int workTime17, int workTime18, int workTime19, String regCd, String modCd
    ) {
        this.workStart = workStart;
        this.fabCode = fabCode;
        this.fabName = fabName;
        this.goodsCd = goodsCd;
        this.goodsType = goodsType;
        this.goodsNm = goodsNm;
        this.material = material;
        this.weight = weight;
        this.color = color;
        this.packingQuantity = packingQuantity;
        this.fubItemCount = fubItemCount;
        this.fubItemPsCount = fubItemPsCount;
        this.plasticWrap = plasticWrap;
        this.active = active;
        this.workTime20 = workTime20;
        this.workTime21 = workTime21;
        this.workTime22 = workTime22;
        this.workTime23 = workTime23;
        this.workTime00 = workTime00;
        this.workTime01 = workTime01;
        this.workTime02 = workTime02;
        this.workTime03 = workTime03;
        this.workTime04 = workTime04;
        this.workTime05 = workTime05;
        this.workTime06 = workTime06;
        this.workTime07 = workTime07;
        this.workTime08 = workTime08;
        this.workTime09 = workTime09;
        this.workTime10 = workTime10;
        this.workTime11 = workTime11;
        this.workTime12 = workTime12;
        this.workTime13 = workTime13;
        this.workTime14 = workTime14;
        this.workTime15 = workTime15;
        this.workTime16 = workTime16;
        this.workTime17 = workTime17;
        this.workTime18 = workTime18;
        this.workTime19 = workTime19;
        this.regCd = regCd;
        this.modCd = modCd;
    }
}
