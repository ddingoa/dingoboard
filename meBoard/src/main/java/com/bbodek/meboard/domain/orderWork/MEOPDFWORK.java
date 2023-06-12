package com.bbodek.meboard.domain.orderWork;

import com.bbodek.meboard.domain.config.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Setter
@Table(name = "meOpDfWork")
public class MEOPDFWORK extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 100, nullable = false)
    @JsonProperty("opCode")
    private String opCode;

    @Column(length = 80, nullable = false)
    @JsonProperty("fabCode")
    private String fabCode;

    @Column(length = 100, nullable = false)
    @JsonProperty("fabName")
    private String fabName;

    @Column(length = 80, nullable = false)
    @JsonProperty("lineCode")
    private String lineCode;

    @Column(length = 100, nullable = false)
    @JsonProperty("lineName")
    private String lineName;

    @JsonProperty("eqpCode")
    @Column(length = 100, nullable = false)
    private String eqpCode;

    @Column(length = 100, nullable = false)
    @JsonProperty("eqpName")
    private String eqpName;

    @Column(length = 100, nullable = false)
    @JsonProperty("workCode")
    private String workCode;

    @Column(length = 80, nullable = false)
    @JsonProperty("workName")
    private String workName;

    @Column(length = 80, nullable = false)
    @JsonProperty("itemCode")
    private String itemCode;

    @Column(length = 80, nullable = false)
    @JsonProperty("itemName")
    private String itemName;

    @Column(nullable = false)
    @JsonProperty("inputTime")
    private int inputTime;

    @Column
    @JsonProperty("workStart")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate workStart;

    @Builder.Default
    @JsonProperty("active")
    @Column(name = "active")
    private boolean active = true;

    @Column(length = 80, nullable = false)
    @JsonProperty("workItemCount")
    private int workItemCount;

    @Column(length = 50, updatable = false)
    @JsonProperty("regCd")
    private String regCd;

    @Column(length = 50, insertable = false)
    @JsonProperty("modCd")
    private String modCd;
}
