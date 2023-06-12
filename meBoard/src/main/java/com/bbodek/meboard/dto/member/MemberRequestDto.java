package com.bbodek.meboard.dto.member;


import com.bbodek.meboard.domain.user.Authority;
import com.bbodek.meboard.domain.user.Member;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDto {

    private String email;
    private String password;

    private String fabCode;
    private String userName;
    private String fabName;
    private String lineCode;
    private String lineName;
    private String workCode;
    private String workName;
    private  boolean userCheckInit;


    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .userName(userName)
                .fabCode(fabCode)
                .fabName(fabName)
                .lineCode(lineCode)
                .lineName(lineName)
                .userCheckInit(false)
                .password(passwordEncoder.encode(password))
                .authority(Authority.ROLE_USER)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
