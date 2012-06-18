package org.openmrs.module.addresshierarchyrwanda.web.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.addresshierarchyrwanda.AddressHierarchy;
import org.openmrs.module.addresshierarchyrwanda.AddressHierarchyService;
import org.openmrs.module.addresshierarchyrwanda.web.HierarchyView;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.AbstractController;

public class HierarchyController extends AbstractController {
	protected final Log log = LogFactory.getLog(getClass());
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AddressHierarchyService ahs = ((AddressHierarchyService)Context.getService(AddressHierarchyService.class));
		Integer locationId;
		ModelAndView mav = null;
		String locationParam = "locationId";
		List<AddressHierarchy> locationList;
		

		try{
			View view=  null;
			if(request.getParameter(locationParam) == null || request.getParameter(locationParam).trim().length() == 0 || request.getParameter(locationParam).equals("-1")){
				locationList = ahs.getTopOfHierarchyList();
			}else{
				locationId = Integer.parseInt(request.getParameter(locationParam));
				locationList = ahs.getNextComponent(locationId);
			}			
			HashMap<String, List<AddressHierarchy>> map = new HashMap<String,List<AddressHierarchy>>();
			map.put(Integer.toString(1), locationList);
			view = new HierarchyView();
			mav = new ModelAndView(view,map);
			
		}catch (NumberFormatException nfe){
			nfe.printStackTrace();
		}
		return mav;
	}


}
