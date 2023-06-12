package com.bbodek.meboard.historydomain.listener;

import com.bbodek.meboard.config.BeanUtil;
import com.bbodek.meboard.domain.mast.MEFABMAST;
import com.bbodek.meboard.domain.mast.MEFABSETTINGMAST;
import com.bbodek.meboard.historydomain.mast.FabMastHistory;
import com.bbodek.meboard.historydomain.mast.FabMastSettingHistory;
import com.bbodek.meboard.historydomain.respository.FabMastHistoryRepository;
import com.bbodek.meboard.historydomain.respository.FabMastSettingHistoryRepository;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class FabMastSettingEntityListener {
    @PrePersist
    @PreUpdate
    public void prePersistAndPreUpdate(Object o) {
        FabMastSettingHistoryRepository fabMastSettingHistoryRepository = BeanUtil.getBean(FabMastSettingHistoryRepository.class);

        MEFABSETTINGMAST mefabsettingmast = (MEFABSETTINGMAST) o;

        FabMastSettingHistory fabMastSettingHistory = FabMastSettingHistory.builder()
                .workStart(mefabsettingmast.getWorkStart())
                .fabCode(mefabsettingmast.getFabCode())
                .fabName(mefabsettingmast.getFabName())
                .goodsCd(mefabsettingmast.getGoodsCd())
                .goodsType(mefabsettingmast.getGoodsType())
                .goodsNm(mefabsettingmast.getGoodsNm())
                .material(mefabsettingmast.getMaterial())
                .weight(mefabsettingmast.getWeight())
                .color(mefabsettingmast.getColor())
                .packingQuantity(mefabsettingmast.getPackingQuantity())
                .fubItemCount(mefabsettingmast.getFubItemCount())
                .fubItemPsCount(mefabsettingmast.getFubItemPsCount())
                .plasticWrap(mefabsettingmast.isPlasticWrap())
                .active(mefabsettingmast.isActive())
                .workTime20(mefabsettingmast.getWorkTime20())
                .workTime21(mefabsettingmast.getWorkTime21())
                .workTime22(mefabsettingmast.getWorkTime22())
                .workTime23(mefabsettingmast.getWorkTime23())
                .workTime00(mefabsettingmast.getWorkTime00())
                .workTime01(mefabsettingmast.getWorkTime01())
                .workTime02(mefabsettingmast.getWorkTime02())
                .workTime03(mefabsettingmast.getWorkTime03())
                .workTime04(mefabsettingmast.getWorkTime04())
                .workTime05(mefabsettingmast.getWorkTime05())
                .workTime06(mefabsettingmast.getWorkTime06())
                .workTime07(mefabsettingmast.getWorkTime07())
                .workTime08(mefabsettingmast.getWorkTime08())
                .workTime09(mefabsettingmast.getWorkTime09())
                .workTime10(mefabsettingmast.getWorkTime10())
                .workTime11(mefabsettingmast.getWorkTime11())
                .workTime12(mefabsettingmast.getWorkTime12())
                .workTime13(mefabsettingmast.getWorkTime13())
                .workTime14(mefabsettingmast.getWorkTime14())
                .workTime15(mefabsettingmast.getWorkTime15())
                .workTime16(mefabsettingmast.getWorkTime16())
                .workTime17(mefabsettingmast.getWorkTime17())
                .workTime18(mefabsettingmast.getWorkTime18())
                .workTime19(mefabsettingmast.getWorkTime19())
                .regCd(mefabsettingmast.getRegCd())
                .modCd(mefabsettingmast.getModCd())
                .build();

        fabMastSettingHistoryRepository.save(fabMastSettingHistory);
    }
}
