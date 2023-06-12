package com.bbodek.meboard.dto.chartData;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MECHARTDATASPEC {
    private String dateTime;
    private int SpecCount;
}
