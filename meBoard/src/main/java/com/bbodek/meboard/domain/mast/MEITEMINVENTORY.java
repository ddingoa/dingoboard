package com.bbodek.meboard.domain.mast;


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
@Table(name = "meItemInventoryMast")
public class MEITEMINVENTORY extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 80)
    @JsonProperty("business")
    private String business;

    @Column
    @JsonProperty("workStart")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate workStart;


    @Column(length = 80)
    @JsonProperty("itemCode")
    private String itemCode;

    @Column(length = 80)
    @JsonProperty("itemName")
    private String itemName;

    @Column(length = 80)
    @JsonProperty("itemCount")
    private int itemCount = 0;

    @Column(length = 80)
    @JsonProperty("itemPsCount")
    private int itemPsCount = 0;

    @Column(length = 80)
    @JsonProperty("itemDfPsCount")
    private int itemDfPsCount = 0;

    @Column
    @JsonProperty("note")
    private String note;

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
