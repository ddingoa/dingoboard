package com.bbodek.meboard.service.productSeervice;

import com.bbodek.meboard.domain.erpMast.bdItem010;
import com.bbodek.meboard.respository.productRepository.ProductRepository;
import com.bbodek.meboard.util.commonMsg.MessageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public List<bdItem010> GetProductData() throws SQLException{
        try {
            return productRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.PRODUCT_NON_DATA_MSG);
        }
    }

    @Transactional
    public List<bdItem010> GetProductBuDataList(String bucode) throws SQLException{
        try {
            return productRepository.findByDivisionCd(bucode);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.PRODUCT_NON_DATA_MSG);
        }
    }


}
