package com.bbodek.meboard.respository.pkOrderRepository;


import com.bbodek.meboard.domain.pkOrderWork.MEPKWORK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface pkOrderRepository extends JpaRepository<MEPKWORK, Long> {
}
