package com.bbodek.meboard.dto.orderData;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MEDFORDERDASHDTO {
    private String workCode;
    private String workName;
    private String itemCode;
    private String itemName;
    private int itemCount;
    private int inputTime;
}
