package com.bbodek.meboard.domain.code;


import com.bbodek.meboard.domain.config.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Table(name = "meCode")
public class MECODE extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("codeId")
    @Column(name = "codeId",length = 100,nullable = false)
    private String codeId;

    @JsonProperty("codeName")
    @Column(name = "codeName",nullable = false)
    private String codeName;

    @JsonProperty("comment")
    @Column(name = "comment")
    private String comment;

    @JsonProperty("active")
    @Column(name = "active")
    private boolean active;

    @Column(length = 50, updatable = false)
    @JsonProperty("regCd")
    private String regCd;

    @Column(length = 50, insertable = false)
    @JsonProperty("modCd")
    private String modCd;

}
