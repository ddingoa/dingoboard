package com.bbodek.meboard.domain.mast;


import com.bbodek.meboard.domain.config.BaseTimeEntity;
import com.bbodek.meboard.historydomain.listener.FabMastEntityListener;
import com.bbodek.meboard.historydomain.listener.FabMastSettingEntityListener;
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
@EntityListeners(value = FabMastSettingEntityListener.class)
@Table(name = "meFabSettingMast")
public class MEFABSETTINGMAST extends BaseTimeEntity {

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

}
