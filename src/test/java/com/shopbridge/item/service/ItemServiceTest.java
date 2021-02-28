package com.shopbridge.item.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.shopbridge.item.dto.ItemDTO;
import com.shopbridge.item.entity.Item;
import com.shopbridge.item.repository.ItemRepository;
import com.shopbridge.item.response.ItemResponse;

import junit.framework.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemServiceTest {

	@Autowired
	ItemService itemService;

	@MockBean
	ItemRepository itemRepository;
	
	Item item = new Item();

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);	
		item.setId(1);
		item.setName("Apple");
		item.setDescription("Apple from Kashmir");
		item.setPrice(123.9f);		
	}

	@Test
	public void saveItemTest() {
		ItemDTO itemDTO = setValue();
		Mockito.when(itemRepository.findByName(Mockito.anyString())).thenReturn(null);
	    Mockito.when(itemRepository.save(Mockito.any(Item.class))).thenReturn(item);	    
	    ItemResponse itemResponse = new ItemResponse(null,false,"Item Saved Successfully");	    
	    Assert.assertEquals(itemResponse.getError(),itemService.saveItem(itemDTO).getError());
	    
	}
	
	@Test
	public void findByItemIdTest() {
		ItemDTO itemDTO = setValue();
	    Mockito.when(itemRepository.getOne(Mockito.anyInt())).thenReturn(item);	    
	    ItemResponse itemResponse = new ItemResponse(itemDTO,false,"Item Found");	    
	    Assert.assertEquals(itemResponse.getError(),itemService.findByItemId(1).getError());
	    
	}

	@Test
	public void deleteByItemIdTest() {
	    Mockito.when(itemRepository.getOne(Mockito.anyInt())).thenReturn(item);	    
	    ItemResponse itemResponse = new ItemResponse(null,false,"Item deleted Successfully");	    
	    Assert.assertEquals(itemResponse.getError(),itemService.deleteByItemId(1).getError());
	    
	}
	

	@Test
	public void findAllItemTest() {
		ItemDTO itemDTO = setValue();
		List<ItemDTO> itemDTOList = new ArrayList<>();
		itemDTOList.add(itemDTO);
		List<Item> itemList = new ArrayList<>();
		itemList.add(item);
	    Mockito.when(itemRepository.findAll()).thenReturn(itemList);	    
	    ItemResponse itemResponse = new ItemResponse(itemDTOList,false,"Items Found");	    
	    Assert.assertEquals(itemResponse.getError(),itemService.findAllItem().getError());
	    
	}

	
	public ItemDTO setValue() {
		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setId(1);
		itemDTO.setName("Apple");
		itemDTO.setDescription("Apple from Kashmir");
		itemDTO.setPrice(123.9f);
		itemDTO.setImage("dchgghdskjvcfdshvkjdkfhkjd");
		return itemDTO;		
	}
	

}
