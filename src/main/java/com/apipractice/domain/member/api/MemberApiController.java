package com.apipractice.domain.member.api;

import com.apipractice.domain.member.application.service.MemberService;
import com.apipractice.domain.member.dto.MemberDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
@Slf4j
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

  @GetMapping("/guest/{param}")
  public ResponseEntity<Void> guest(@PathVariable String param) {
    log.info("########################################");
    log.info("call guest + {}", param);
    log.info("########################################");
    return ResponseEntity.ok().build();
  }

  //TODO : 샘플 api로서 추후 제거
  @GetMapping("/user/{param}")
  public ResponseEntity<Void> user(@PathVariable String param) {
    log.info("########################################");
    log.info("call user + {}", param);
    log.info("########################################");
    return ResponseEntity.ok().build();
  }

  //TODO : 샘플 api로서 추후 제거
  @GetMapping("/admin/{param}")
  public ResponseEntity<Void> admin(@PathVariable String param) {
    log.info("########################################");
    log.info("call admin + {}", param);
    log.info("########################################");
    return ResponseEntity.ok().build();
  }
}
