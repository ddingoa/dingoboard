package com.bbodek.meboard.dto.opsworker;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MEOPSWORKERDTO {
    private String fabCode;
    private String fabName;
    private String lineCode;
    private String lineName;
    private String workCode;
    private String workName;
    private String opName;
    private String opCode;
}
