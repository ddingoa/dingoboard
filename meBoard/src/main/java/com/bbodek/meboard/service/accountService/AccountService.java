package com.bbodek.meboard.service.accountService;

import com.bbodek.meboard.domain.erpMast.bdItem010;
import com.bbodek.meboard.domain.erpMast.bdVdr010;
import com.bbodek.meboard.respository.accountRepository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public List<bdVdr010> GetAccountData() throws SQLException {
        return accountRepository.findAll();
    }

    @Transactional
    public List<bdVdr010> GetAccountBUData(String buCode) throws SQLException {
        return accountRepository.findAllByDivisionCd(buCode);
    }


    @Transactional
    public bdVdr010 GetAccountDataS(String accountcode) throws SQLException {
        return accountRepository.findByVendorCd(accountcode);
    }

    @Transactional
    public void SetAccountData(bdVdr010 meaccount) throws SQLException {
         accountRepository.save(meaccount);
    }

    @Transactional
    public void SetAccountDataList(List<bdVdr010> meaccount) throws SQLException {
        accountRepository.saveAll(meaccount);
    }

    @Transactional
    public Boolean UpadateAccountData(String id, bdVdr010 meaccount) throws SQLException {
        bdVdr010 code = accountRepository.findByVendorCd(id);
        if (code != null) {
            accountRepository.save(meaccount);
            return true;
        } else {
            return false;
        }
    }
}
