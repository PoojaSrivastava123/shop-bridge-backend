package com.shopbridge.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopbridge.item.dto.ItemDTO;
import com.shopbridge.item.response.ItemResponse;
import com.shopbridge.item.service.ItemService;

@RestController
@RequestMapping("/item")
@CrossOrigin
public class ItemController {
	
	@Autowired
	ItemService itemService;
	
	@PostMapping(value = "/save")
	public ItemResponse saveItem(@RequestBody ItemDTO itemDTO) {
		return itemService.saveItem(itemDTO);
	}
	
	@GetMapping(value = "/{id}")
	public ItemResponse getItemByIdItem(@PathVariable("id") Integer id) {
		return itemService.findByItemId(id);
	}
	
	@GetMapping(value = "/all")
	public ItemResponse getAllItemItem() {
		return itemService.findAllItem();
	}
	
	@DeleteMapping(value = "/{id}")
	public ItemResponse deleteItem(@PathVariable("id") Integer id) {
		return itemService.deleteByItemId(id);
	}

}
