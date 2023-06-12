package com.bbodek.meboard.historydomain.respository;


import com.bbodek.meboard.historydomain.mast.FabMastHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FabMastHistoryRepository extends JpaRepository<FabMastHistory, String> {
}
