package org.openmrs.module.addresshierarchyrwanda.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.addresshierarchyrwanda.AddressHierarchy;
import org.openmrs.module.addresshierarchyrwanda.AddressHierarchyService;
import org.openmrs.module.addresshierarchyrwanda.AddressHierarchyType;
import org.openmrs.module.addresshierarchyrwanda.web.FileUploadBean;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.support.StringMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class AddressUploadController extends SimpleFormController{
	 /** Logger for this class and subclasses */
    protected final Log log = LogFactory.getLog(getClass());
    
    @Override
	protected Map<String, Object> referenceData(HttpServletRequest request, Object obj, Errors err) throws Exception {
    	// genders
    	HashMap<String, Object> refMap = new HashMap<String, Object>();
    	AddressHierarchyService ahs = ((AddressHierarchyService)Context.getService(AddressHierarchyService.class));
    	List<AddressHierarchyType> types = ahs.getAddressHierarchyTypes();
    	HashMap<String, String> typeMap = new HashMap<String,String>();
    	
    	for(AddressHierarchyType type:types){
    		typeMap.put(Integer.toString(type.getTypeId()), type.getName());
    	}
    	
    	refMap.put("typeOptions", typeMap);
    	return refMap;
    	
    
    }
    
    
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) 
		throws ServletException, IOException{
		AddressHierarchyService ahs = ((AddressHierarchyService)Context.getService(AddressHierarchyService.class));
		// cast the bean
		FileUploadBean bean = (FileUploadBean) command;
		
		// let's see if there's content there
		String file = bean.getFile();
		String typeIdParam = request.getParameter("typeId");
		int typeId=0;
		if(typeIdParam != null && typeIdParam.trim().length()>0){
			typeId = Integer.parseInt(typeIdParam);
		}
		
		if (file == null) {
			//  the user did not upload anything
		}else if(typeId != 0){
			AddressHierarchy addressHierarchy = null;
			AddressHierarchy parentLocation = null;
			String[] lines = file.split("\\r?\\n");
			String parentIdParam = null;
			String userGeneratedIdParam = null;
			String userGeneratedId = ""; 
			String parentUserGenId = "";
			
			for(String line:lines){
				String[] tokens = line.split(",");
				if(tokens.length==5){
					addressHierarchy = new AddressHierarchy();
					
					// userGeneratedId
					if(tokens[0] != null){
						userGeneratedIdParam = tokens[0].trim();
					}
					if(userGeneratedIdParam != null && userGeneratedIdParam.length() > 0){
						userGeneratedId = userGeneratedIdParam;
						addressHierarchy.setUserGeneratedId(userGeneratedId);
					}
					
					
					// parentId
					if(tokens[1] != null){
						parentIdParam = tokens[1].trim();
					}
					if(parentIdParam != null && !parentIdParam.equals("-1") && parentIdParam.length()>0){
						parentUserGenId = parentIdParam;
						parentLocation = ahs.getLocationFromUserGenId(parentUserGenId);
						if(parentLocation != null){
							addressHierarchy.setParent(ahs.getAddressHierarchy(parentLocation.getAddressHierarchyId()));
						}
						
					}else{
						addressHierarchy.setParent(null);
					}
					
					addressHierarchy.setLocationName(tokens[2].trim());
					
					addressHierarchy.setHierarchyType(ahs.getHierarchyType(typeId));
					ahs.saveAddressHierarchy(addressHierarchy);
				}
			}
		}
		// here lets check the locations for null parents
		// find locations with matching user ids and set parents
		HashMap<String, Object> model = new HashMap<String,Object>();
    	List<AddressHierarchyType> types = ahs.getAddressHierarchyTypes();
    	
    	HashMap<String, String> typeMap = new HashMap<String,String>();
    	
    	for(AddressHierarchyType type:types){
    		typeMap.put(Integer.toString(type.getTypeId()), type.getName());
    		if(type.getParentType() != null){
    			log.info(type.getParentType().getName());
    		}
    	}
    	model.put("typeOptions", typeMap);
    	ModelAndView mav = new ModelAndView(getSuccessView(),model);
//		try {
//			mav = super.onSubmit(request, response, command, errors);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		return mav;
	}
	
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
		throws ServletException {
		
		// to actually be able to convert Multipart instance to a String
		// we have to register a custom editor
		binder.registerCustomEditor(String.class, new StringMultipartFileEditor());
		// now Spring knows how to handle multipart object and convert them
	}

			
}
