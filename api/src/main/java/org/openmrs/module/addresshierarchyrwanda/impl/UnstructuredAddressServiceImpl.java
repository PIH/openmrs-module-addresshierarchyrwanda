package org.openmrs.module.addresshierarchyrwanda.impl;

import org.openmrs.module.addresshierarchyrwanda.UnstructuredAddress;
import org.openmrs.module.addresshierarchyrwanda.UnstructuredAddressService;
import org.openmrs.module.addresshierarchyrwanda.db.UnstructuredAddressDAO;

/**
 * The Class AddressHierarchyServiceImpl default implementation of AddressHierarchyService.
 */
public class UnstructuredAddressServiceImpl implements UnstructuredAddressService{
    
    private UnstructuredAddressDAO unstructuredAddressDAO;

	public UnstructuredAddressDAO getUnstructuredAddressDAO() {
		return unstructuredAddressDAO;
	}

	public void setUnstructuredAddressDAO(UnstructuredAddressDAO unstructuredAddressDAO) {
		this.unstructuredAddressDAO = unstructuredAddressDAO;
	}

	public void getUnstructuredAddress(int id) {
		// TODO Auto-generated method stub
		
	}

	public void saveUnstructuredAddress(UnstructuredAddress unstructuredAddress) {
		unstructuredAddressDAO.saveUnstructuredAddress(unstructuredAddress);
	}
    
    
    

}
