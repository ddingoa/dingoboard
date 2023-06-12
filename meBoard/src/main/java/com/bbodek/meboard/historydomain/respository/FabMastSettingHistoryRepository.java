package com.bbodek.meboard.historydomain.respository;

import com.bbodek.meboard.historydomain.mast.FabMastHistory;
import com.bbodek.meboard.historydomain.mast.FabMastSettingHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FabMastSettingHistoryRepository extends JpaRepository<FabMastSettingHistory, String> {
}
