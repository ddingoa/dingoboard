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
@Table(name = "meEnterItem")
public class MEENTERITEM extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 100, nullable = false)
    @JsonProperty("enterCode")
    private String enterCode;

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

    @Column(length = 100, nullable = false)
    @JsonProperty("accountCode")
    private String accountCode;

    @Column(length = 100, nullable = false)
    @JsonProperty("accountName")
    private String accountName;

    @Column(length = 100, nullable = false)
    @JsonProperty("itemCode")
    private String itemCode;

    @Column(length = 100, nullable = false)
    @JsonProperty("itemName")
    private String itemName;

    @Column(length = 100, nullable = false)
    @JsonProperty("enterItem")
    private int enterItem;

    @Column(length = 100, nullable = false)
    @JsonProperty("dfCount")
    private int dfCount;

    @Lob
    @Column
    @JsonProperty("dfImg")
    private Blob dfImg;

    @Column(length = 100, nullable = false)
    @JsonProperty("insManager")
    private String insManager;

    @Builder.Default
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
