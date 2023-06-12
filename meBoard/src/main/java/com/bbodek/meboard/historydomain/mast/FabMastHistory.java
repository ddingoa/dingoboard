package com.bbodek.meboard.historydomain.mast;

import com.bbodek.meboard.domain.config.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Table(name = "meFabMastHistory")
public class FabMastHistory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false, updatable = true)
    @JsonProperty("fabCode")
    private String fabCode;

    @Column(length = 100, nullable = false, updatable = true)
    @JsonProperty("fabName")
    private String fabName;

    @Column(length = 100)
    @JsonProperty("fabManager")
    private String fabManager;

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
    public FabMastHistory (String fabcode, String fabName, String fabManager, boolean active, String regCd, String modCd) {
        this.fabCode = fabcode;
        this.fabName = fabName;
        this.fabManager = fabManager;
        this.active =active;
        this.regCd = regCd;
        this.modCd = modCd;
    }
}
