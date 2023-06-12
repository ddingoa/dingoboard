package com.bbodek.meboard.dto.pkOrderData;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MEPKORDERDASHDTO {
    private String itemCode;
    private String itemName;
    private int itemCount;
    private int inputTime;
}
