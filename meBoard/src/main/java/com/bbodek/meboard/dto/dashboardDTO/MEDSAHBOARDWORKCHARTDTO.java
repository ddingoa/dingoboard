package com.bbodek.meboard.dto.dashboardDTO;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MEDSAHBOARDWORKCHARTDTO {
    private LocalDate workStart;
    private String itemName;
    private int inputTime;
    private int workItemCount;
}
