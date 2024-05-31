package com.apipractice.domain.item.application.repository;

import com.apipractice.domain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.domain.item.application.repository
 * @since : 25.05.24
 */
public interface ItemRepositroy extends JpaRepository<Item, Long>, ItemRepositroyCustom {

  boolean existsByName(String name);

}
