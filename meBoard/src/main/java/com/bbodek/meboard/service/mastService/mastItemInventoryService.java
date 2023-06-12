package com.bbodek.meboard.service.mastService;

import com.bbodek.meboard.domain.mast.MEFABSETTINGMAST;
import com.bbodek.meboard.domain.mast.MEITEMINVENTORY;
import com.bbodek.meboard.respository.mastRepository.ItemInventoryRepository;
import com.bbodek.meboard.util.commonMsg.MessageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class mastItemInventoryService {

    @Autowired
    private ItemInventoryRepository itemInventoryRepository;

    @Transactional
    public List<MEITEMINVENTORY> GetItemInventoryMastDataList(String bucode) throws SQLException {
        try {
            return itemInventoryRepository.findAllByActiveAndBusiness(true,bucode);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.EQP_NON_DATA_MSG);
        }
    }


    @Transactional
    public void SetItemInventoryMastDataList(List<MEITEMINVENTORY> meiteminventoryList) throws SQLException {
        try {
            itemInventoryRepository.saveAll(meiteminventoryList);
        } catch (Exception e) {
            throw new RuntimeException(MessageCode.COM_SAVE_ERROR_MSG);
        }
    }

}
