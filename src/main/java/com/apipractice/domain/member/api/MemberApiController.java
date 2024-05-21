package com.apipractice.domain.member.api;

import com.apipractice.domain.member.application.MemberService;
import com.apipractice.domain.member.dto.MemberDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.domain.member.api
 * @since : 18.05.24
 */
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
@RestController
public class MemberApiController {

  private final MemberService memberService;

  @PostMapping("/signup")
  public ResponseEntity<Void> signup(
      @RequestBody @Valid MemberDto.SignUpRequest requestDto
  ) {
    memberService.signUp(requestDto);
    return ResponseEntity.ok().build();
  }

  //TODO : 샘플 api로서 추후 제거
  @PostMapping("/hello/{param}")
  public ResponseEntity<Void> hello(@PathVariable String param) {
    System.out.println("########################################");
    System.out.println("param = " + param);
    System.out.println("########################################");
    return ResponseEntity.ok().build();
  }
}
