package com.bbodek.meboard.respository.mastRepository;

import com.bbodek.meboard.domain.mast.MEITEMINVENTORY;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemInventoryRepository extends JpaRepository<MEITEMINVENTORY, Long> {

    List<MEITEMINVENTORY> findAllByActiveAndBusiness(boolean active, String bucode);

}
