package com.bbodek.meboard.service.mastService;


import com.bbodek.meboard.domain.eqpMast.MEEQPMAST;
import com.bbodek.meboard.domain.eqpMast.MELINEMAST;
import com.bbodek.meboard.domain.mast.MEFABMAST;
import com.bbodek.meboard.respository.mastRepository.EqpMastRepository;
import com.bbodek.meboard.respository.mastRepository.FabMastRepository;
import com.bbodek.meboard.respository.mastRepository.LineMastRepository;
import com.bbodek.meboard.util.commonMsg.MessageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class mastService {

    @Autowired
    private EqpMastRepository eqpMastRepository;

    @Autowired
    private LineMastRepository lineMastRepository;

    @Autowired
    private FabMastRepository fabMastRepository;

    @Transactional
    public List<MEEQPMAST> GetEQPMastData() throws SQLException {
        try {
            return eqpMastRepository.findAllByActive(true);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.EQP_NON_DATA_MSG);
        }
    }


    @Transactional
    public List<MEEQPMAST> GetEQPMastLineData(String linecode) throws SQLException {
        try {
            return eqpMastRepository.findAllByLineCodeAndActive(linecode, true);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.EQP_NON_DATA_MSG);
        }
    }

    @Transactional
    public List<MEEQPMAST> GetEQPMastFabData(String fabcode) throws SQLException {
        try {
            return eqpMastRepository.findAllByFabCodeAndActive(fabcode, true);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.EQP_NON_DATA_MSG);
        }
    }

    @Transactional
    public void SetEQPMastData(MEEQPMAST eqpData) throws SQLException {
        try {
            eqpMastRepository.save(eqpData);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.COM_SAVE_ERROR_MSG);
        }
    }

    @Transactional
    public Boolean UpadateEQPMastData(Long id, MEEQPMAST eqpData) throws SQLException {
        try {
            Long code = eqpMastRepository.getById(id).getId();
            if (code != null) {
                eqpData.setId(code);
                eqpMastRepository.save(eqpData);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.COM_UP_ERROR_MSG);
        }
    }

    @Transactional
    public void SetEQPMastListData(List<MEEQPMAST> eqpListData) throws SQLException {
        try {
            eqpMastRepository.saveAll(eqpListData);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.COM_SAVE_ERROR_MSG);
        }
    }

    @Transactional
    public void DelEQPMastData(MEEQPMAST meeqpmast) throws SQLException {
        try {
            Optional<MEEQPMAST> meeqpmastList = eqpMastRepository.findById(meeqpmast.getId());
            if(meeqpmastList.isPresent()){
                meeqpmast.setActive(false);
                eqpMastRepository.save(meeqpmast);
            }else {
                throw new RuntimeException(MessageCode.NON_EQP_MSG);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(MessageCode.COM_DEL_ERROR_MSG);
        }

    }


    @Transactional
    public List<MELINEMAST> GetLineMastData() throws SQLException {
        try {
            return lineMastRepository.findAllByActive(true);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.LINE_NON_DATA_MSG);
        }
    }

    @Transactional
    public List<MELINEMAST> GetLineMastIDData(String id) throws SQLException {
        try {
            return lineMastRepository.findByFabCodeAndActive(id,true);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.LINE_NON_DATA_MSG);
        }
    }

    @Transactional
    public void SetLineMastData(MELINEMAST lineData) throws SQLException {
        try {
            lineMastRepository.save(lineData);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.COM_SAVE_ERROR_MSG);
        }
    }

    @Transactional
    public Boolean UpadateLineMastData(Long id, MELINEMAST lineData) throws SQLException {
        try {
            Long code = lineMastRepository.getById(id).getId();
            if (code != null) {
                lineData.setId(code);
                lineMastRepository.save(lineData);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.COM_UP_ERROR_MSG);
        }
    }

    @Transactional
    public void SetLineMastListData(List<MELINEMAST> lineListData) throws SQLException {
        try {
            lineMastRepository.saveAll(lineListData);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.COM_SAVE_ERROR_MSG);
        }
    }

    @Transactional
    public void DelLineMastData(MELINEMAST lineCode) throws SQLException {
        Optional<MELINEMAST> melinemastList = lineMastRepository.findById(lineCode.getId());
        if(melinemastList.isPresent()){
            lineCode.setActive(false);
            lineMastRepository.save(lineCode);
        }else {
            throw new RuntimeException(MessageCode.NON_LINE_MSG);
        }
    }


    @Transactional
    public List<MEFABMAST> Getmefabsettingmast() throws SQLException {
        try {
            return fabMastRepository.findAllByActiveOrderByFabNameAsc(true);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.FAB_NON_DATA_MSG);
        }
    }

    @Transactional
    public void Setmefabsettingmast(MEFABMAST mefabmast) throws SQLException {
        try {
            fabMastRepository.save(mefabmast);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.COM_SAVE_ERROR_MSG);
        }
    }

    @Transactional
    public void Delmefabsettingmast(MEFABMAST mefabmast) throws SQLException {
        Optional<MEFABMAST> mefabmastlist = fabMastRepository.findById(mefabmast.getId());
        if(mefabmastlist.isPresent()){
            mefabmast.setActive(false);
            fabMastRepository.save(mefabmast);
        }else {
            throw new RuntimeException(MessageCode.NON_EQP_MSG);
        }
    }

    @Transactional
    public void SetmefabsettingmastList(List<MEFABMAST> mefabmast) throws SQLException {
        try {
            fabMastRepository.saveAll(mefabmast);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.COM_SAVE_ERROR_MSG);
        }
    }

    @Transactional
    public Boolean Upadatemefabsettingmast(Long id, MEFABMAST lineData) throws SQLException {
        try {
            Long code = fabMastRepository.getById(id).getId();
            if (code != null) {
                lineData.setId(code);
                fabMastRepository.save(lineData);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.COM_UP_ERROR_MSG);
        }
    }
}
