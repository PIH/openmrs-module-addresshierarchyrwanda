package org.openmrs.module.addresshierarchyrwanda.db.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.openmrs.PersonAddress;
import org.openmrs.module.addresshierarchyrwanda.AddressHierarchy;
import org.openmrs.module.addresshierarchyrwanda.AddressHierarchyType;
import org.openmrs.module.addresshierarchyrwanda.UnstructuredAddress;
import org.openmrs.module.addresshierarchyrwanda.db.AddressHierarchyDAO;
import org.openmrs.module.addresshierarchyrwanda.util.AHConst;

/**
 * The Class HibernateAddressHierarchyDAO which links to the tables
 * address_hierarchy, address_hierarchy_type and person_address. This class does
 * the functions of storing and retrieving addresses.
 */
public class HibernateAddressHierarchyDAO implements AddressHierarchyDAO {

	protected final Log log = LogFactory.getLog(getClass());
	/**
	 * Hibernate session factory
	 */
	private SessionFactory sessionFactory;
	
	
	
	public AddressHierarchy getAddressHierarchy(int addressHierarchyId) {
		Session session = sessionFactory.getCurrentSession();
		AddressHierarchy ah = (AddressHierarchy)session.load(AddressHierarchy.class, addressHierarchyId);
		
		return ah;
	}

	public void deleteLocationAndAllChildren(AddressHierarchy ah) {
		// 1) get lowest level
		// 2) get parents
		// 3) delete level
		// 4) get parents2
		// 5) delete level2
	}
	
	public List<AddressHierarchy> getLeafNodes(AddressHierarchy ah){
		List<AddressHierarchy> leafList = new ArrayList<AddressHierarchy>();
		getLowestLevel(ah, leafList);
//		for(AddressHierarchy child:leafList){
//			System.out.println("child node is " + child.getLocationName() );
//		}
		return leafList;
	}
	
	/**
	 * Recursively finds leaf nodes of ah
	 * @param ah
	 * @param leafList
	 * @return
	 */
	private List<AddressHierarchy> getLowestLevel(AddressHierarchy ah, List<AddressHierarchy> leafList) {
		List<AddressHierarchy> children = getNextComponent(ah.getAddressHierarchyId());
		if (children.size() > 0) {
			for (AddressHierarchy addressHierarchy : children) {
				getLowestLevel(addressHierarchy, leafList);
			}
		}else{
			leafList.add(ah);
		}
		return children;
	}
	
	
	public void associateCoordinateToLeafNode(AddressHierarchy ah, double latitude, double longitude){
		ah.setLatitude(latitude);
		ah.setLongitude(longitude);
		Session session = sessionFactory.getCurrentSession();
		session.update(ah);
	}
	

	public AddressHierarchy addLocation(int parentId, String name, int typeId) {
		AddressHierarchy ah = null;
		if (parentId != -1) {
			Session session = sessionFactory.getCurrentSession();
			ah = new AddressHierarchy();
			ah.setLocationName(name);
			if (typeId != -1) {
				ah.setHierarchyType(getHierarchyType(typeId));
			} else {
				ah.setHierarchyType(getAddressHierarchy(parentId).getHierarchyType().getChildType());
			}
			ah.setParent(getAddressHierarchy(parentId));
			session.save(ah);
		}
		
		return ah;
	}


	public AddressHierarchyType getHierarchyType(int typeId) {
		Session session = sessionFactory.getCurrentSession();
		AddressHierarchyType type = (AddressHierarchyType) session.load(AddressHierarchyType.class, typeId);

		return type;
	}

	public void initializeHierarchyTables() {
		Session session = sessionFactory.getCurrentSession();

		AddressHierarchyType country = new AddressHierarchyType();
		country.setName("Country");

		AddressHierarchyType province = new AddressHierarchyType();
		province.setName("Province");

		AddressHierarchyType district = new AddressHierarchyType();
		district.setName("District");

		AddressHierarchyType sector = new AddressHierarchyType();
		sector.setName("Sector");

		AddressHierarchyType cell = new AddressHierarchyType();
		cell.setName("Cell");

		AddressHierarchyType umudugudu = new AddressHierarchyType();
		umudugudu.setName("Umudugudu");

		country.setChildType(province);
		province.setParentType(country);
		province.setChildType(district);

		district.setParentType(province);
		district.setChildType(sector);

		sector.setParentType(district);
		sector.setChildType(cell);

		cell.setParentType(sector);
		cell.setChildType(umudugudu);
		umudugudu.setParentType(cell);
		
		session.save(country);
		session.save(province);
		session.save(country);
		session.save(district);
		session.save(sector);
		session.save(cell);
		session.save(umudugudu);

	}

