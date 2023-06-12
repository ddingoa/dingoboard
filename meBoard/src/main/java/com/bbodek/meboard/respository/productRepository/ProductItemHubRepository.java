package com.bbodek.meboard.respository.productRepository;

import com.bbodek.meboard.domain.product.MEPRODUCTHUBITEM;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductItemHubRepository extends JpaRepository<MEPRODUCTHUBITEM, Long> {
    List<MEPRODUCTHUBITEM> findAllByActiveAndFabCode(boolean ative,String code);
    MEPRODUCTHUBITEM findByActiveAndId(boolean active,long id);
}
