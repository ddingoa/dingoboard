package com.bbodek.meboard.domain.orderWork;

import com.bbodek.meboard.domain.config.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Setter
@Table(name = "meNonItem")
public class MENONITEM extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 100, nullable = false, unique = true)
    @JsonProperty("nonItemCode")
    private String nonItemCode;

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

    @Column(nullable = false)
    @JsonProperty("enterItem")
    private int enterItem;

    @Column
    @JsonProperty("totalOrderItem")
    private int totalOrderItem;

    @Column
    @JsonProperty("totalNonItem")
    private int totalNonItem;

    @Column(length = 50, updatable = false)
    @JsonProperty("regCd")
    private String regCd;

    @Column(length = 50, insertable = false)
    @JsonProperty("modCd")
    private String modCd;
    
}
