package com.bbodek.meboard.domain.orderWork;

import com.bbodek.meboard.domain.config.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Setter
@Table(name = "meDayPkWork")
public class MEDAYPKWORK extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 100, nullable = false)
    @JsonProperty("fabCode")
    private String fabCode;

    @Column
    @JsonProperty("workStart")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate workStart;


    @Column(length = 100, nullable = false)
    @JsonProperty("goodsCd")
    private String goodsCd;

    @Column(length = 100, nullable = false)
    @JsonProperty("goodsNm")
    private String goodsNm;

    @Column
    @Builder.Default
    @JsonProperty("totalOrderCount")
    private int totalOrderCount =0;

    @Column
    @Builder.Default
    @JsonProperty("totalOrderPsCount")
    private int totalOrderPsCount=0;

    @Column
    @Builder.Default
    @JsonProperty("totalPkWorkCount")
    private int totalPkWorkCount=0;

    @Column
    @Builder.Default
    @JsonProperty("totalPkWorkPsCount")
    private int totalPkWorkPsCount=0;

    @Column
    @Builder.Default
    @JsonProperty("nototalCount")
    private int nototalCount=0;

    @Column
    @Builder.Default
    @JsonProperty("totalcountvalue")
    private int totalcountvalue=0;

}
