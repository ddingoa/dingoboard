package com.bbodek.meboard.historydomain.respository;

import com.bbodek.meboard.historydomain.mast.ProductHubItemHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductHubItemHistoryRepository extends JpaRepository<ProductHubItemHistory, String> {
        }
