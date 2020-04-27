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
package org.openmrs.module.addresshierarchyrwanda;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.module.Activator;
import org.openmrs.module.addresshierarchyrwanda.htmlformentry.handler.AddressHierarchyTagHandler;
import org.openmrs.module.addresshierarchyrwanda.util.AddressHierarchyRwandaContextAware;
import org.openmrs.module.htmlformentry.HtmlFormEntryService;
import org.springframework.context.ApplicationContext;

/**
 * This class contains the logic that is run every time this module
 * is either started or shutdown
 */
public class AddressHierarchyActivator implements Activator, Runnable {

	private Log log = LogFactory.getLog(this.getClass());

	//to switch to BaseModuleActivator strategy, just extend BaseModuleActivator implements runnable, and put an @Override on startup()
	
	
	/**
	 * @see org.openmrs.module.Activator#startup()
	 */
	public void startup() {
		log.info("Starting AddressHierarchyRwanda Module");
        Thread contextChecker = new Thread(this);
        contextChecker.start();
	}
	
	/**
	 *  @see org.openmrs.module.Activator#shutdown()
	 */
	public void shutdown() {
		log.info("Shutting down AddressHierarchyRwanda Module");
	}
	
    /**
     * Waits for applicationContext to be loaded and adds the addressHierarchyRwanda tag
     * 
     * the workaround is also used in HTMLFlowsheet module
     * 
     * @see java.lang.Runnable#run()
     */
    public final void run() {
        // Wait for context refresh to finish

        ApplicationContext ac = null;
        HtmlFormEntryService fs = null;
        try {
            while (ac == null || fs == null) {
                Thread.sleep(30000);
                if (AddressHierarchyRwandaContextAware.getApplicationContext() != null){
                    try{
                        log.info("AddressHierachyRwanda still waiting for app context and services to load...");
                        ac = AddressHierarchyRwandaContextAware.getApplicationContext();
                        fs = Context.getService(HtmlFormEntryService.class);
                    } catch (APIException apiEx){
                    	log.error(apiEx);
                    }
                }
            }
        } catch (InterruptedException ex) {
        	log.error(ex);
        }
        try {
            Thread.sleep(10000);
            // Start new OpenMRS session on this thread
            Context.openSession();
            Context.addProxyPrivilege("View Concept Classes");
            Context.addProxyPrivilege("View Concepts");
            Context.addProxyPrivilege("Manage Concepts");
            Context.addProxyPrivilege("View Global Properties");
            Context.addProxyPrivilege("Manage Global Properties");
            Context.addProxyPrivilege("SQL Level Access");
            Context.addProxyPrivilege("View Forms");
            Context.addProxyPrivilege("Manage Forms");
            onLoad(fs);
        } catch (Exception ex) {
            log.error(ex);
            throw new RuntimeException("Could not pre-load concepts " + ex);
        } finally {
            Context.removeProxyPrivilege("SQL Level Access");
            Context.removeProxyPrivilege("View Concept Classes");
            Context.removeProxyPrivilege("View Concepts");
            Context.removeProxyPrivilege("Manage Concepts");
            Context.removeProxyPrivilege("View Global Properties");
            Context.removeProxyPrivilege("Manage Global Properties");
            Context.removeProxyPrivilege("View Forms");
            Context.removeProxyPrivilege("Manage Forms");
            Context.closeSession();
            
            log.info("Finished loading AdressHierachyRwanda metadata.");
        }   
    }
    
    /**
     * Called after module application context has been loaded. There is no authenticated
     * user so all required privileges must be added as proxy privileges
     */
    protected void onLoad(HtmlFormEntryService hfes) { 
        try {
            hfes.addHandler("addressHierarchyRwanda", new AddressHierarchyTagHandler());
        } catch (Exception ex){
            log.error("failed to register AddressHierarchy tag in AddressHierarchyRwanda");
        }
        log.info("registering AddressHierarchy tag...");
    }
}
