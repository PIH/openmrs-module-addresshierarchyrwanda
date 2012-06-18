package org.openmrs.module.addresshierarchyrwanda.db.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openmrs.module.addresshierarchyrwanda.UnstructuredAddress;
import org.openmrs.module.addresshierarchyrwanda.db.UnstructuredAddressDAO;

/**
 * The Class HibernateAddressHierarchyDAO which links to the tables address_hierarchy, address_hierarchy_type and person_address. This class does the functions of storing and retrieving addresses.
 */
public class HibernateUnstructuredAddressDAO implements UnstructuredAddressDAO{
    
    protected final Log log = LogFactory.getLog(getClass());

    private SessionFactory sessionFactory;
    
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void getUnstructuredAddress(int id) {
		// TODO Auto-generated method stub
		
	}
	
	public void saveUnstructuredAddress(UnstructuredAddress unstructuredAddress) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(unstructuredAddress);
		session.getTransaction().commit();
		
	}
    
}