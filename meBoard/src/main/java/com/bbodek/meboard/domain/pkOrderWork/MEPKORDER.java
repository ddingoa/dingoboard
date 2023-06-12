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
@Table(name = "mePkOrder")
public class MEPKORDER extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 100, nullable = false, unique = true)
    @JsonProperty("pkoCode")
    private String pkoCode;

    @Column(length = 80)
    @JsonProperty("fabCode")
    private String fabCode;

    @Column(length = 80)
    @JsonProperty("fabName")
    private String fabName;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime startTime;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
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
    @JsonProperty("itemCount")
    private int itemCount;

    @Column(length = 80, nullable = false)
    @JsonProperty("holidaysApply")
    private boolean holidaysApply;

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
