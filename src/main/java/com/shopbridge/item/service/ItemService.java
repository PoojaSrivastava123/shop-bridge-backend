package com.shopbridge.item.service;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopbridge.item.dto.ItemDTO;
import com.shopbridge.item.entity.Item;
import com.shopbridge.item.exception.ResourceAlreadyExistException;
import com.shopbridge.item.exception.ResourceNotFoundException;
import com.shopbridge.item.repository.ItemRepository;
import com.shopbridge.item.response.ItemResponse;

@Service
public class ItemService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	public ItemResponse saveItem(ItemDTO itemDTO) {
		ItemResponse itemResponse = new ItemResponse();
		Item itemObject = itemRepository.findByName(itemDTO.getName());
		if(Objects.nonNull(itemObject)) {
			throw new ResourceAlreadyExistException("Item already exists");
		}
		else {
			Item item = new Item();
			item.setName(itemDTO.getName());
			item.setDescription(itemDTO.getDescription());
			item.setPrice(itemDTO.getPrice());
			String imageString = itemDTO.getImage();
			try {
				System.out.println("inside try block");
				byte[] buff = imageString.getBytes();
				
				Blob blob = new SerialBlob(buff);
				item.setImage(blob);
				System.out.println("set blob");
			} catch (Exception e) {
				e.printStackTrace();
			}
			itemRepository.save(item);
			System.out.println("saved item");
			itemResponse.setError(false);
			itemResponse.setMessage("Item Saved Successfully");
			return itemResponse;
		}
	}
	
	public ItemResponse findByItemId(Integer id) {
		Item item = itemRepository.getOne(id);
		if(Objects.nonNull(item)) {
			ItemDTO itemDTO = convertEntityToDTO(item);
			System.out.println("Inside if block of find by id");
			ItemResponse itemResponse = new ItemResponse();
			itemResponse.setData(itemDTO);
			itemResponse.setError(false);
			itemResponse.setMessage("Item Found");
			return itemResponse;
		}
		else {
			throw new ResourceNotFoundException(id,"Item not found");
		}
	}
	
	public ItemResponse deleteByItemId(Integer id) {
		Item item = itemRepository.getOne(id);
		if(Objects.nonNull(item)) {
			itemRepository.deleteById(id);
			ItemResponse itemResponse = new ItemResponse();
			itemResponse.setError(false);
			itemResponse.setMessage("Item deleted Successfully");
			return itemResponse;
		}
		else {
			throw new ResourceNotFoundException(id,"Item not found");
		}
	}
	
	public ItemResponse findAllItem() {
		List<Item> items = itemRepository.findAll();
		List<ItemDTO> itemsDTO = new ArrayList<>();
		if(!items.isEmpty()) {
			for(Item item : items) {
				ItemDTO itemDTO = convertEntityToDTO(item);
				itemsDTO.add(itemDTO);
			}
			ItemResponse itemResponse = new ItemResponse();
			itemResponse.setError(false);
			itemResponse.setData(itemsDTO);
			itemResponse.setMessage("Items Found");
			return itemResponse;
		}
		else {
			throw new ResourceNotFoundException("No Item Found");
		}
	}
	
	public ItemDTO convertEntityToDTO(Item item) {
		ItemDTO itemDTO = new ItemDTO();
		if(Objects.nonNull(item.getImage())) {
			byte[] bytes = null;
			try {
				bytes = item.getImage().getBytes(1l,(int) item.getImage().length());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String blobString = new String(bytes);
			itemDTO.setImage(blobString);
		}		
		itemDTO.setName(item.getName());
		itemDTO.setDescription(item.getDescription());
		itemDTO.setPrice(item.getPrice());
		itemDTO.setId(item.getId());
		return itemDTO;
	}
	
}
