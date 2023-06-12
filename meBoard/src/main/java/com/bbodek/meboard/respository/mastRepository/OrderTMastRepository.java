package com.bbodek.meboard.respository.mastRepository;

import com.bbodek.meboard.domain.mast.MEORDERTMAST;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderTMastRepository  extends JpaRepository<MEORDERTMAST, Long> {

    MEORDERTMAST findByAccountCodeAndItemCode(String accountCode, String itemCode);
}
