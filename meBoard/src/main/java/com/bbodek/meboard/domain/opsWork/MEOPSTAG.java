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
@Table(name = "meOpsWorktag")
public class MEOPSTAG extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonProperty("opCode")
    @Column(length = 100, nullable = false, unique = true)
    private String opCode;

    @Column(length = 80, nullable = false)
    @JsonProperty("opName")
    private String opName;

    @Column(length = 80, nullable = false)
    @JsonProperty("workCode")
    private String workCode;

    @Column(length = 80, nullable = false)
    @JsonProperty("lineCode")
    private String lineCode;

    @Column(length = 80, nullable = false)
    @JsonProperty("fabCode")
    private String fabCode;

    @Column(length = 50, nullable = false)
    @JsonProperty("lineManager")
    private String lineManager;

    @Column
    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime tagTime;

    @Column(nullable = false)
    @JsonProperty("tagFlag")
    private Boolean tagFlag;

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