	public void removeHierarchyTables() {
		Session session = sessionFactory.getCurrentSession();
		session.createSQLQuery("truncate table address_hierarchy").executeUpdate();
		session.createSQLQuery("delete from address_hierarchy_type").executeUpdate();
	}

	public int getAddressHierarchyCount() {
		int x = 0;
		Session session = sessionFactory.getCurrentSession();
		try{
			x = session.createQuery("from AddressHierarchy").list().size();
		} catch (Exception ex){
			//pass
		}	
		return x;
	}

	/**
	 * Gets the typeId of the locations which are children of the location
	 * specified by locationId
	 * 
	 * @param typeId
	 */
//	private int getTypeOfChild(int locationId) {
//		Session session = sessionFactory.getCurrentSession();
//		Criteria criteria = session.createCriteria(AddressHierarchy.class);
//		criteria.setMaxResults(1);
//		List<AddressHierarchy> list = criteria.add(Restrictions.eq("parentId", locationId)).list();
//		int typeId = -1;
//		if (list != null && list.size() > 0) {
//			typeId = list.get(0).getLocationId();
//		}
//
//		return typeId;
//	}

	/**
	 * Searches for locations like the <code>searchString</code>
	 */
	public List<AddressHierarchy> searchHierarchy(String searchString, int locationTypeId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(AddressHierarchy.class);
		criteria.add(Restrictions.like("locationName", searchString, MatchMode.ANYWHERE));
		List<AddressHierarchy> hierarchyList;
		if (locationTypeId != -1) {
			criteria.createCriteria("hierarchyType").add(Restrictions.eq("typeId", locationTypeId));
		}

		hierarchyList = criteria.list();
		return hierarchyList;
	}

	public void saveUnstructuredAddress(UnstructuredAddress unstructuredAddress) {
		Session session = sessionFactory.getCurrentSession();
		session.save(unstructuredAddress);

	}
	
	public int getUnstructuredCount(int page){
		SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(AHConst.INVALID_ADDRESS_COUNT);
		List<Integer> unstructuredCount = sqlQuery.list();
		int count=0;
		if(unstructuredCount.size() > 0){
			count = unstructuredCount.get(0);
		}
		return count;
	}
	
//	public List<Object[]> findUnstructuredAddresses(int page){
//		int startIndex = 0;
//		if(page > 0){
//			startIndex = page*100 - 100;
//		}
//		
//		SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(AHConst.INVALID_ADDRESS_QUERY);
//		sqlQuery
//		.addScalar("patient_identifier.identifier",Hibernate.STRING)
//		.addScalar("person_name.family_name",Hibernate.STRING)
//		.addScalar("person_name.given_name",Hibernate.STRING)
//		.addScalar("location.name",Hibernate.STRING)
//		.addScalar("person_address.state_province",Hibernate.STRING)
//		.addScalar("person_address.county_district",Hibernate.STRING)
//		.addScalar("person_address.city_village",Hibernate.STRING)
//		.addScalar("person_address.neighborhood_cell",Hibernate.STRING)
//		.addScalar("person_address.address1",Hibernate.STRING)
//		.addScalar("patient.patient_id",Hibernate.INTEGER);
//		
//		sqlQuery.setMaxResults(100);
//		sqlQuery.setFirstResult(startIndex);
//		
//		List<Object[]> unstructuredPersonAddressIds = sqlQuery.list();
//		return unstructuredPersonAddressIds;
//	}
	
	public List<Object[]> getLocationAddressBreakdown(int locationId){
		SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(AHConst.LOCATION_BREAKDOWN);
		sqlQuery.addScalar("city_village",Hibernate.STRING)
		.addScalar("count(*)",Hibernate.INTEGER)
		.setInteger(0, locationId);
		
		
		return sqlQuery.list();
	}
	
	public List<Object[]> findUnstructuredAddresses(int page,int locationId){
		int startIndex = 0;
		if(page > 0){
			startIndex = page*100 - 100;
		}
		
		SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(AHConst.CELL_UMU);
		sqlQuery
		.addScalar("patient_id",Hibernate.INTEGER)
		.addScalar("identifier",Hibernate.STRING)
		.addScalar("name",Hibernate.STRING)
		.addScalar("state_province", Hibernate.STRING)
		.addScalar("county_district", Hibernate.STRING)
		.addScalar("city_village", Hibernate.STRING)
		.addScalar("neighborhood_cell", Hibernate.STRING)
		.addScalar("address1", Hibernate.STRING);
		sqlQuery.setInteger(0, locationId);
		
//		x.state_province, x.county_district, x.city_village,x.neighborhood_cell,x.address1
		
		sqlQuery.setMaxResults(100);
		sqlQuery.setFirstResult(startIndex);
		
		List<Object[]> unstructuredPersonAddressIds = sqlQuery.list();
		
		return unstructuredPersonAddressIds;
	}
	
