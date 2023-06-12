package com.bbodek.meboard.controller.user;


import com.bbodek.meboard.domain.user.Authority;
import com.bbodek.meboard.domain.user.Member;
import com.bbodek.meboard.dto.member.*;
import com.bbodek.meboard.service.userService.AuthService;
import com.bbodek.meboard.service.userService.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(authService.signup(memberRequestDto));
    }

    @PostMapping("/signupupdate")
    public ResponseEntity<MemberResponseDto> signupUpdate(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(authService.signupUpdate(memberRequestDto));
    }

    @PostMapping("/password")
    public ResponseEntity<MemberResponseDto> passwordC(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(authService.passwordc(memberRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<MemberDto> login(@RequestBody MemberRequestDto memberRequestDto) {
        MemberDto memberDto = new MemberDto();
        memberDto.setTokenDto(authService.login(memberRequestDto));
        Member member = memberService.getMemberData(memberRequestDto.getEmail());
        boolean checkMember = member.getAuthority().name() == Authority.ROLE_ADMIN.toString() ? true : false;
        memberDto.setEmail(member.getEmail());
        memberDto.setUserName(member.getUserName());
        memberDto.setFabCode(member.getFabCode());
        memberDto.setFabName(member.getFabName());
        memberDto.setLineCode(member.getLineCode());
        memberDto.setLineName(member.getLineName());
        memberDto.setAuthority(checkMember);
        memberDto.setUserCheckInit(member.isUserCheckInit());
        return ResponseEntity.ok(memberDto);
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }
}
