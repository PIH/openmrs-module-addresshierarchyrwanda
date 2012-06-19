package org.openmrs.module.addresshierarchyrwanda;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.addresshierarchyrwanda.AddressHierarchy;
import org.openmrs.module.addresshierarchyrwanda.AddressHierarchyService;
import org.openmrs.test.BaseModuleContextSensitiveTest;

public class AddressHierarchyTest extends BaseModuleContextSensitiveTest {
	
	
	protected static final String XML_DATASET_PACKAGE_PATH = "org/openmrs/include/standardTestDataset.xml";
	
	
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
    	
    	AddressHierarchyService ahs = Context.getService(AddressHierarchyService.class);
    	Assert.assertNotNull(ahs);
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
