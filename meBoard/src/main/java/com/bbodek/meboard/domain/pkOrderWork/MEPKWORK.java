package com.bbodek.meboard.domain.pkOrderWork;


import com.bbodek.meboard.domain.config.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Setter
@Table(name = "mePkWork")
public class MEPKWORK extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    @JsonProperty("pkoCode")
    private String pkoCode;

    @Column(length = 80)
    @JsonProperty("orderNo")
    private String orderNo;

    @Column(length = 80)
    @JsonProperty("fabCode")
    private String fabCode;

    @Column(length = 80)
    @JsonProperty("fabName")
    private String fabName;

    @Column
    @JsonProperty("workStart")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate workStart;

    @Column(nullable = false)
    @JsonProperty("inputTime")
    private int inputTime;

//    @Column(length = 20)
//    @JsonProperty("btDepartment")
//    private String btDepartment;
//
//    @Column(length = 80, nullable = false)
//    @JsonProperty("accountCode")
//    private String accountCode;
//
//    @Column(length = 80, nullable = false)
//    @JsonProperty("accountName")
//    private String accountName;

    @Column(length = 80, nullable = false)
    @JsonProperty("itemCode")
    private String itemCode;

    @Column(length = 80, nullable = false)
    @JsonProperty("itemName")
    private String itemName;

    @Column(length = 80, nullable = false)
    @JsonProperty("itemCount")
    private int itemCount;

    @Column(length = 80, nullable = false)
    @JsonProperty("itemPsCount")
    private int itemPsCount;

    @Column(length = 80, nullable = false)
    @JsonProperty("pkItemCount")
    private int pkItemCount;

//    @Column(length = 80, nullable = false)
//    @JsonProperty("holidaysApply")
//    private boolean holidaysApply;

    @Column(length = 80)
    @JsonProperty("pkingCheck")
    private boolean pkingCheck;

    @Builder.Default
    @JsonProperty("active")
    @Column(name = "active")
    private boolean active = true;

    @Column(length = 20, updatable = false)
    @JsonProperty("regCd")
    private String regCd;

    @Column(length = 20, insertable = false)
    @JsonProperty("modCd")
    private String modCd;
}
