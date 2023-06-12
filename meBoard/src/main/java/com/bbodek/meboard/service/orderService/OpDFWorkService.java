package com.bbodek.meboard.service.orderService;

import com.bbodek.meboard.domain.orderWork.MEOPDFWORK;
import com.bbodek.meboard.respository.orderRepository.OpDFWorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class OpDFWorkService {

    @Autowired
    private OpDFWorkRepository opDFWorkRepository;

    @Transactional
    public void SetDFWorkDataAll(List<MEOPDFWORK> meopdfworkList) throws SQLException {
        opDFWorkRepository.saveAll(meopdfworkList);
    }


    @Transactional
    public MEOPDFWORK GetDFWorkDataAll(String fabcode, String lineCode,String eqpCode, String workCode, String itemCode) throws SQLException {
        return  opDFWorkRepository.findByActiveAndFabCodeAndLineCodeAndEqpCodeAndWorkCodeAndItemCode(true,fabcode,lineCode,eqpCode,workCode ,itemCode);
    }


}
