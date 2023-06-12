package com.bbodek.meboard.domain.user;

import com.bbodek.meboard.domain.config.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "meMember")
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String userName;

    private String password;

    private String fabCode;

    private String fabName;

    private String lineCode;

    private String lineName;

    private boolean userCheckInit;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Column(length = 50, updatable = false)
    private String regCd;

    @Column(length = 50, insertable = false)
    private String modCd;

    @Builder
    public Member(String email, String password, String userName, String fabCode,String fabName, String lineCode, String  lineName,
                 boolean userCheckInit,String regCd,String modCd,  Authority authority) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.fabCode = fabCode;
        this.fabName = fabName;
        this.lineCode = lineCode;
        this.lineName = lineName;
        this.regCd = regCd;
        this.modCd = modCd;
        this.authority = authority;
        this.userCheckInit = userCheckInit;
    }
}
