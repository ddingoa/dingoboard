package com.bbodek.meboard.service.otherService;

import com.bbodek.meboard.domain.eqpMast.MEEQPMAST;
import com.bbodek.meboard.domain.orderWork.MEOPWORK;
import com.bbodek.meboard.domain.wms.MEKIDSWMSMODEL;
import com.bbodek.meboard.domain.wms.MEKIDSWMSSUBMODEL;
import com.bbodek.meboard.respository.otherRepository.KidsWmsModelRepository;
import com.bbodek.meboard.respository.otherRepository.KidsWmsSubModelRepository;
import com.bbodek.meboard.util.commonMsg.MessageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class KidsWmsModelSerivce {

    @Autowired
    private KidsWmsModelRepository kidsWmsModelRepository;

    @Autowired
    private KidsWmsSubModelRepository kidsWmsSubModelRepository;

    @Transactional
    public void SetKidsWmsModelDataAll(List<MEKIDSWMSMODEL> mekidswmsmodels) throws SQLException {
        kidsWmsModelRepository.saveAll(mekidswmsmodels);
    }
    @Transactional
    public void SetKidsWmsModelData(MEKIDSWMSMODEL mekidswmsmodels) throws SQLException {
        kidsWmsModelRepository.save(mekidswmsmodels);
    }

    @Transactional
    public List<MEKIDSWMSMODEL> GetKidsWmsModelDataAll() throws SQLException {
        return kidsWmsModelRepository.findAll();
    }



    @Transactional
    public Boolean UpadateKidsWmsModelData(Long id, MEKIDSWMSMODEL mekidswmsmodel) throws SQLException {
        try {
            Long code = kidsWmsModelRepository.getById(id).getId();
            if (code != null) {
                mekidswmsmodel.setId(code);
                kidsWmsModelRepository.save(mekidswmsmodel);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.COM_UP_ERROR_MSG);
        }
    }


    @Transactional
    public void DelKidsWmsModelData(MEKIDSWMSMODEL meeqpmast) throws SQLException {
        try {
            Optional<MEKIDSWMSMODEL> meeqpmastList = kidsWmsModelRepository.findById(meeqpmast.getId());
            if (meeqpmastList.isPresent()) {
                kidsWmsModelRepository.delete(meeqpmast);
            } else {
                throw new RuntimeException(MessageCode.NON_EQP_MSG);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(MessageCode.COM_DEL_ERROR_MSG);
        }
    }

    @Transactional
    public void SetKidsWmsSubModelDataAll(List<MEKIDSWMSSUBMODEL> mekidswmsmodels) throws SQLException {
        kidsWmsSubModelRepository.saveAll(mekidswmsmodels);
    }

    @Transactional
    public List<MEKIDSWMSSUBMODEL> GetKidsWmsSubModelDataAll() throws SQLException {
        return kidsWmsSubModelRepository.findAll();
    }

    @Transactional
    public List<MEKIDSWMSSUBMODEL> GetKidsWmsModelCodeData(String code) throws SQLException {
        return kidsWmsSubModelRepository.findAllByDeliveryCode(code);
    }


    @Transactional
    public void DelKidsWmsSubModelData(MEKIDSWMSSUBMODEL mekidswmssubmodel) throws SQLException {
        try {
            Optional<MEKIDSWMSSUBMODEL> meeqpmastList = kidsWmsSubModelRepository.findById(mekidswmssubmodel.getId());
            if (meeqpmastList.isPresent()) {
                kidsWmsSubModelRepository.delete(mekidswmssubmodel);
            } else {
                throw new RuntimeException(MessageCode.NON_EQP_MSG);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(MessageCode.COM_DEL_ERROR_MSG);
        }
    }


}
