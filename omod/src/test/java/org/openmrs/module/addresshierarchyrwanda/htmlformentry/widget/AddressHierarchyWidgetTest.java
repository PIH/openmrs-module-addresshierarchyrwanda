/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.addresshierarchyrwanda.htmlformentry.widget;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.PersonAddress;
import org.openmrs.module.htmlformentry.FormEntryContext;
import org.openmrs.module.htmlformentry.FormEntryContext.Mode;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.openmrs.test.Verifies;

@Ignore
public class AddressHierarchyWidgetTest extends BaseModuleContextSensitiveTest {
	
	protected static final String XML_DATASET_PACKAGE_PATH = "org/openmrs/include/standardTestDataset.xml";
	
	@Before
	public void setupDatabase() throws Exception {
		//initializeInMemoryDatabase();
		authenticate();
		executeDataSet(XML_DATASET_PACKAGE_PATH);
	}
	
//	@Before
//	public void setupPersonAddress() {
//		pa1 = new PersonAddress();
//		pa1.setAddress1("firsttest");
//		pa1.setAddress2("firsttest2");
//		pa1.setDateCreated(new Date());
//		pa1.setVoided(false);
//		pa1.setCountry("Rwanda");
//	}
	
	/**
	 * @see {@link AddressHierarchyWidget#AddressHierarchyWidget(String,PersonAddress)}
	 */
	@Test
	@Verifies(value = "should set type and initial Value", method = "AddressHierarchyWidget(String,PersonAddress)")
	public void AddressHierarchyWidget_shouldSetTypeAndInitialValue() throws Exception {
		
		
		PersonAddress pa1 = new PersonAddress();
		pa1.setAddress1("firsttest");
		pa1.setAddress2("firsttest2");
		pa1.setDateCreated(new Date());
		pa1.setVoided(false);
		pa1.setCountry("Rwanda");
		
		AddressHierarchyWidget widget;
		widget = new AddressHierarchyWidget("country", pa1);
		
		Assert.assertNotNull(widget.getType());
		Assert.assertNotNull(widget.getInitialValue());
		Assert.assertTrue(widget.getType().equals("country"));
		Assert.assertTrue(widget.getInitialValue().equals(pa1.getCountry()));
	}
	
	/**
	 * @see {@link AddressHierarchyWidget#generateHtml(FormEntryContext)}
	 */
	@Test
	@Verifies(value = "should return HTML snippet", method = "generateHtml(FormEntryContext)")
	public void generateHtml_shouldReturnHTMLSnippet() throws Exception {
		FormEntryContext context = new FormEntryContext(Mode.ENTER);
		PersonAddress pa1 = new PersonAddress();
		pa1.setAddress1("firsttest");
		pa1.setAddress2("firsttest2");
		pa1.setDateCreated(new Date());
		pa1.setVoided(false);
		pa1.setCountry("Rwanda");
		
		AddressHierarchyWidget widget;
		
		widget = new AddressHierarchyWidget("country", pa1);
		context.registerWidget(widget);
		
		String resultHTML = widget.generateHtml(context);
		Assert.assertTrue(resultHTML.length() > 0);
		Assert.assertTrue(resultHTML.indexOf("country") > 0);
	}
}
