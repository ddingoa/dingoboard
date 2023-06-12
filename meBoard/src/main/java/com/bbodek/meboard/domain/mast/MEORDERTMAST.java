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
@Table(name = "meOrderTMast")
public class MEORDERTMAST extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @JsonProperty("workStart")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate workStart;

    @Column(length = 80)
    @JsonProperty("orderKeyCode")
    private String orderKeyCode;

    @JsonProperty("accountCode")
    @Column(name = "accountCode")
    private String accountCode;

    @Column(length = 80)
    @JsonProperty("accountName")
    private String accountName;

    @Column(length = 80)
    @JsonProperty("itemCode")
    private String itemCode;

    @Column(length = 80)
    @JsonProperty("itemName")
    private String itemName;

    @Column(length = 80)
    @JsonProperty("itemType")
    private String itemType;

    @Column(length = 80)
    @JsonProperty("orderItemCount")
    private int orderItemCount;

    @Column(length = 80)
    @JsonProperty("insManger")
    private String insManger;

    @Column(length = 50, updatable = false)
    @JsonProperty("regCd")
    private String regCd;

    @Column(length = 50, insertable = false)
    @JsonProperty("modCd")
    private String modCd;


}
