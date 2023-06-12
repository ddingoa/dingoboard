package com.bbodek.meboard.service.productSeervice;


import com.bbodek.meboard.domain.product.MEPRODUCTHUBITEM;
import com.bbodek.meboard.respository.productRepository.ProductItemHubRepository;
import com.bbodek.meboard.util.commonMsg.MessageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;

@Service
public class ProductItemHubService {

    @Autowired
    private ProductItemHubRepository productItemHubRepository;

    @Transactional
    public List<MEPRODUCTHUBITEM> GetProductHubCodeData(String code) throws SQLException {
        try {
            return productItemHubRepository.findAllByActiveAndFabCode(true,code);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.PRODUCT_NON_DATA_MSG);
        }
    }

    @Transactional
    public MEPRODUCTHUBITEM GetProductHubId(long id) throws SQLException {
        try {
            return productItemHubRepository.findByActiveAndId(true,id);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.PRODUCT_NON_DATA_MSG);
        }
    }

    @Transactional
    public void SetProductHubCodeData(MEPRODUCTHUBITEM hubitem) throws SQLException {
        try {
            productItemHubRepository.save(hubitem);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.COM_SAVE_ERROR_MSG);
        }
    }
    @Transactional
    public void SetProductHubCodeDataList(List<MEPRODUCTHUBITEM> hubitemlist) throws SQLException {
        try {
            productItemHubRepository.saveAll(hubitemlist);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.COM_SAVE_ERROR_MSG);
        }
    }

    @Transactional
    public void DeletProductHubCodeDataList(long code) throws SQLException {
        try {
            productItemHubRepository.deleteById(code);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.COM_SAVE_ERROR_MSG);
        }
    }



}
