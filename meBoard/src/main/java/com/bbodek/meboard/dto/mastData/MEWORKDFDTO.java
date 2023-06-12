package com.bbodek.meboard.dto.mastData;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MEWORKDFDTO {
    private String itemCode;
    private String itemName;
    private int totalWorkDfCount;
}
