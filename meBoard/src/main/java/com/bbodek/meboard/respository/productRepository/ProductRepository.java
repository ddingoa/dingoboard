package com.bbodek.meboard.respository.productRepository;


import com.bbodek.meboard.domain.erpMast.bdItem010;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<bdItem010, String> {
    List<bdItem010> findByDivisionCd(String businessCode);
}
