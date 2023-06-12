package com.bbodek.meboard.domain.label;

import com.bbodek.meboard.domain.config.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Setter
@Table(name = "bbodekLabel")
public class bbodeklabel extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonProperty("qrCode")
    @Column(length = 100, nullable = false, unique = true)
    private String qrCode;

    @Column(length = 100)
    @JsonProperty("labeName")
    private String labeName;

    @Column(length = 100)
    @JsonProperty("labePW")
    private String labePW;

    @Column
    @JsonProperty("labeNote")
    private String labeNote;

    @Column
    @JsonProperty("checkValue")
    private boolean checkValue;

    @Column
    @JsonProperty("returnTime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime returnTime;

    @Column
    @JsonProperty("outTime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime outTime;
}
