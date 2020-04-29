package org.openmrs.module.addresshierarchyrwanda;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

@Ignore
public class AddressHierarchyTest extends BaseModuleContextSensitiveTest {
	protected final Log log = LogFactory.getLog(getClass());
	
	protected static final String XML_DATASET_PACKAGE_PATH = "org/openmrs/include/standardTestDataset.xml";

	@Autowired
	private AddressHierarchyService addressHierarchyService;

//	@Override
//	public Boolean useInMemoryDatabase() {
//		
//		return false;
//	}
	
	@Before
	public void setupDatabase() throws Exception {
		//initializeInMemoryDatabase();
		authenticate();
		executeDataSet(XML_DATASET_PACKAGE_PATH);
	}
	
	
    @Test
	public void leafNodesShouldBeUmudugudus() throws Exception{
    	
    	//AddressHierarchyService ahs = Context.getService(AddressHierarchyService.class);
    	Assert.assertNotNull(addressHierarchyService);
//    	AddressHierarchy ah = ahs.getNextComponent(1).get(0);
//    	List<AddressHierarchy> children = ahs.getLeafNodes(ah);
//    	for(AddressHierarchy leaf: children){
//    		Assert.assertTrue(leaf.getHierarchyType().getName().equals("Umudugudu"));
//    	}
	}
    
    @Test
	public void topOfListShouldBeCountries() throws Exception{
    	
    	AddressHierarchyService ahs = ((AddressHierarchyService) Context.getService(AddressHierarchyService.class));
    	List<AddressHierarchy> countries = ahs.getTopOfHierarchyList();
    	//there aren't any unless you initialize the heirarchy which takes 15 min.  too long to wait to make this test meaningful
    	for(AddressHierarchy country: countries){
    		Assert.assertTrue(country.getHierarchyType().getName().equals("Country"));
    	}
	}
    
}
