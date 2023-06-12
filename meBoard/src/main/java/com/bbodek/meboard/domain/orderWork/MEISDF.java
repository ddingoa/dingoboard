package com.bbodek.meboard.domain.orderWork;


import com.bbodek.meboard.domain.config.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Blob;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Setter
@Table(name = "meIsDf")
public class MEISDF extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 100, nullable = false, unique = true)
    @JsonProperty("dfCode")
    private String dfCode;

    @Column(length = 100, nullable = false)
    @JsonProperty("dfName")
    private String dfName;

    @Column(length = 80)
    @JsonProperty("fabCode")
    private String fabCode;

    @Column(length = 100)
    @JsonProperty("fabName")
    private String fabName;

    @Column(length = 80, nullable = false)
    @JsonProperty("itemCode")
    private String itemCode;

    @Column(length = 100, nullable = false)
    @JsonProperty("itemName")
    private String itemName;

    @Column(nullable = false)
    @JsonProperty("dfCount")
    private int dfCount;

    @Column(length = 80, nullable = false)
    @JsonProperty("insManager")
    private String insManager;

    @Builder.Default
    @JsonProperty("active")
    @Column(name = "active")
    private boolean active = true;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate insTime;

    @Column(length = 80, nullable = false)
    @JsonProperty("actStatus")
    private String actStatus;


    @Column(length = 50, updatable = false)
    @JsonProperty("regCd")
    private String regCd;

    @Column(length = 50, insertable = false)
    @JsonProperty("modCd")
    private String modCd;

}
