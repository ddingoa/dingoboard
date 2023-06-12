package com.bbodek.meboard.historydomain.listener;

import com.bbodek.meboard.config.BeanUtil;
import com.bbodek.meboard.domain.eqpMast.MEEQPMAST;
import com.bbodek.meboard.domain.mast.MEFABMAST;
import com.bbodek.meboard.historydomain.mast.EqpMastHistory;
import com.bbodek.meboard.historydomain.mast.FabMastHistory;
import com.bbodek.meboard.historydomain.respository.EqpMastHistoryRepository;
import com.bbodek.meboard.historydomain.respository.FabMastHistoryRepository;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class EqpMastEntityListener {
    @PrePersist
    @PreUpdate
    public void prePersistAndPreUpdate(Object o) {
        EqpMastHistoryRepository eqpMastHistoryRepository = BeanUtil.getBean(EqpMastHistoryRepository.class);

        MEEQPMAST meeqpmast = (MEEQPMAST) o;

        EqpMastHistory eqpMastHistory = EqpMastHistory.builder()
                .eqpCode(meeqpmast.getEqpCode())
                .lineCode(meeqpmast.getLineCode())
                .eqpName(meeqpmast.getEqpName())
                .fabCode(meeqpmast.getFabCode())
                .fabName(meeqpmast.getFabName())
                .opStart(meeqpmast.getOpStart())
                .aqCost(meeqpmast.getAqCost())
                .aqForm(meeqpmast.getAqForm())
                .regularPm(meeqpmast.getRegularPm())
                .dyCheck(meeqpmast.getDyCheck())
                .wyCheck(meeqpmast.getWyCheck())
                .myCheck(meeqpmast.getMyCheck())
                .eqpManager(meeqpmast.getEqpManager())
                .active(meeqpmast.isActive())
                .regCd(meeqpmast.getRegCd())
                .modCd(meeqpmast.getModCd())
                .build();

        eqpMastHistoryRepository.save(eqpMastHistory);
    }
}
