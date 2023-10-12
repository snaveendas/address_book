package com.naveen.reecetech.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.naveen.reecetech.model.AddressBookEntry;
import com.naveen.reecetech.service.AddressBookEntryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@RestController
@Api(produces = "application/json", value = "Operations pertaining to address book management application")
public class Controller {
    
    @Autowired
    private AddressBookEntryService addressBookEntryService;

    @ApiOperation(value = "Add a new entry to the address book", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added a new address book entry"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    }
    )
    @PostMapping("/api/v1/reece-address-book/contacts/{addressBookName}")
    public ResponseEntity<AddressBookEntry> addAddressEntry(@PathVariable String addressBookName, @Validated @RequestBody AddressBookEntry addressBookEntry){
    	return addressBookEntryService.addAddressBookEntry(addressBookName, addressBookEntry);
    }

    @ApiOperation(value = "View contents of address books", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved a list of address book entries"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    }
    )
    @GetMapping("/api/v1/reece-address-book/contacts")
    public ResponseEntity<List<AddressBookEntry>> getAllContacts(@RequestParam(value = "unique", defaultValue = "false") Boolean isUnique) {
    	return addressBookEntryService.getAllEntries(isUnique);
    }
    
    @ApiOperation(value = "View address book entry", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted the address book entries"),
            @ApiResponse(code = 404, message = "The resource you were trying to delete is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    }
    )
    @DeleteMapping("/api/v1/reece-address-book/delete_contacts/{number}")
    public ResponseEntity<List<AddressBookEntry>> removeContactFromAddressBook(@PathVariable String number) throws Exception {
    	return addressBookEntryService.removeAddressBookEntry(number);
    }

}
