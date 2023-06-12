package com.bbodek.meboard.domain.orderWork;


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
@Table(name = "meOpSaOrder")
public class MEOPSAORDER extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 100, nullable = false, unique = true)
    @JsonProperty("opCode")
    private String opCode;

    @Column(length = 80)
    @JsonProperty("fabCode")
    private String fabCode;

    @Column(length = 100)
    @JsonProperty("fabName")
    private String fabName;

    @Column(length = 80)
    @JsonProperty("lineCode")
    private String lineCode;

    @Column(length = 100)
    @JsonProperty("lineName")
    private String lineName;

    @Column(length = 80)
    @JsonProperty("eqpCode")
    private String eqpCode;

    @Column(length = 80)
    @JsonProperty("btDepartment")
    private String btDepartment;

    @Column(length = 100, nullable = false)
    @JsonProperty("accountCode")
    private String accountCode;

    @Column(length = 100, nullable = false)
    @JsonProperty("accountName")
    private String accountName;

    @Builder.Default
    @JsonProperty("active")
    @Column(name = "active")
    private boolean active = true;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime stratTime;

    @Column(length = 80, nullable = false)
    @JsonProperty("timeCode")
    private String timeCode;

    @Column(length = 80, nullable = false)
    @JsonProperty("itemName")
    private String itemName;

    @Column(length = 80, nullable = false)
    @JsonProperty("orderItemCount")
    private int orderItemCount;

    @Column(length = 80, nullable = false)
    @JsonProperty("inItemCount")
    private int inItemCount;

    @Column(length = 80, nullable = false)
    @JsonProperty("totalItemCount")
    private int totalItemCount;

    @Column(length = 50, updatable = false)
    @JsonProperty("regCd")
    private String regCd;

    @Column(length = 50, insertable = false)
    @JsonProperty("modCd")
    private String modCd;

}
