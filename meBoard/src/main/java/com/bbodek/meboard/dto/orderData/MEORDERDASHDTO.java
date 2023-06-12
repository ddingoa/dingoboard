package com.bbodek.meboard.dto.orderData;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MEORDERDASHDTO {
    private String eqpCode;
    private String eqpName;
    private String itemCode;
    private String itemName;
    private int itemCount;
    private int inputTime;
}
