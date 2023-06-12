package com.bbodek.meboard.domain.erpMast;


import com.bbodek.meboard.domain.erpMast.ID.bdItem012ID;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@IdClass(bdItem012ID.class)
@Table(name = "bdItm012")
public class bdItem012 implements Serializable {
    //박스 구성
    @Id
    @JsonProperty("boxItmCd")
    @Column(length = 10, nullable = false)
    private String boxItmCd;

    @Id
    @JsonProperty("goodsCd")
    @Column(length = 10, nullable = false)
    private String goodsCd;

    @Column(length = 3, nullable = false)
    @JsonProperty("boxInQty")
    private String boxInQty;

    @Column(length = 200)
    @JsonProperty("remark")
    private String remark;

    @Column(length = 1, nullable = false)
    @JsonProperty("deleteYn")
    private String deleteYn;

    @Column(length = 20, nullable = false)
    @JsonProperty("regUserId")
    private String regUserId;

    @Column(length = 20, nullable = false)
    @JsonProperty("regDate")
    private String regDate;

    @Column(length = 20, nullable = false)
    @JsonProperty("modiUserId")
    private String modiUserId;

    @Column(length = 20, nullable = false)
    @JsonProperty("modiDate")
    private String modiDate;

}
