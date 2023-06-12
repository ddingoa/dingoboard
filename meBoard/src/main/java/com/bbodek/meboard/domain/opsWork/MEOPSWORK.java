package com.bbodek.meboard.domain.opsWork;

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
@Table(name = "meOpsWork")
public class MEOPSWORK extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 100, nullable = false, unique = true)
    @JsonProperty("workCode")
    private String workCode;

    @Column(length = 80, nullable = false)
    @JsonProperty("workName")
    private String workName;

    @JsonProperty("eqpCode")
    @Column(length = 100, nullable = false)
    private String eqpCode;

    @Column(length = 100, nullable = false)
    @JsonProperty("eqpName")
    private String eqpName;

    @Column(length = 80, nullable = false)
    @JsonProperty("fabCode")
    private String fabCode;

    @Column(length = 100)
    @JsonProperty("fabName")
    private String fabName;

    @Column(length = 80, nullable = false)
    @JsonProperty("lineCode")
    private String lineCode;

    @Column(length = 50, nullable = false)
    @JsonProperty("lineManager")
    private String lineManager;

    @Column(length = 100)
    @JsonProperty("rePerson")
    private String rePerson;

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
