package org.openmrs.module.addresshierarchyrwanda.db;

import java.util.List;

import org.openmrs.module.addresshierarchyrwanda.AddressHierarchy;
import org.openmrs.module.addresshierarchyrwanda.AddressHierarchyType;
import org.openmrs.module.addresshierarchyrwanda.UnstructuredAddress;

/**
 * The Interface AddressHierarchyDAO which is implemented in
 * HibernateAddressHierarchyDAO which links to the tables address_hierarchy,
 * address_hierarchy_type and person_address. This class does the functions of
 * storing and retrieving addresses.
 */
public interface AddressHierarchyDAO {
	
	public List<Object[]> getLocationAddressBreakdown(int locationId);
	
	public List<Object[]> getAllAddresses(int page);
	
	public void associateCoordinateToLeafNode(AddressHierarchy ah, double latitude, double longitude);
	
	public int getUnstructuredCount();
	
	public List<Object[]> findUnstructuredAddresses(int page, int locationId);
	
	public AddressHierarchy getAddressHierarchy(int addressHierarchyId);
	
	public void initializeHierarchyTables();

	public int getAddressHierarchyCount();

	public AddressHierarchy addLocation(int parentId, String name, int typeId);

	public void removeHierarchyTables();

	public AddressHierarchy getLocationFromUserGenId(String userGeneratedId);

	public AddressHierarchyType getHierarchyType(int typeId);

	public List<AddressHierarchyType> getAddressHierarchyTypes();

	public void saveAddressHierarchy(AddressHierarchy ah);

	public List<AddressHierarchy> searchHierarchy(String searchString, int locationTypeId);

	public List<AddressHierarchy> getTopOfHierarchyList();

	public List<AddressHierarchy> getLeafNodes(AddressHierarchy ah);

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
	public AddressHierarchy editLocationName(Integer parentLocationId, String newName);

}