	public List<Object[]> getAllAddresses(int page){
		
		int startIndex = 0;
		if(page > 0){
			startIndex = page*400 - 400;
		}
		
		SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(AHConst.ALL_ADDRESSES);
		sqlQuery
		.addScalar("patient_id", Hibernate.INTEGER)
		.addScalar("country",Hibernate.STRING)
		.addScalar("person_address.state_province",Hibernate.STRING)
		.addScalar("person_address.county_district", Hibernate.STRING)
		.addScalar("person_address.city_village",Hibernate.STRING)
		.addScalar("person_address.neighborhood_cell",Hibernate.STRING)
		.addScalar("person_address.address1",Hibernate.STRING);
		
		
		sqlQuery.setMaxResults(100);
		sqlQuery.setFirstResult(startIndex);
		
		List<Object[]> allAddresses = sqlQuery.list();
		//List<PersonAddress> pas = convertToPersonAddresses(allAddresseses);
		
		return allAddresses;
	}
	
	
	private List<PersonAddress> convertToPersonAddresses(List<Object[]> rows){
		List<PersonAddress> pas = new ArrayList<PersonAddress>();
		
		PersonAddress pa = new PersonAddress();
		
		for(Object[] row : rows){
			pa.setCountry((String)row[1]);
			pa.setStateProvince((String)row[2]);
			pa.setCountyDistrict((String)row[3]);
			pa.setCityVillage((String)row[4]);
			pa.setNeighborhoodCell((String)row[5]);
			pa.setAddress1((String)row[6]);
			
			pas.add(pa);
		}
		
		return pas;
	}
	
	

	public void saveAddressHierarchy(AddressHierarchy ah) {
		Session session = sessionFactory.getCurrentSession();
		session.save(ah);
		session.flush();
		session.clear();
	}

	/**
	 * Set session factory
	 * 
	 * @param sessionFactory
	 */

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * Method used to get the child locations.
	 * 
	 * @param parent_type_Id
	 *            the parent_type_ id
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
	public List<AddressHierarchy> getNextComponent(Integer locationId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(AddressHierarchy.class);
		List<AddressHierarchy> list = criteria.createCriteria("parent").add(Restrictions.eq("addressHierarchyId", locationId)).list();
		return list;
	}

	@SuppressWarnings( { "unchecked" })
	public List<AddressHierarchy> getTopOfHierarchyList() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(AddressHierarchy.class);
		List list = criteria.add(Restrictions.isNull("parent")).createCriteria("hierarchyType").add(
				Restrictions.isNull("parentType")).list();

		return list;
	}

	/**
	 * Changes the locations name to <code>newName</code>
	 * 
	 * @param locationId
	 * @param newName
	 */
	public AddressHierarchy editLocationName(Integer locationId, String newName) {
		// begin transaction
		Session session = sessionFactory.getCurrentSession();

		// get the location by id
		Criteria c = session.createCriteria(AddressHierarchy.class);
		c.add(Restrictions.idEq(locationId));
		List<AddressHierarchy> hierarchyList = c.list();
		AddressHierarchy ah = null;

		// change the name if we have an ah
		if (hierarchyList != null && hierarchyList.size() > 0) {
			ah = hierarchyList.get(0);
			ah.setLocationName(newName);
		}

		// close the transaction
		return ah;
	}

	public List<AddressHierarchyType> getAddressHierarchyTypes() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(AddressHierarchyType.class);
		return criteria.list();
	}

	/**
	 * Execute an update query when a string query is passed.
	 * 
	 * @param query
	 *            the update query
	 * 
	 * @see org.openmrs.module.addresshierarchyrwanda.db.AddressHierarchyDAO#execQuery(java.lang.String)
	 */

	public AddressHierarchy getLocationFromUserGenId(String userGeneratedId) {
		AddressHierarchy ah = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(AddressHierarchy.class);

		List<AddressHierarchy> list = criteria.add(Restrictions.eq("userGeneratedId", userGeneratedId)).list();
		if (list != null && list.size() > 0) {
			ah = list.get(0);
		}
		return ah;
	}
	
	

	public int getUnstructuredCount() {
		// TODO Auto-generated method stub
		return 0;
	}

}
