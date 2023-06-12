package com.bbodek.meboard.controller.product;

import com.bbodek.meboard.domain.erpMast.bdItem010;
import com.bbodek.meboard.service.productSeervice.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/get/productdata")
    private Map AccountGetData() {
        HashMap resultMap = new HashMap<String, String>();
        try {
            List<bdItem010> meproductList = productService.GetProductData();
            resultMap.put("ResultValue", "조회에 성공하였습니다.");
            resultMap.put("data", meproductList);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
        }
        return resultMap;
    }


}
