package com.bbodek.meboard.respository.mastRepository;


import com.bbodek.meboard.domain.eqpMast.MEEQPMAST;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EqpMastRepository extends JpaRepository<MEEQPMAST, Long> {
    List<MEEQPMAST> findAllByLineCodeAndActive(String linecode, boolean active);
    List<MEEQPMAST> findAllByFabCodeAndActive(String fabcode, boolean active);
    List<MEEQPMAST> findAllByActive(boolean active);
}
