package com.apipractice.integration.member.application.service;

import com.apipractice.domain.member.application.service.MemberService;
import com.apipractice.global.aop.aspect.TraceAspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.integration.member.application.service
 * @since : 25.05.24
 */
@Import(TraceAspect.class)
@ActiveProfiles(value = "local")
@SpringBootTest
public class MemberServiceIntegrationTest {

  @Autowired
  MemberService memberService;

}
