package com.bbodek.meboard.historydomain.listener;

import com.bbodek.meboard.config.BeanUtil;
import com.bbodek.meboard.domain.mast.MEFABMAST;
import com.bbodek.meboard.domain.product.MEPRODUCTHUBITEM;
import com.bbodek.meboard.historydomain.mast.FabMastHistory;
import com.bbodek.meboard.historydomain.mast.ProductHubItemHistory;
import com.bbodek.meboard.historydomain.respository.FabMastHistoryRepository;
import com.bbodek.meboard.historydomain.respository.ProductHubItemHistoryRepository;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class ProductHubItemEntityListener {
    @PrePersist
    @PreUpdate
    public void prePersistAndPreUpdate(Object o) {
        ProductHubItemHistoryRepository hubItemHistoryRepository = BeanUtil.getBean(ProductHubItemHistoryRepository.class);

        MEPRODUCTHUBITEM meproducthubitem = (MEPRODUCTHUBITEM) o;

        ProductHubItemHistory productHubItemHistory = ProductHubItemHistory.builder()
                .goodsCd(meproducthubitem.getGoodsCd())
                .divisionCd(meproducthubitem.getDivisionCd())
                .goodsType(meproducthubitem.getGoodsType())
                .goodsNm(meproducthubitem.getGoodsNm())
                .material(meproducthubitem.getMaterial())
                .weight(meproducthubitem.getWeight())
                .color(meproducthubitem.getColor())
                .packingQuantity(meproducthubitem.getPackingQuantity())
                .plasticWrap(meproducthubitem.isPlasticWrap())
                .fabCode(meproducthubitem.getFabCode())
                .fabName(meproducthubitem.getFabName())
                .active(meproducthubitem.isActive())
                .regCd(meproducthubitem.getRegCd())
                .modCd(meproducthubitem.getModCd())
                .build();

        hubItemHistoryRepository.save(productHubItemHistory);
    }
}
