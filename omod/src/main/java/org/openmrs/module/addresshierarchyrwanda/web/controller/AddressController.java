package org.openmrs.module.addresshierarchyrwanda.web.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.PersonAddress;
import org.openmrs.api.context.Context;
import org.openmrs.module.addresshierarchyrwanda.web.AddressView;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.AbstractController;


public class AddressController extends AbstractController {
	protected final Log log = LogFactory.getLog(getClass());

	/**
	 * Gets the addresses for the patient specified in the patientId request parameter.  Addresses are handed off to a view.
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		String patientParam = "patientId";
		Set<PersonAddress> addressSet = null;
		Map<String, Set<PersonAddress>> map = new HashMap<String,Set<PersonAddress>>();
		
		try{
			int patientId = Integer.parseInt(request.getParameter(patientParam));
			Patient patient = Context.getPatientService().getPatient(patientId);
			if(patient != null){
				addressSet = patient.getAddresses();
				map.put("1", addressSet);
				View view=  new AddressView();
				mav = new ModelAndView(view, map);
			}
			
		}catch (NumberFormatException nfe){
			nfe.printStackTrace();
		}
		return mav;
	}


}
