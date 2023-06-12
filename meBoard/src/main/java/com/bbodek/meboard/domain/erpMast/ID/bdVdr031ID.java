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
public class bdVdr031ID implements Serializable {
    private String memberId;
    private String vendorCd;
    private String classCd;
    private String goodsCd;
}
