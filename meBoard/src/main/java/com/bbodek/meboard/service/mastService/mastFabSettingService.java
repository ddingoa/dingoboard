package com.bbodek.meboard.service.mastService;


import com.bbodek.meboard.domain.eqpMast.MEEQPMAST;
import com.bbodek.meboard.domain.mast.MEFABSETTINGMAST;
import com.bbodek.meboard.respository.mastRepository.FabSettingMastRepository;
import com.bbodek.meboard.util.commonMsg.MessageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Service
public class mastFabSettingService {

    @Autowired
    private FabSettingMastRepository fabSettingMastRepository;

    @Transactional
    public List<MEFABSETTINGMAST> GetFABSettingMastDayDataList(String fabcde, LocalDate workDate) throws SQLException {
        try {
            return fabSettingMastRepository.findAllByActiveAndFabCodeAndWorkStart(true,fabcde,workDate);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.EQP_NON_DATA_MSG);
        }
    }


    @Transactional
    public List<MEFABSETTINGMAST> GetFABSettingMastDataList(String fabcde) throws SQLException {
        try {
            return fabSettingMastRepository.findAllByActiveAndFabCode(true,fabcde);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.EQP_NON_DATA_MSG);
        }
    }
    @Transactional
    public MEFABSETTINGMAST GetFABSettingMastItemData(String fabcode,String itemcode,LocalDate workDay) throws SQLException {
        try {
            return fabSettingMastRepository.findByActiveAndFabCodeAndGoodsCdAndWorkStart(true,fabcode,itemcode,workDay);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.COM_SAVE_ERROR_MSG);
        }
    }

    @Transactional
    public MEFABSETTINGMAST GetFABSettingMastItemData(String fabcode,String itemcode) throws SQLException {
        try {
            return fabSettingMastRepository.findByActiveAndFabCodeAndGoodsCd(true,fabcode,itemcode);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.COM_SAVE_ERROR_MSG);
        }
    }

    @Transactional
    public void SetFABSettingMastDataList(List<MEFABSETTINGMAST> mefabsettingmastlist) throws SQLException {
        try {
            fabSettingMastRepository.saveAll(mefabsettingmastlist);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.COM_SAVE_ERROR_MSG);
        }
    }

    @Transactional
    public void DelFABSettingMastDataList(long id) throws SQLException {
        try {
            fabSettingMastRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.COM_SAVE_ERROR_MSG);
        }
    }

}
