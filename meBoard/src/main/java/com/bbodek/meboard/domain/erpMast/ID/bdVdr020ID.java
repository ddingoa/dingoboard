package com.bbodek.meboard.domain.erpMast.ID;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class bdVdr020ID implements Serializable {
    private String vendorCd;
    private String classCd;
}
