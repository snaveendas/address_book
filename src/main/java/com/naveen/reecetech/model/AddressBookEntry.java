package com.naveen.reecetech.model;

import java.util.Objects;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * POJO to hold the address book entry information.
 */
public class AddressBookEntry {
    
	@NotNull(message = "Name cannot be null")
	@NotEmpty(message = "Name cannot be empty")
	@Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Accepts only alphanumeric values.")
    private String name;
	
	@NotNull(message = "Number cannot be null")
	@NotEmpty(message = "Number cannot be empty")
	@Pattern(regexp= "\\(?\\d+\\)?[-.\\s]?\\d+[-.\\s]?\\d+", message= "Accepts only valid telephone number formats.")
    private String number;

	/**
	 * Constructor
	 * @param name - name of the address book entry.
	 * @param number - number of the address book entry.
	 */
    public AddressBookEntry(String name, String number){
        this.setName(name);
        this.setNumber(number);
    }

    /**
     * Get the address book entry name.
     * @return entry name as a String.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the address book entry name.
     * @param name - address book entry name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the address book entry name.
     * @return used to return the address book entry name as a string.
     */
    public String getNumber() {
        return number;
    }

    /**
     * Used to set the address book entry number.
     * @param number - the address book entry number.
     */
    public void setNumber(String number) {
        this.number = number;
    }
    
    /**
     * Overriding the equals method for stream operation.
     */
    @Override
    public boolean equals(Object obj) {
     	if(obj instanceof AddressBookEntry) {
    		return Objects.equals(name, ((AddressBookEntry) obj).name) && Objects.equals(number, ((AddressBookEntry)obj).number);
    	}
    	return false;
    }

    /**
     * Overriding the hashcode method for stream operation.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, number);
    }
    
}
