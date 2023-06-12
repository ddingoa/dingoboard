package com.bbodek.meboard.respository.otherRepository;

import com.bbodek.meboard.domain.wms.MEKIDSWMSSUBMODEL;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KidsWmsSubModelRepository extends JpaRepository<MEKIDSWMSSUBMODEL, Long> {

    List<MEKIDSWMSSUBMODEL> findAllByDeliveryCode(String code);
}
