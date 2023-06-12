package com.bbodek.meboard.domain.erpMast.ID;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class bdVdr011ID implements Serializable {
    private String vendorCd;
    private String deliveryNo;
}
