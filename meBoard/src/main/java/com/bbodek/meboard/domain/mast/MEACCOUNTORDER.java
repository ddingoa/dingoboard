package com.bbodek.meboard.domain.mast;


import com.bbodek.meboard.domain.config.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Table(name = "meAccountOrderMast")
public class MEACCOUNTORDER  extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("accountCode")
    @Column(name = "accountCode")
    private String accountCode;

    @Column(length = 80)
    @JsonProperty("accountName")
    private String accountName;

    @Column(length = 80)
    @JsonProperty("bussiness")
    private String bussiness;

    @Column(length = 80)
    @JsonProperty("detailClass")
    private String detailClass;

    @Column(length = 80)
    @JsonProperty("itemCode")
    private String itemCode;

    @Column(length = 80)
    @JsonProperty("itemName")
    private String itemName;

    @Column(length = 80)
    @JsonProperty("qcCount")
    private String qcCount;

    @Column(length = 80)
    @JsonProperty("note")
    private String note;

    @JsonProperty("active")
    @Column(name = "active")
    private boolean active = true;

}
