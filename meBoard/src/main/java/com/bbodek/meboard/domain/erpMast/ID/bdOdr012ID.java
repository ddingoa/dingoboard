package com.bbodek.meboard.domain.erpMast.ID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class bdOdr012ID  implements Serializable {
    private String orderNo;
    private String ordernoSeq;
    private String classCd;
    private String memberId;
}
