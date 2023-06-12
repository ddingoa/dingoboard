package com.bbodek.meboard.respository.mastRepository;

import com.bbodek.meboard.domain.mast.MEFABSETTINGMAST;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface FabSettingMastRepository  extends JpaRepository<MEFABSETTINGMAST, Long> {
    List<MEFABSETTINGMAST> findAllByActiveAndFabCodeAndWorkStart(boolean activ, String fabcode, LocalDate workDay);
    List<MEFABSETTINGMAST> findAllByActiveAndFabCode(boolean activ, String fabcode);

    MEFABSETTINGMAST findByActiveAndFabCodeAndGoodsCdAndWorkStart(boolean active, String fabcode, String itemcode,LocalDate workDay);
    MEFABSETTINGMAST findByActiveAndFabCodeAndGoodsCd(boolean active, String fabcode, String itemcode);
}
