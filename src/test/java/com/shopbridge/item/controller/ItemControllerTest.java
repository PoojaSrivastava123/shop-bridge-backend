package com.shopbridge.item.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopbridge.item.dto.ItemDTO;
import com.shopbridge.item.entity.Item;
import com.shopbridge.item.repository.ItemRepository;
import com.shopbridge.item.response.ItemResponse;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerTest {

	@InjectMocks
	private ItemController itemController;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ItemRepository itemRepository;
	
	Item item = new Item();
	ItemDTO itemDTO = new ItemDTO();
	
	@Before
	public void init() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
	
		item.setId(1);
		item.setName("Apple");
		item.setDescription("Apple from Kashmir");
		item.setPrice(100f);
	
		itemDTO.setId(1);
		itemDTO.setName("Apple");
		itemDTO.setDescription("Apple from Kashmir");	
		itemDTO.setPrice(100f);
	}
	
	@Test
	public void saveItemIfCaseTest() throws Exception {
		Mockito.when(itemRepository.findByName(Mockito.anyString())).thenReturn(item);	
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/item/save")
				.contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"Apple\",\"description\":\"Apple from Kashmir\",\"price\":\"100\",\"image\":\"gfchgcjhgjh\"}");
		MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();
		String outputInJson = result.getResponse().getContentAsString();
		ItemResponse itemResponse = new ItemResponse(null, true, "Item already exists");
		String inputInJson = mapToJson(itemResponse);
		JSONAssert.assertEquals(inputInJson, outputInJson, false);

	}

	@Test
	public void saveItemElseCaseTest() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/item/save")
				.contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"\",\"description\":\"\",\"price\":\"\",\"image\":\"\"}");
		MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();
		String outputInJson = result.getResponse().getContentAsString();
		ItemResponse itemResponse = new ItemResponse(null, false, "Item Saved Successfully");
		String inputInJson = mapToJson(itemResponse);
		JSONAssert.assertEquals(inputInJson, outputInJson, false);

	}
	
	@Test
	public void getItemByIdItemIfCaseTest() throws Exception {
		Mockito.when(itemRepository.getOne(Mockito.anyInt())).thenReturn(item);		
		ItemResponse itemResponse = new ItemResponse(itemDTO, false, "Item Found");
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/item/1")
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();
		String outputInJson = result.getResponse().getContentAsString();
		String inputInJson = mapToJson(itemResponse);
		JSONAssert.assertEquals(inputInJson, outputInJson, false);
	}
	
	@Test
	public void getItemByIdItemElseCaseTest() throws Exception {
		Mockito.when(itemRepository.getOne(Mockito.anyInt())).thenReturn(null);		
		ItemResponse itemResponse = new ItemResponse(null, true, "Item not found");
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/item/1")
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();
		String outputInJson = result.getResponse().getContentAsString();
		String inputInJson = mapToJson(itemResponse);
		JSONAssert.assertEquals(inputInJson, outputInJson, false);
	}
	
	@Test
	public void getAllItemItemIfCaseTest() throws Exception {
		List<Item> items = new ArrayList<>();
		items.add(item);
		Mockito.when(itemRepository.findAll()).thenReturn(items);
		
		List<ItemDTO> itemsDTO = new ArrayList<>();
		itemsDTO.add(itemDTO);
		ItemResponse itemResponse = new ItemResponse(itemsDTO, false, "Items Found");		
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/item/all")
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();
		String outputInJson = result.getResponse().getContentAsString();
		String inputInJson = mapToJson(itemResponse);
		JSONAssert.assertEquals(inputInJson, outputInJson, false);
	}
	
	@Test
	public void getAllItemItemElseCaseTest() throws Exception {
		List<Item> items = new ArrayList<>();
		Mockito.when(itemRepository.findAll()).thenReturn(items);
		
		ItemResponse itemResponse = new ItemResponse(null, true, "No Item Found");		
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/item/all")
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();
		String outputInJson = result.getResponse().getContentAsString();
		String inputInJson = mapToJson(itemResponse);
		JSONAssert.assertEquals(inputInJson, outputInJson, false);
	}
	
	@Test
	public void deleteItemIfCaseTest() throws Exception {
		Mockito.when(itemRepository.getOne(Mockito.anyInt())).thenReturn(item);		
		ItemResponse itemResponse = new ItemResponse(null, false, "Item deleted Successfully");
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/item/1")
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();
		String outputInJson = result.getResponse().getContentAsString();
		String inputInJson = mapToJson(itemResponse);
		JSONAssert.assertEquals(inputInJson, outputInJson, false);
	}
	
	@Test
	public void deleteItemElseCaseTest() throws Exception {
		Mockito.when(itemRepository.getOne(Mockito.anyInt())).thenReturn(null);		
		ItemResponse itemResponse = new ItemResponse(null, true, "Item not found");
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/item/1")
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();
		String outputInJson = result.getResponse().getContentAsString();
		String inputInJson = mapToJson(itemResponse);
		JSONAssert.assertEquals(inputInJson, outputInJson, false);
	}


	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(object);

	}

}
