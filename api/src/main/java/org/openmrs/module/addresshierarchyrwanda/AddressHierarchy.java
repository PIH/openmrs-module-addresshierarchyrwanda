package org.openmrs.module.addresshierarchyrwanda;

import java.util.Set;

/**
 * The Class AddressHierarchy is linked to the table address_hierarchy table
 * mapped in AddressHierarchy.hbm.xml.
 */
public class AddressHierarchy {
	private Integer addressHierarchyId;
	private String locationName;
	private AddressHierarchyType hierarchyType;
	private AddressHierarchy parent;
	private String userGeneratedId;
	private Double latitude;
	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getElevation() {
		return elevation;
	}

	public void setElevation(Double elevation) {
		this.elevation = elevation;
	}

	private Double longitude;
	private Double elevation;
	

	public AddressHierarchyType getHierarchyType() {
		return hierarchyType;
	}

	public void setHierarchyType(AddressHierarchyType hierarchyType) {
		this.hierarchyType = hierarchyType;
	}

	public String getUserGeneratedId() {
		return userGeneratedId;
	}

	public void setUserGeneratedId(String userGeneratedId) {
		this.userGeneratedId = userGeneratedId;
	}

	/**
	 * To string.
	 * 
	 * @return the string
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return locationName;
	}

	public Integer getAddressHierarchyId() {
		return addressHierarchyId;
	}

	public void setAddressHierarchyId(Integer addressHierarchyId) {
		this.addressHierarchyId = addressHierarchyId;
	}

	public AddressHierarchy getParent() {
		return parent;
	}

	public void setParent(AddressHierarchy parent) {
		this.parent = parent;
	}

	/**
	 * Gets the location name.
	 * 
	 * @return the location name
	 */
	public String getLocationName() {
		return locationName;
	}

	/**
	 * Sets the location name.
	 * 
	 * @param locationName
	 *            the new location name
	 */
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

}
