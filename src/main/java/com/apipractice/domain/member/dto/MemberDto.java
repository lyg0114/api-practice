package com.apipractice.domain.member.dto;

import static com.apipractice.domain.common.Address.createAddress;
import static lombok.AccessLevel.PRIVATE;

import com.apipractice.domain.member.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.domain.member.dto
 * @since : 18.05.24
 */
public class MemberDto {

  @Getter
  @AllArgsConstructor
  public static class SignUpRequest {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "성함을 입력해주세요.")
    private String name;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,15}",
        message = "비밀번호는 영어, 숫자, 특수문자를 포함하여 8~15자로 입력해주세요.")
    private String password;

    private String nickname;

    private String city;

    private String street;

    private String zipCode;

    public Member toEntity() {
      return Member.builder()
          .email(this.email)
          .password(this.password)
          .name(this.name)
          .nickname(this.nickname)
          .address(createAddress(city, street, zipCode))
          .build();
    }
  }

  @Getter
  @AllArgsConstructor(access = PRIVATE)
  public static class SignUpResponse {

    private String email;
    private String password;

    public static SignUpResponse fromRequestDto(SignUpRequest requestDto) {
      return new SignUpResponse(requestDto.getEmail(), requestDto.getPassword());
    }
  }

}