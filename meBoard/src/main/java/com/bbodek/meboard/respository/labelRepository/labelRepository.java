package com.bbodek.meboard.respository.labelRepository;

import com.bbodek.meboard.domain.label.bbodeklabel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface labelRepository extends JpaRepository<bbodeklabel, Long> {
    bbodeklabel findByQrCode(String qrcode);
}
