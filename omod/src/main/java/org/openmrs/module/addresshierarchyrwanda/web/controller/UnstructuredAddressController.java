package org.openmrs.module.addresshierarchyrwanda.web.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Person;
import org.openmrs.PersonAddress;
import org.openmrs.api.PersonService;
import org.openmrs.api.context.Context;
import org.openmrs.module.addresshierarchyrwanda.AddressHierarchyService;
import org.openmrs.module.addresshierarchyrwanda.AddressValidator;
import org.openmrs.module.addresshierarchyrwanda.UnstructuredAddress;
import org.openmrs.module.addresshierarchyrwanda.web.UnstructuredAddressView;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class UnstructuredAddressController extends AbstractController {

	protected final Log log = LogFactory.getLog(getClass());
	
	/**
	 * Searches all existing address, compares them with entries in the hierarchy,
	 * returns a list of all deemed unstructured. Compares:
			country
			stateProvince
			countyDistrict
			cityVillage
			address3
			address1
		if you have a country that matches one in the db then all following must match
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse arg1) throws Exception {
		String pageParameter = request.getParameter("page");
		AddressHierarchyService ahs = ((AddressHierarchyService)Context.getService(AddressHierarchyService.class));
		UnstructuredAddress ua = null;
		AddressValidator av = new AddressValidator();
		try{
			PersonService personService = Context.getPersonService();
			if(pageParameter!=null && pageParameter.length()>0){
				int page = Integer.parseInt(pageParameter);
				int startIndex = page*25 - 25;
				for(int i = startIndex; i<25; i++){
					Person person = personService.getPerson(i);
					if(person != null){
						PersonAddress personAddress = person.getPersonAddress();
						if(personAddress != null){
							if(av.isAddressStructured(personAddress)){
								log.info("structured! : " + personAddress);
							}
							else{
								log.info("unstructured! : " + personAddress);
								ua = new UnstructuredAddress();
								ua.setPerson(person);
								ua.setPersonAddress(personAddress);
								ahs.saveUnstructuredAddress(ua);
							}
						}
					}
				}
			}
		} catch (NumberFormatException nfe){
			nfe.printStackTrace();
		}
		
		ModelAndView mav = new ModelAndView(new UnstructuredAddressView(),new HashMap<String,Object>());
		
		return mav;
	}
	
}
