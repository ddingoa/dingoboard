package com.bbodek.meboard.dto.mastData;

import com.bbodek.meboard.dto.util.EnterCobDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MEENTERREDTO {
    private List<MEENTERDTO> meenteritemList;
    private String username;
    private EnterCobDto enterCobDto;
}
