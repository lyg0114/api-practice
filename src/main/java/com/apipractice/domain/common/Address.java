package com.apipractice.domain.common;

import static lombok.AccessLevel.PRIVATE;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.global.type
 * @since : 18.05.24
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@Getter
@Embeddable
public class Address {

  private String city;

  private String street;

  private String zipCode;

  public static Address createAddress(String city, String street, String zipcode) {
    AddressBuilder builder = Address.builder();
    if(StringUtils.hasText(city)){ builder.city(city); }
    if(StringUtils.hasText(street)){ builder.street(street); }
    if(StringUtils.hasText(zipcode)){ builder.zipCode(zipcode); }
    return builder.build();
  }
}
