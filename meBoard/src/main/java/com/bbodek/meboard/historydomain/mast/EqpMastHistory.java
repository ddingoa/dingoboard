package com.bbodek.meboard.historydomain.mast;

import com.bbodek.meboard.domain.config.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Table(name = "meEqpMastHistory")
public class EqpMastHistory extends BaseTimeEntity {

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

    @Builder
    public EqpMastHistory(String eqpCode, String lineCode, String eqpName, String fabCode, String fabName, LocalDate opStart
            , String aqCost, String aqForm, String regularPm, String dyCheck, String wyCheck, String myCheck, String eqpManager
            , boolean active, String regCd, String modCd) {
        this.eqpCode = eqpCode;
        this.lineCode = lineCode;
        this.eqpName = eqpName;
        this.fabCode = fabCode;
        this.fabName = fabName;
        this.opStart = opStart;
        this.aqCost = aqCost;
        this.aqForm = aqForm;
        this.regularPm = regularPm;
        this.dyCheck = dyCheck;
        this.wyCheck = wyCheck;
        this.myCheck = myCheck;
        this.eqpManager = eqpManager;
        this.active = active;
        this.regCd = regCd;
        this.modCd = modCd;
    }

}
