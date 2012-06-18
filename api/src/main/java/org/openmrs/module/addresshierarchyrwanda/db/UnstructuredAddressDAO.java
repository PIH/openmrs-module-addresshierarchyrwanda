package org.openmrs.module.addresshierarchyrwanda.db;

import org.openmrs.module.addresshierarchyrwanda.UnstructuredAddress;

/**
 * The Interface AddressHierarchyDAO which is implemented in HibernateAddressHierarchyDAO which links to the tables address_hierarchy, address_hierarchy_type and person_address. This class does the functions of storing and retrieving addresses.
 */
public interface UnstructuredAddressDAO{

	public void saveUnstructuredAddress(UnstructuredAddress unstructuredAddress);
	
	public void getUnstructuredAddress(int id);
    
}
