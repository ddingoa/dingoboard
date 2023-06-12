package com.bbodek.meboard.historydomain.respository;

import com.bbodek.meboard.historydomain.mast.EqpMastHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EqpMastHistoryRepository extends JpaRepository<EqpMastHistory, String> {
}
