package com.bbodek.meboard.dto.mastData;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MEWORKORDERDTO {
    private String itemCode;
    private String itemName;
    private int totalWorkCount;
}
