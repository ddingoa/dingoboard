package com.bbodek.meboard.domain.eqpMast;


import com.bbodek.meboard.domain.config.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Setter
@Table(name = "meLineIfMast")
public class MELINEMAST extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonProperty("lineCode")
    @Column(length = 100, nullable = false, unique = true)
    private String lineCode;

    @Column(length = 100, nullable = false)
    @JsonProperty("lineName")
    private String lineName;

    @Column(length = 80, nullable = false)
    @JsonProperty("fabCode")
    private String fabCode;


    @Column(length = 80, nullable = false)
    @JsonProperty("fabName")
    private String fabName;

    @Column(length = 100)
    @JsonProperty("rePerson")
    private String rePerson;

    @Column(length = 50, nullable = false)
    @JsonProperty("lineManager")
    private String lineManager;

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
