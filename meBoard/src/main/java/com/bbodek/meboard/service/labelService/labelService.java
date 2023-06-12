package com.bbodek.meboard.service.labelService;

import com.bbodek.meboard.domain.label.bbodeklabel;
import com.bbodek.meboard.domain.opsWork.MEOPSWORK;
import com.bbodek.meboard.respository.labelRepository.labelRepository;
import com.bbodek.meboard.util.commonMsg.MessageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class labelService {

    @Autowired
    private labelRepository labelrepository;

    @Transactional
    public List<bbodeklabel> GetLabelDataList() throws SQLException {
        try {
            return labelrepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.LABEL_NON_DATA_MSG);
        }
    }

    @Transactional
    public bbodeklabel GetLabelData(String qrcode) throws SQLException {
        try {
            return labelrepository.findByQrCode(qrcode);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.LABEL_NON_DATA_MSG);
        }
    }

    @Transactional
    public void SetLabelData(bbodeklabel bbodeklabelList) throws SQLException {
        try {
            labelrepository.save(bbodeklabelList);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.LABEL_SAVE_DATA_MSG);
        }
    }



    @Transactional
    public void DelLabelData(long code) throws SQLException {
        try {
            labelrepository.deleteById(code);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.LABEL_SAVE_DATA_MSG);
        }
    }


}
