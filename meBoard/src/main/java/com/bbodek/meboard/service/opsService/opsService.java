package com.bbodek.meboard.service.opsService;

import com.bbodek.meboard.domain.eqpMast.MEEQPMAST;
import com.bbodek.meboard.domain.eqpMast.MELINEMAST;
import com.bbodek.meboard.domain.mast.MEFABMAST;
import com.bbodek.meboard.domain.opsWork.MEOPSTAG;
import com.bbodek.meboard.domain.opsWork.MEOPSWORK;
import com.bbodek.meboard.domain.opsWork.MEOPSWORKER;
import com.bbodek.meboard.domain.opsWork.MEOPSWORKTIME;
import com.bbodek.meboard.respository.opsRepository.OpsTagRepository;
import com.bbodek.meboard.respository.opsRepository.OpsWorkRepository;
import com.bbodek.meboard.respository.opsRepository.OpsWorkerRepository;
import com.bbodek.meboard.respository.opsRepository.OpsWorkerTimeRepository;
import com.bbodek.meboard.util.commonMsg.MessageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class opsService {

    @Autowired
    private OpsWorkRepository opsWorkRepository;

    @Autowired
    private OpsTagRepository opsTagRepository;

    @Autowired
    private OpsWorkerRepository opsWorkerRepository;

    @Autowired
    private OpsWorkerTimeRepository opsWorkerTimeRepository;

    //GET
    @Transactional
    public List<MEOPSWORK> GetOPSWorkData() throws SQLException {
        try {
            return opsWorkRepository.findAllByActive(true);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.OPSWORK_NON_DATA_MSG);
        }
    }

    @Transactional
    public List<MEOPSWORK> GetOPSWorkLineCodeData(String linecode) throws SQLException {
        try {
            return opsWorkRepository.findByLineCode(linecode);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.OPSWORK_NON_DATA_MSG);
        }
    }

    @Transactional
    public List<MEOPSWORKER> GetOPSWorkerData() throws SQLException {
        try {
            return opsWorkerRepository.findAllByActive(true);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.OPSWORK_NON_DATA_MSG);
        }
    }

    @Transactional
    public List<MEOPSTAG> GetOPSWorkTagData() throws SQLException {
        try {
            return opsTagRepository.findAllByActive(true);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.OPSWORK_NON_DATA_MSG);
        }
    }

    //SET
    @Transactional
    public void SetOPSWorkData(MEOPSWORK meopswork) throws SQLException {
        try {
            opsWorkRepository.save(meopswork);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.COM_SAVE_ERROR_MSG);
        }
    }

    @Transactional
    public void SetOPSWorkerData(MEOPSWORKER meopsworker) throws SQLException {
        try {
            opsWorkerRepository.save(meopsworker);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.COM_SAVE_ERROR_MSG);
        }
    }

    @Transactional
    public void SetOPSWorkTagData(MEOPSTAG meopstag) throws SQLException {
        try {
            opsTagRepository.save(meopstag);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.COM_SAVE_ERROR_MSG);
        }
    }

    @Transactional
    public void SetOPSWorkerTimeData(MEOPSWORKTIME meopsworktime) throws SQLException {
        try {
            opsWorkerTimeRepository.save(meopsworktime);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.COM_SAVE_ERROR_MSG);
        }
    }


    //SETList
    @Transactional
    public void SetOPSWorkListData(List<MEOPSWORK> meopswork) throws SQLException {
        try {
            opsWorkRepository.saveAll(meopswork);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.COM_SAVE_ERROR_MSG);
        }
    }

    @Transactional
    public void SetOPSWorkerListData(List<MEOPSWORKER> meopsworker) throws SQLException {
        try {
            opsWorkerRepository.saveAll(meopsworker);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.COM_SAVE_ERROR_MSG);
        }
    }

    @Transactional
    public void SetOPSWorkTagListData(List<MEOPSTAG> meopstag) throws SQLException {
        try {
            opsTagRepository.saveAll(meopstag);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.COM_SAVE_ERROR_MSG);
        }
    }

    //Del
    @Transactional
    public void DelOPSWorkData(MEOPSWORK opsWorkCode) throws SQLException {
        Optional<MEOPSWORK> meopsworkList = opsWorkRepository.findById(opsWorkCode.getId());
        if(meopsworkList.isPresent()){
            opsWorkCode.setActive(false);
            opsWorkRepository.save(opsWorkCode);
        }else {
            throw new RuntimeException(MessageCode.NON_OPSWORK_MSG);
        }
    }

    @Transactional
    public void DelOPSWokerData(MEOPSWORKER opsWorkerCode) throws SQLException {
        Optional<MEOPSWORKER> meopsworkerlist = opsWorkerRepository.findById(opsWorkerCode.getId());
        if(meopsworkerlist.isPresent()){
            opsWorkerCode.setActive(false);
            opsWorkerRepository.save(opsWorkerCode);
        }else {
            throw new RuntimeException(MessageCode.NON_OPSWORKER_MSG);
        }
    }

    @Transactional
    public void DelOPSWorkerTagData(MEOPSTAG opsWorkTagCode) throws SQLException {
        Optional<MEOPSTAG> meopstaglist = opsTagRepository.findById(opsWorkTagCode.getId());
        if(meopstaglist.isPresent()){
            opsWorkTagCode.setActive(false);
            opsTagRepository.save(opsWorkTagCode);
        }else {
            throw new RuntimeException(MessageCode.NON_OPSWORKTAG_MSG);
        }
    }


    //Update
    @Transactional
    public Boolean UpadateWorkData(Long id, MEOPSWORK meopswork) throws SQLException {
        try {
            Long code = opsWorkRepository.getById(id).getId();
            if (code != null) {
                meopswork.setId(code);
                opsWorkRepository.save(meopswork);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.COM_UP_ERROR_MSG);
        }
    }

    @Transactional
    public Boolean UpadateWorkerData(Long id, MEOPSWORKER meopsworker) throws SQLException {
        try {
            Long code = opsWorkerRepository.getById(id).getId();
            if (code != null) {
                opsWorkerRepository.save(meopsworker);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.COM_UP_ERROR_MSG);
        }
    }

    @Transactional
    public Boolean UpadateWokerTagData(Long id, MEOPSTAG meopstag) throws SQLException {
        try {
            Long code = opsTagRepository.getById(id).getId();
            if (code != null) {
                opsTagRepository.save(meopstag);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.COM_UP_ERROR_MSG);
        }
    }





    @Transactional
    public List<MEOPSWORK> GetOPSWorkFabLineData(HashMap<String,Object> cobodata) throws SQLException {
        try {
            String fabCode =cobodata.get("fabCode").toString();
            String lineCode =cobodata.get("lineCode").toString();
            return opsWorkRepository.findAllByActiveAndFabCodeAndLineCode(true,fabCode,lineCode);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.OPSWORK_NON_DATA_MSG);
        }
    }


    @Transactional
    public List<MEOPSWORKER> GetOPSWorkerFabLineData(HashMap<String,Object> cobodata) throws SQLException {
        try {
            String fabCode =cobodata.get("fabCode").toString();
            String lineCode =cobodata.get("lineCode").toString();
            String workcode =cobodata.get("workCode").toString();
            return opsWorkerRepository.findAllByActiveAndFabCodeAndLineCodeAndWorkCode(true,fabCode,lineCode,workcode);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.OPSWORK_NON_DATA_MSG);
        }
    }


    @Transactional
    public MEOPSWORKTIME GetOPSWorkerTimeData(String opcode) throws SQLException {
        try {
            return opsWorkerTimeRepository.findByAndOpCode(opcode);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.OPSWORK_NON_DATA_MSG);
        }
    }


}
