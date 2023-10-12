package com.naveen.reecetech;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.naveen.reecetech.model.AddressBookEntry;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReecetechApplicationTests {

	
	@Test
	public void contextLoads() {
	}
	
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	private String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
	
	@Test
	public void testCreateEntry() throws Exception {
		mockMvc.perform(post("/api/v1/reece-address-book/contacts/test")
		      .content(asJsonString(new AddressBookEntry("abc","123")))
		      .contentType(MediaType.APPLICATION_JSON)
		      .accept(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists())
	      .andExpect(MockMvcResultMatchers.jsonPath("$.number").exists());
	}
	
	
	@Test
	public void testGetEntries() throws Exception {
		mockMvc.perform(post("/api/v1/reece-address-book/contacts/test")
			      .content(asJsonString(new AddressBookEntry("abcxxx","123456")))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON));
		
		mockMvc.perform(post("/api/v1/reece-address-book/contacts/test")
			      .content(asJsonString(new AddressBookEntry("abcdef","123456789")))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON));
		
		mockMvc.perform(post("/api/v1/reece-address-book/contacts/test")
			      .content(asJsonString(new AddressBookEntry("abcxxx","123456")))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON));
		
		mockMvc.perform(get("/api/v1/reece-address-book/contacts")).andExpect(status().isOk())
		.andExpect(content().string(containsString("abcxxx")))
		.andExpect(jsonPath("$[0].name").value("abcxxx"))
		.andExpect(jsonPath("$[1].name").value("abcdef"))
		.andExpect(jsonPath("$[2].name").value("abcxxx"))
		.andExpect(jsonPath("$[0].number").value("123456"))
		.andExpect(jsonPath("$[1].number").value("123456789"))
		.andExpect(jsonPath("$[2].number").value("123456"));
		
		mockMvc.perform(get("/api/v1/reece-address-book/contacts?unique=true")).andExpect(status().isOk())
		.andExpect(content().string(containsString("abcxxx")))
		.andExpect(jsonPath("$[0].name").value("abcxxx"))
		.andExpect(jsonPath("$[1].name").value("abcdef"))
		.andExpect(jsonPath("$[0].number").value("123456"))
		.andExpect(jsonPath("$[1].number").value("123456789"));

	}
	
	@Test
	public void testRemoveEntry() throws Exception{
		mockMvc.perform(post("/api/v1/reece-address-book/contacts/test")
			      .content(asJsonString(new AddressBookEntry("abc","456")))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON));
		
		mockMvc.perform(delete("/api/v1/reece-address-book/delete_contacts/456") )
        .andExpect(status().isOk());
		
		mockMvc.perform(delete("/api/v1/reece-address-book/delete_contacts/456") )
        .andExpect(status().isNotFound());
	}
}