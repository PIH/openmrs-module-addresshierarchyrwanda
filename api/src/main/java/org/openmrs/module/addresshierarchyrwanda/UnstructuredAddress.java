package org.openmrs.module.addresshierarchyrwanda;

import org.openmrs.Person;
import org.openmrs.PersonAddress;

public class UnstructuredAddress {
	private Person person;
	private PersonAddress personAddress;
	private int unstructuredAddressId;
	
	public int getUnstructuredAddressId() {
		return unstructuredAddressId;
	}
	public void setUnstructuredAddressId(int unstructuredAddressId) {
		this.unstructuredAddressId = unstructuredAddressId;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public PersonAddress getPersonAddress() {
		return personAddress;
	}
	public void setPersonAddress(PersonAddress personAddress) {
		this.personAddress = personAddress;
	}
	
}
