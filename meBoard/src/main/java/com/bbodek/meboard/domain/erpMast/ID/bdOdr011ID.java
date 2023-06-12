package com.bbodek.meboard.domain.erpMast.ID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class bdOdr011ID  implements Serializable {
    private String orderNo;
    private String ordernoSeq;
    private String deliveryNo;
}
