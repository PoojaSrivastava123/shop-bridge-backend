package com.shopbridge.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopbridge.item.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer>{
	
	Item findByName(String name);

}
