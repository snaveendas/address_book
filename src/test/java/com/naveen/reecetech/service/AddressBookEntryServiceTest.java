package com.naveen.reecetech.service;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.naveen.reecetech.model.AddressBookEntry;


@RunWith(SpringRunner.class)
public class AddressBookEntryServiceTest {
	
	private AddressBookEntryService service;
	
	@Before
	public void Setup() {
		service = new AddressBookEntryService();
	}
	
	private AddressBookEntry createTestEntry(String name,String number) {
        return new AddressBookEntry(name,number);
    }
	
	@Test
	public void testAddEntry() {
		AddressBookEntry testObj= createTestEntry("abc","1234567890");
		ResponseEntity<AddressBookEntry> respEntity = service.addAddressBookEntry("testOne", testObj);
		AddressBookEntry resp = respEntity.getBody();
		Assert.assertEquals(resp, testObj);
		Assert.assertEquals(respEntity.getBody().getName(), "abc");
		Assert.assertEquals(respEntity.getBody().getNumber(), "1234567890");	
		Assert.assertEquals(respEntity.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void testGetAllEntries() {
		AddressBookEntry testObjOne= createTestEntry("abc","123");
		AddressBookEntry testObjTwo= createTestEntry("def","456");
		AddressBookEntry testObjThree= createTestEntry("abc","123");
		service.addAddressBookEntry("testOne", testObjOne);
		service.addAddressBookEntry("testOne", testObjTwo);
		service.addAddressBookEntry("testOne", testObjThree);
		ResponseEntity<List<AddressBookEntry>> nonUniqueEntries = service.getAllEntries(false);
		Assert.assertEquals(nonUniqueEntries.getBody().stream().map(e -> e.getName()).collect(Collectors.joining(",")), "abc,def,abc");	
		Assert.assertEquals(nonUniqueEntries.getBody().stream().map(e -> e.getNumber()).collect(Collectors.joining(",")), "123,456,123");	
		
		ResponseEntity<List<AddressBookEntry>> uniqueEntries = service.getAllEntries(true);
		Assert.assertEquals(uniqueEntries.getBody().stream().map(e -> e.getName()).collect(Collectors.joining(",")), "abc,def");	
		Assert.assertEquals(uniqueEntries.getBody().stream().map(e -> e.getNumber()).collect(Collectors.joining(",")), "123,456");	
	}
	
	@Test
	public void removeAddressBookEntry() throws Exception {
		AddressBookEntry testObjOne= createTestEntry("abc","123");
		service.addAddressBookEntry("testOne", testObjOne);
		ResponseEntity<List<AddressBookEntry>> testResponse = service.removeAddressBookEntry("123");
		Assert.assertEquals(testResponse.getBody().get(0).getName(),"abc");
		
		ResponseEntity<List<AddressBookEntry>> testNotFoundResponse = service.removeAddressBookEntry("123");
		Assert.assertEquals(testNotFoundResponse.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@After
	public void tearDown() {
		service = null;
	}
	

}
