package com.bbodek.meboard.respository.accountRepository;



import com.bbodek.meboard.domain.erpMast.bdItem010;
import com.bbodek.meboard.domain.erpMast.bdVdr010;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<bdVdr010, String> {
     bdVdr010 findByVendorCd(String accountCode);
     List<bdVdr010> findAllByDivisionCd(String buCode);

}
