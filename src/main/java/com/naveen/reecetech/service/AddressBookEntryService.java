package com.naveen.reecetech.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.naveen.reecetech.model.AddressBookEntry;

/**
 * The service class associated with handling of address book entries of the address book.
 * Interface was not considered due to the limited behavior.
 */
@Service
public class AddressBookEntryService {
		
	/**
	 * Map used to hold address book entries in memory.
	 */
	private final Map<String, List<AddressBookEntry>> addressBookMap = new HashMap<>();
	
    
    /**
     * Used to create and add an entry into the address book.
     * @param addressBookName - the identifier for the address book entry.
     * @param addressBookEntry - the actual address book entry.
     * @return the address book entry as part of the response entity.
     */
    public ResponseEntity<AddressBookEntry> addAddressBookEntry(String addressBookName, AddressBookEntry addressBookEntry) {
        if (!addressBookMap.containsKey(addressBookName)) {
            addressBookMap.put(addressBookName, new ArrayList<>());
        }
        addressBookMap.get(addressBookName).add(addressBookEntry);
        return new ResponseEntity<AddressBookEntry>(addressBookEntry, HttpStatus.OK);
    }
    
    /**
     * Used to retrieve the list of entries from address book(s)
     * @param isUnique - true returns unique/distinct entries, false returns all entries across address books.
     * @return list of address entries.
     */
    public ResponseEntity<List<AddressBookEntry>> getAllEntries(boolean isUnique) {
    	if(isUnique) {
    		return new ResponseEntity<List<AddressBookEntry>>(addressBookMap.values().stream().flatMap(List::stream).distinct().collect(Collectors.toList()), HttpStatus.OK);
    	} else {
    		return new ResponseEntity<List<AddressBookEntry>>(addressBookMap.values().stream().flatMap(List::stream).collect(Collectors.toList()),HttpStatus.OK);
    	}
    }
    
    /**
     * Used to delete/remove an address book entry.
     * @param number - the number associated with the address book entry.
     * 
     */
	public ResponseEntity<List<AddressBookEntry>> removeAddressBookEntry(String number) throws Exception{
		List<AddressBookEntry> entriesToDelete = addressBookMap.values().stream().flatMap(List::stream).filter(n -> n.getNumber().equals(number)).collect(Collectors.toList());
		if(entriesToDelete.isEmpty()) {
			return new ResponseEntity<List<AddressBookEntry>>(Collections.emptyList(), HttpStatus.NOT_FOUND);
		}
		
    	for (List<AddressBookEntry> addressBookEntryList : addressBookMap.values()) {
    		addressBookEntryList.removeIf(n -> number.equals(n.getNumber()));
        }
    	
    	return new ResponseEntity<List<AddressBookEntry>>(entriesToDelete, HttpStatus.OK);
    }
    
}
