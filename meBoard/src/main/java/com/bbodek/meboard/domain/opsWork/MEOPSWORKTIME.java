package com.bbodek.meboard.domain.opsWork;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Setter
@Table(name = "meOpsWorkerTime")
public class MEOPSWORKTIME {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonProperty("opCode")
    @Column(length = 100, nullable = false, unique = true)
    private String opCode;

    @Column(length = 80)
    @JsonProperty("workTime20")
    private boolean workTime20;

    @Column(length = 80)
    @JsonProperty("workTime21")
    private boolean workTime21;

    @Column(length = 80)
    @JsonProperty("workTime22")
    private boolean workTime22;

    @Column(length = 80)
    @JsonProperty("workTime23")
    private boolean workTime23;

    @Column(length = 80)
    @JsonProperty("workTime00")
    private boolean workTime00;

    @Column(length = 80)
    @JsonProperty("workTime01")
    private boolean workTime01;

    @Column(length = 80)
    @JsonProperty("workTime02")
    private boolean workTime02;

    @Column(length = 80)
    @JsonProperty("workTime03")
    private boolean workTime03;

    @Column(length = 80)
    @JsonProperty("workTime04")
    private boolean workTime04;

    @Column(length = 80)
    @JsonProperty("workTime05")
    private boolean workTime05;

    @Column(length = 80)
    @JsonProperty("workTime06")
    private boolean workTime06;

    @Column(length = 80)
    @JsonProperty("workTime07")
    private boolean workTime07;

    @Column(length = 80)
    @JsonProperty("workTime08")
    private boolean workTime08;

    @Column(length = 80)
    @JsonProperty("workTime09")
    private boolean workTime09;

    @Column(length = 80)
    @JsonProperty("workTime10")
    private boolean workTime10;

    @Column(length = 80)
    @JsonProperty("workTime11")
    private boolean workTime11;

    @Column(length = 80)
    @JsonProperty("workTime12")
    private boolean workTime12;

    @Column(length = 80)
    @JsonProperty("workTime13")
    private boolean workTime13;

    @Column(length = 80)
    @JsonProperty("workTime14")
    private boolean workTime14;

    @Column(length = 80)
    @JsonProperty("workTime15")
    private boolean workTime15;

    @Column(length = 80)
    @JsonProperty("workTime16")
    private boolean workTime16;

    @Column(length = 80)
    @JsonProperty("workTime17")
    private boolean workTime17;

    @Column(length = 80)
    @JsonProperty("workTime18")
    private boolean workTime18;

    @Column(length = 80)
    @JsonProperty("workTime19")
    private boolean workTime19;
}
