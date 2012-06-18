package org.openmrs.module.addresshierarchyrwanda.impl;

import java.util.List;

import org.openmrs.module.addresshierarchyrwanda.AddressHierarchy;
import org.openmrs.module.addresshierarchyrwanda.AddressHierarchyService;
import org.openmrs.module.addresshierarchyrwanda.AddressHierarchyType;
import org.openmrs.module.addresshierarchyrwanda.UnstructuredAddress;
import org.openmrs.module.addresshierarchyrwanda.db.AddressHierarchyDAO;

/**
 * The Class AddressHierarchyServiceImpl default implementation of AddressHierarchyService.
 */
public class AddressHierarchyServiceImpl implements AddressHierarchyService {
	
    private AddressHierarchyDAO dao;
    
    
    public List<Object[]> getLocationAddressBreakdown(int locationId){
    	return dao.getLocationAddressBreakdown(locationId);
    }
    
    
    public List<Object[]> findUnstructuredAddresses(int page, int locationId){
    	return dao.findUnstructuredAddresses(page, locationId);
    }
    
    public List<Object[]> getAllAddresses(int page){
    	return dao.getAllAddresses(page);
    }
    
    public int getUnstructuredCount(){
    	return dao.getUnstructuredCount();
    }
    
    public void initializeHierarchyTables(){
    	dao.initializeHierarchyTables();
    }
    
    public int getAddressHierarchyCount(){
    	return dao.getAddressHierarchyCount();
    }
    
    public void truncateHierarchyTables(){
		dao.removeHierarchyTables();
	}
    
    public AddressHierarchy addLocation(int parentId, String name, int typeId){
    	return dao.addLocation(parentId, name, typeId);
    }
    public AddressHierarchyType getHierarchyType(int typeId){
    	return dao.getHierarchyType(typeId);
    }
    
    public List<AddressHierarchy> searchHierarchy(String searchString, int locationTypeId){
    	return dao.searchHierarchy(searchString, locationTypeId);
    }
	
    public List<AddressHierarchy> getTopOfHierarchyList(){
    	return dao.getTopOfHierarchyList();
    }
    /**
     * @see org.openmrs.module.addresshierarchyrwanda.AddressHierarchyService#setAddressHierarchyDAO(org.openmrs.module.addresshierarchyrwanda.db.AddressHierarchyDAO)
     */
    public void setAddressHierarchyDAO(AddressHierarchyDAO dao){
        this.dao = dao;
    };

    /**
     * Method used to add a location to the address_hierarchy table when an AddressHierarchy object is sent.
     * 
     * @param ahs the AddressHierarchy Object
     * 
     * @return the Location Id
     * 
     * @see org.openmrs.module.addresshierarchyrwanda.db.AddressHierarchyDAO#setNextComponent(org.openmrs.module.addresshierarchyrwanda.AddressHierarchy)
     */

    /**
     * Method used to get the child locations.
     * 
     * @param type_Id the type_ id
     * @param location_Name the location_ name
     * @param parent_Id the parent_ id
     * 
     * @return the next component in an array
     * 
     * @see org.openmrs.module.addresshierarchyrwanda.db.AddressHierarchyDAO#getNextComponent(java.lang.Integer, java.lang.String, java.lang.Integer)
     */
    public List<AddressHierarchy> getNextComponent(Integer locationId){
        return dao.getNextComponent(locationId);
    }    

    /**
     * Method gives out the total number of locations in the address_hierarchy table.
     * 
     * @return the location count
     * 
     * @see org.openmrs.module.addresshierarchyrwanda.db.AddressHierarchyDAO#locationCount()


    /**
     * Method used to edit a location name.
     * 
     * @param parentLocationId the parent location id
     * @param oldName the old name
     * @param newName the new name
     * 
     * @see org.openmrs.module.addresshierarchyrwanda.db.AddressHierarchyDAO#editLocation(java.lang.Integer, java.lang.String, java.lang.String)
     */
    public AddressHierarchy editLocationName(Integer parentLocationId,String newName){
    	 return dao.editLocationName(parentLocationId,newName);
    }



    
    
    /**
     * Execute an update query when a string query is passed.
     * 
     * @param query the update query
     * 
     * @see org.openmrs.module.addresshierarchyrwanda.db.AddressHierarchyDAO#execQuery(java.lang.String)
     */
	public AddressHierarchy getLocationFromUserGenId(String userGeneratedId) {
		// TODO Auto-generated method stub
		return dao.getLocationFromUserGenId(userGeneratedId);
	}
	public void saveAddressHierarchy(AddressHierarchy ah) {
		dao.saveAddressHierarchy(ah);
		
	}
	public List<AddressHierarchyType> getAddressHierarchyTypes() {
		// TODO Auto-generated method stub
		return dao.getAddressHierarchyTypes();
	}
	public void saveUnstructuredAddress(UnstructuredAddress unstructuredAddress) {
		dao.saveUnstructuredAddress(unstructuredAddress);
	}

	public List<AddressHierarchy> getLeafNodes(AddressHierarchy ah) {
		// TODO Auto-generated method stub
		return dao.getLeafNodes(ah);
	}

	public AddressHierarchy getAddressHierarchy(int addressHierarchyId) {
		return dao.getAddressHierarchy(addressHierarchyId);
	}

	@Override
	public void associateCoordinatesToLeafNode(AddressHierarchy ah, double latitude, double longitude) {
		dao.associateCoordinateToLeafNode(ah, latitude, longitude);
	}


	@Override
	public void onShutdown() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onStartup() {
		// TODO Auto-generated method stub
		
	}


}
