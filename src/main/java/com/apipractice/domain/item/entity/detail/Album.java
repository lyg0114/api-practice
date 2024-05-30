package com.apipractice.domain.item.entity.detail;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;
import static org.springframework.util.StringUtils.hasText;

import com.apipractice.domain.common.BaseTimeEntity;
import com.apipractice.domain.item.dto.ItemDto;
import com.apipractice.domain.item.dto.ItemDto.AlbumItemRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.domain.item.entity.detail
 * @since : 18.05.24
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Table(name = "album")
@Entity
public class Album extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "album_id")
  private Long id;

  @Column(name = "artist")
  private String artist;

  @Column(name = "etc")
  private String etc;

  public void updateAlbum(AlbumItemRequest album) {
    if (hasText(album.getArtist())) {
      this.artist = album.getArtist();
    }
    if (hasText(album.getEtc())) {
      this.etc = album.getEtc();
    }
  }
}
