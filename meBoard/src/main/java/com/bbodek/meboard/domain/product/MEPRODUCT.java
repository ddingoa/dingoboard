//package com.bbodek.meboard.domain.product;
//
//
//import com.bbodek.meboard.domain.config.BaseTimeEntity;
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import lombok.*;
//import org.hibernate.annotations.ColumnDefault;
//import org.springframework.data.annotation.CreatedDate;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Setter
//@Table(name = "meItemMast")
//public class MEPRODUCT extends BaseTimeEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @JsonProperty("itemCode")
//    @Column(name = "itemCode", length = 80, nullable = false, unique = true)
//    private String itemCode;
//
//    @Column
//    @JsonProperty("business")
//    private String business;
//
//    @Column
//    @JsonProperty("itemType")
//    private String itemType;
//
//    @Column(length = 150, nullable = false)
//    @JsonProperty("itemName")
//    private String itemName;
//
//    @Column
//    @JsonProperty("texture")
//    private String texture;
//
//    @Column
//    @JsonProperty("weight")
//    private String weight;
//
//    @Column
//    @JsonProperty("packingQuantity")
//    private String packingQuantity;
//
//    @Column
//    @JsonProperty("plasticWrap")
//    private boolean plasticWrap;
//
//    @Column
//    @JsonProperty("assetType")
//    private String assetType;
//
//    @Column
//    @JsonProperty("purchasePrice")
//    private int purchasePrice;
//
//    @Column
//    @JsonProperty("purchase")
//    private String purchase;
//
//    @Column
//    @JsonProperty("purchaseManager")
//    private String purchaseManager;
//
//    @Column
//    @JsonProperty("purchaseManagerNumber")
//    private String purchaseManagerNumber;
//
//
//    @Column(length = 200)
//    @JsonProperty("note")
//    private String note;
//
//    @JsonProperty("active")
//    @Column(name = "active")
//    private boolean active = true;
//
//    @Column(length = 50, updatable = false)
//    @JsonProperty("regCd")
//    private String regCd;
//
//    @Column(length = 50, insertable = false)
//    @JsonProperty("modCd")
//    private String modCd;
//}
