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
package org.openmrs.module.addresshierarchyrwanda.web.controller;




import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.layout.web.LayoutSupport;
import org.openmrs.layout.web.address.AddressSupport;
import org.openmrs.module.addresshierarchyrwanda.AddressHierarchyService;
import org.openmrs.web.controller.layout.LayoutPortletController;
import org.springframework.web.servlet.ModelAndView;



// TODO: Auto-generated Javadoc
/**
 * The Class AddressLayoutPortletController.
 */
public class AddressLayoutPortletController extends LayoutPortletController {
	
	/** The log. */
	private static Log log = LogFactory.getLog(AddressLayoutPortletController.class);
	
	/**
	 * Gets the service.
	 * 
	 * @return the service
	 */
	private AddressHierarchyService getService(){
        return (AddressHierarchyService)Context.getService(AddressHierarchyService.class);
    }
    
	
	/**
	 * Gets the defaults property name.
	 * 
	 * @return the defaults property name
	 * 
	 * @see org.openmrs.web.controller.layout.LayoutPortletController#getDefaultsPropertyName()
	 */
	protected String getDefaultsPropertyName() {
		return "layout.address.defaults";
	}
	
	/**
	 * Gets the default div id.
	 * 
	 * @return the default div id
	 * 
	 * @see org.openmrs.web.controller.layout.LayoutPortletController#getDefaultDivId()
	 */
	protected String getDefaultDivId() {
		return "addressLayoutPortlet";
	}
	
	/**
	 * Gets the layout support instance.
	 * 
	 * @return the layout support instance
	 * 
	 * @see org.openmrs.web.controller.layout.LayoutPortletController#getLayoutSupportInstance()
	 */
	protected LayoutSupport getLayoutSupportInstance() {
		log.debug("Getting address layout instance");
		
		return AddressSupport.getInstance();
		
	}
	
	
	/**
	 * Handle request method which helps to override the addresslayout portlet.
	 * 
	 * @param request the request
	 * @param response the response
	 * 
	 * @return the model and view
	 * 
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * 
	 * @see org.openmrs.web.controller.PortletController#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ModelAndView mav = super.handleRequest(request, response);
		String portletPath = "/module/addresshierarchyrwanda/portlets/addressLayout";
		mav.setViewName(portletPath);
		return mav;
	}
		
	
}
