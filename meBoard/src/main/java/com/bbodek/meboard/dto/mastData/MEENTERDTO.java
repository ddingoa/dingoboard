package com.bbodek.meboard.dto.mastData;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MEENTERDTO {
    private String enterCode;
    private String fabCode;
    private String fabName;
    private String accountCode;
    private String accountName;
    private String itemType;
    private String itemCode;
    private String itemName;
    private int enterItem;
    private int dfCount;
    private String insManger;
}
