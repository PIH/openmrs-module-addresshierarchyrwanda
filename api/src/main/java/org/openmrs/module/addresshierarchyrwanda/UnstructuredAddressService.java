package org.openmrs.module.addresshierarchyrwanda;

import org.openmrs.module.addresshierarchyrwanda.db.UnstructuredAddressDAO;
import org.springframework.transaction.annotation.Transactional;

public interface UnstructuredAddressService {
	
	public UnstructuredAddressDAO getUnstructuredAddressDAO();

	@Transactional
	public void setUnstructuredAddressDAO(UnstructuredAddressDAO unstructuredAddressDAO);

	@Transactional
	public void saveUnstructuredAddress(UnstructuredAddress unstructuredAddress);
	
	@Transactional(readOnly = true)
	public void getUnstructuredAddress(int id);
}
