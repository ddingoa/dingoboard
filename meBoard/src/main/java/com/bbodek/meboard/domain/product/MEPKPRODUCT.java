package com.bbodek.meboard.domain.product;


import com.bbodek.meboard.historydomain.listener.ProductHubItemEntityListener;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Setter
@Table(name = "mePkItem")
public class MEPKPRODUCT {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("goodsCd")
    @Column(name = "goodsCd", length = 80, nullable = false)
    private String goodsCd;

    @Column
    @JsonProperty("divisionCd")
    private String divisionCd;

    @Column
    @JsonProperty("goodsType")
    private String goodsType;

    @Column(length = 150, nullable = false)
    @JsonProperty("goodsNm")
    private String goodsNm;

    @Column
    @Builder.Default
    @JsonProperty("packQuantity")
    private int packQuantity =0;

    @Column
    @JsonProperty("plasticWrap")
    private boolean plasticWrap;
}
