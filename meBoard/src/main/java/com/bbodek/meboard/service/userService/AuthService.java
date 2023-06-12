package com.bbodek.meboard.service.userService;


import com.bbodek.meboard.domain.user.Member;
import com.bbodek.meboard.domain.user.RefreshToken;
import com.bbodek.meboard.dto.member.MemberRequestDto;
import com.bbodek.meboard.dto.member.MemberResponseDto;
import com.bbodek.meboard.dto.member.TokenDto;
import com.bbodek.meboard.dto.member.TokenRequestDto;
import com.bbodek.meboard.respository.userRepository.MemberRepository;
import com.bbodek.meboard.respository.userRepository.RefreshTokenRepository;
import com.bbodek.meboard.util.jwtUtil.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public MemberResponseDto signup(MemberRequestDto memberRequestDto) {
        if (memberRepository.existsByEmail(memberRequestDto.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }

        Member member = memberRequestDto.toMember(passwordEncoder);
        return MemberResponseDto.of(memberRepository.save(member));
    }

    @Transactional
    public MemberResponseDto signupUpdate(MemberRequestDto memberRequestDto) {
        Member member =  new Member();
        Optional<Member> email = memberRepository.findByEmail(memberRequestDto.getEmail());
        if (email == null) {
            throw new RuntimeException("가입되어 있지 않은 유저 입니다.");
        }
        member.setId(email.get().getId());
        member.setPassword(email.get().getPassword());
        member.setAuthority(email.get().getAuthority());
        member.setUserName(memberRequestDto.getUserName());
        member.setEmail(memberRequestDto.getEmail());
        member.setFabCode(memberRequestDto.getFabCode());
        member.setFabName(memberRequestDto.getFabName());
        member.setLineCode(memberRequestDto.getLineCode());
        member.setLineName(memberRequestDto.getLineName());
        member.setUserCheckInit(memberRequestDto.isUserCheckInit());
        return MemberResponseDto.of(memberRepository.save(member));
    }


    @Transactional
    public MemberResponseDto passwordc(MemberRequestDto memberRequestDto) {
        Optional<Member> email = memberRepository.findByEmail(memberRequestDto.getEmail());
        if (email == null) {
            throw new RuntimeException("가입되어 있지 않은 유저 입니다.");
        }
        Member member = memberRequestDto.toMember(passwordEncoder);
        member.setId(email.get().getId());
        member.setUserName(email.get().getUserName());
        member.setEmail(email.get().getEmail());
        member.setFabCode(email.get().getFabCode());
        member.setFabName(email.get().getFabName());
        member.setLineCode(email.get().getLineCode());
        member.setLineName(email.get().getLineName());
        member.setUserCheckInit(true);
        return MemberResponseDto.of(memberRepository.save(member));
    }

    @Transactional
    public TokenDto login(MemberRequestDto memberRequestDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = memberRequestDto.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return tokenDto;
    }

    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenDto;
    }
}
