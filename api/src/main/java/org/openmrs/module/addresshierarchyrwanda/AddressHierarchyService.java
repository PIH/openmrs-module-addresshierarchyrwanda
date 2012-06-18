package org.openmrs.module.addresshierarchyrwanda;

import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.addresshierarchyrwanda.db.AddressHierarchyDAO;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Interface AddressHierarchyService has the service methods for
 * AddressHierarchy module.
 */

public interface AddressHierarchyService extends OpenmrsService {
	
	@Transactional(readOnly = true)
	public List<Object[]> getAllAddresses(int page);
	
	@Transactional(readOnly = true)
	public List<Object[]> getLocationAddressBreakdown(int locationId);
	
	@Transactional(readOnly = true)
	public AddressHierarchy getAddressHierarchy(int addressHierarchyId);
	
	@Transactional(readOnly = true)
	public List<Object[]> findUnstructuredAddresses(int page, int locationId);
	
	@Transactional(readOnly = true)
	public int getUnstructuredCount();
	
	@Transactional
	public void initializeHierarchyTables();

	@Transactional(readOnly = true)
	public int getAddressHierarchyCount();

	@Transactional
	public void truncateHierarchyTables();

	@Transactional
	public AddressHierarchy getLocationFromUserGenId(String userGeneratedId);

	@Transactional(readOnly = true)
	public AddressHierarchyType getHierarchyType(int typeId);

	@Transactional
	public void saveAddressHierarchy(AddressHierarchy ah);

	@Transactional
	public AddressHierarchy addLocation(int parentId, String name, int typeId);

	@Transactional(readOnly = true)
	public List<AddressHierarchy> getLeafNodes(AddressHierarchy ah);

	@Transactional(readOnly = true)
	public List<AddressHierarchy> searchHierarchy(String searchString, int locationTypeId);

	@Transactional(readOnly = true)
	public List<AddressHierarchyType> getAddressHierarchyTypes();

	@Transactional(readOnly = true)
	public List<AddressHierarchy> getTopOfHierarchyList();
	
	@Transactional
	public void associateCoordinatesToLeafNode(AddressHierarchy ah, double latitidue, double longitude);
	
	/**
	 * Sets the address hierarchy object dao.
	 * 
	 * @param dao
	 *            the new address hierarchy dao
	 */
	public void setAddressHierarchyDAO(AddressHierarchyDAO dao);

	@Transactional
	public void saveUnstructuredAddress(UnstructuredAddress unstructuredAddress);

	/**
	 * Method used to get the child locations.
	 * 
	 * @param type_Id
	 *            the type_ id
	 * @param location_Name
	 *            the location_ name
	 * @param parent_Id
	 *            the parent_ id
	 * 
	 * @return the next component in an array
	 * 
	 * @see org.openmrs.module.addresshierarchyrwanda.db.AddressHierarchyDAO#getNextComponent(java.lang.Integer,
	 *      java.lang.String, java.lang.Integer)
	 */
	@Transactional(readOnly = true)
	public List<AddressHierarchy> getNextComponent(Integer locationId);

	/**
	 * Method used to edit a location name.
	 * 
	 * @param parentLocationId
	 *            the parent location id
	 * @param oldName
	 *            the old name
	 * @param newName
	 *            the new name
	 * 
	 * @see org.openmrs.module.addresshierarchyrwanda.db.AddressHierarchyDAO#editLocation(java.lang.Integer,
	 *      java.lang.String, java.lang.String)
	 */
	@Transactional
	public AddressHierarchy editLocationName(Integer locationId, String newName);


}
