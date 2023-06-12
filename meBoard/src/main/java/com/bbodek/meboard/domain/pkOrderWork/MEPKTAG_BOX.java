package com.bbodek.meboard.domain.pkOrderWork;


import com.bbodek.meboard.domain.config.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Setter
@Table(name = "mePkTagBox")
public class MEPKTAG_BOX extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    @JsonProperty("pkoCode")
    private String pkoCode;

    @Column(length = 80)
    @JsonProperty("fabCode")
    private String fabCode;

    @Column(length = 80)
    @JsonProperty("fabName")
    private String fabName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @JsonProperty("startTime")
    private LocalDateTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @JsonProperty("dtTime")
    private LocalDateTime dtTime;

    @Column(length = 80)
    @JsonProperty("btDepartment")
    private String btDepartment;

    @Column(length = 80, nullable = false)
    @JsonProperty("accountCode")
    private String accountCode;

    @Column(length = 80, nullable = false)
    @JsonProperty("accountName")
    private String accountName;

    @Column(length = 80, nullable = false)
    @JsonProperty("itemCode")
    private String itemCode;

    @Column(length = 80, nullable = false)
    @JsonProperty("itemName")
    private String itemName;

    @Column(length = 80, nullable = false)
    @JsonProperty("pkBoxTag")
    private String pkBoxTag;

    @Column(length = 80, nullable = false)
    @JsonProperty("pkTagPr")
    private boolean pkTagPr;

    @Column(length = 80, nullable = false)
    @JsonProperty("paTagRpr")
    private boolean paTagRpr;

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
