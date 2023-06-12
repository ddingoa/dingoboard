package com.bbodek.meboard.historydomain.listener;

import com.bbodek.meboard.config.BeanUtil;
import com.bbodek.meboard.domain.mast.MEFABMAST;
import com.bbodek.meboard.historydomain.mast.FabMastHistory;
import com.bbodek.meboard.historydomain.respository.FabMastHistoryRepository;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class FabMastEntityListener {
    @PrePersist
    @PreUpdate
    public void prePersistAndPreUpdate(Object o) {
        FabMastHistoryRepository fabHistoryRepository = BeanUtil.getBean(FabMastHistoryRepository.class);

        MEFABMAST mefabsettingmast = (MEFABMAST) o;

        FabMastHistory fabmastHistory = FabMastHistory.builder()
                .fabcode(mefabsettingmast.getFabCode())
                .fabName(mefabsettingmast.getFabName())
                .fabManager(mefabsettingmast.getFabManager())
                .active(mefabsettingmast.isActive())
                .regCd(mefabsettingmast.getRegCd())
                .modCd(mefabsettingmast.getModCd())
                .build();

        fabHistoryRepository.save(fabmastHistory);
    }
}
