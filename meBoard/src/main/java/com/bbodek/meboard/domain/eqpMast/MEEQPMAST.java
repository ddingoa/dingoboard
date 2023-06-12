package com.bbodek.meboard.domain.eqpMast;


import com.bbodek.meboard.domain.config.BaseTimeEntity;
import com.bbodek.meboard.historydomain.listener.EqpMastEntityListener;
import com.bbodek.meboard.historydomain.listener.FabMastEntityListener;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.ManyToAny;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@EntityListeners(value = EqpMastEntityListener.class)
@Table(name = "meEqpIfMast")
public class MEEQPMAST extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonProperty("eqpCode")
    @Column(length = 100, nullable = false, unique = true)
    private String eqpCode;

    @Column(nullable = false)
    @JsonProperty("lineCode")
    private String lineCode;

    @Column(length = 100, nullable = false, unique = true)
    @JsonProperty("eqpName")
    private String eqpName;

    @Column(length = 80, nullable = false)
    @JsonProperty("fabCode")
    private String fabCode;

    @Column(length = 100)
    @JsonProperty("fabName")
    private String fabName;

    @Column
    @JsonProperty("opStart")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate opStart;

    @Column(length = 40)
    @JsonProperty("aqCost")
    private String aqCost;

    @Column(length = 40)
    @JsonProperty("aqForm")
    private String aqForm;

    @Column(length = 40)
    @JsonProperty("regularPm")
    private String regularPm;

    @Column(length = 40)
    @JsonProperty("dyCheck")
    private String dyCheck;

    @Column(length = 40)
    @JsonProperty("wyCheck")
    private String wyCheck;

    @Column(length = 40)
    @JsonProperty("myCheck")
    private String myCheck;

    @Column(length = 50)
    @JsonProperty("eqpManager")
    private String eqpManager;

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
