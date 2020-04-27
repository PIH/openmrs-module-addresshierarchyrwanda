package org.openmrs.module.addresshierarchyrwanda.web.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.openmrs.Location;
import org.openmrs.PersonAddress;
import org.openmrs.api.LocationService;
import org.openmrs.api.context.Context;
import org.openmrs.module.addresshierarchyrwanda.AddressHierarchy;
import org.openmrs.module.addresshierarchyrwanda.AddressHierarchyService;
import org.openmrs.module.addresshierarchyrwanda.AddressHierarchyType;
import org.openmrs.module.addresshierarchyrwanda.AddressValidator;
import org.openmrs.module.addresshierarchyrwanda.web.HierarchyTypeView;
import org.openmrs.module.addresshierarchyrwanda.web.HierarchyView;
import org.openmrs.module.addresshierarchyrwanda.web.IntegerView;
import org.openmrs.module.addresshierarchyrwanda.web.LocationView;
import org.openmrs.module.addresshierarchyrwanda.web.ObjectArrayView;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class AddressHierarchyMultiActionController extends MultiActionController {

	protected final Log log = LogFactory.getLog(getClass());

	public ModelAndView ahValidateAddress(HttpServletRequest request, HttpServletResponse response) {
		PersonAddress pa = new PersonAddress();

		pa.setCountry(ServletRequestUtils.getStringParameter(request, "country", null).trim());
		pa.setStateProvince(ServletRequestUtils.getStringParameter(request, "province", null).trim());
		pa.setCountyDistrict(ServletRequestUtils.getStringParameter(request, "district", null).trim());
		pa.setCityVillage(ServletRequestUtils.getStringParameter(request, "sector", null).trim());
		pa.setAddress3(ServletRequestUtils.getStringParameter(request, "cell", null).trim());
		pa.setAddress1(ServletRequestUtils.getStringParameter(request, "umudugudu", null).trim());
		boolean validity = new AddressValidator().isAddressStructured(pa);

		HashMap<String, Integer> validityMap = new HashMap<String, Integer>();
		if (validity) {
			validityMap.put("1", 1);
		} else {
			validityMap.put("1", 0);
		}
		View view = new IntegerView();

		return new ModelAndView(view, validityMap);
	}

	private List<PersonAddress> convertToPersonAddresses(List<Object[]> rows) {
		List<PersonAddress> pas = new ArrayList<PersonAddress>();

		PersonAddress pa = null;

		for (Object[] row : rows) {

			pa = convertToPersonAddress(row);

			if (pa != null) {
				pas.add(pa);
			}
		}

		return pas;
	}

	private PersonAddress convertToPersonAddress(Object[] row) {
		PersonAddress pa = new PersonAddress();

		pa.setCountry((String) row[1]);
		pa.setStateProvince((String) row[2]);
		pa.setCountyDistrict((String) row[3]);
		pa.setCityVillage((String) row[4]);
		pa.setAddress3((String) row[5]);
		pa.setAddress1((String) row[6]);

		return pa;
	}

	// public ModelAndView ahFindUnstructuredAddresses(HttpServletRequest
	// request, HttpServletResponse response) {
	// AddressHierarchyService ahs = ((AddressHierarchyService)
	// Context.getService(AddressHierarchyService.class));
	// int page = ServletRequestUtils.getIntParameter(request, "page", -1);
	//
	// List<Object[]> badAddressRows = ahs.getAllAddresses(page);
	//
	//
	// AddressValidator av = new AddressValidator();
	//
	//
	// PersonAddress pa = null;
	// Object[] viewRow = null;
	// List<Object[]> returnRows = new ArrayList<Object[]>();
	//
	// Set<Integer> patientIdSet = new HashSet<Integer>();
	// PatientService patientService = Context.getService(PatientService.class);
	// for (Object[] row : badAddressRows) {
	// pa = convertToPersonAddress(row);
	// String reason = null;
	// if ((reason = av.getInvalidReason(pa)) != null) {
	//
	// if (!patientIdSet.contains((Integer) row[0])) {
	// PatientIdentifier pi = patientService.getPatient((Integer)
	// row[0]).getPatientIdentifier();
	// viewRow = new Object[] { row[0], reason, pi.getIdentifier(),
	// pi.getLocation()};
	// returnRows.add(viewRow);
	//
	// patientIdSet.add((Integer) row[0]);
	// }
	// }
	//
	// }
	// HashMap<Integer, List<Object[]>> badAddressIdMap = new HashMap<Integer,
	// List<Object[]>>();
	//
	//
	// badAddressIdMap.put(1, returnRows);
	// View objectArrayView = new ObjectArrayView();
	//
	// return new ModelAndView(objectArrayView, badAddressIdMap);
	// }

	public ModelAndView ahGetAddressBreakdown(HttpServletRequest request, HttpServletResponse response) {
		
		int locationId = ServletRequestUtils.getIntParameter(request, "locId", 1);
		
		AddressHierarchyService ahs = ((AddressHierarchyService) Context.getService(AddressHierarchyService.class));
		List<Object[]> rows = ahs.getLocationAddressBreakdown(locationId);
		
		ObjectArrayView oav = new ObjectArrayView();
		
		HashMap<String, List<Object[]>> addressStats = new HashMap<String, List<Object[]>>();
		addressStats.put("1", rows);
		
		return new ModelAndView(oav,addressStats);
		
	}
	

	public ModelAndView ahFindUnstructuredAddresses(HttpServletRequest request, HttpServletResponse response) {

		AddressHierarchyService ahs = ((AddressHierarchyService) Context.getService(AddressHierarchyService.class));
		int page = ServletRequestUtils.getIntParameter(request, "page", -1);
		int locId = ServletRequestUtils.getIntParameter(request, "locId", 1);

		List<Object[]> badAddressRows = ahs.findUnstructuredAddresses(page, locId);

		HashMap<String, List<Object[]>> badAddressIdMap = new HashMap<String, List<Object[]>>();
		badAddressIdMap.put("1", badAddressRows);
		View objectArrayView = new ObjectArrayView();

		return new ModelAndView(objectArrayView, badAddressIdMap);
	}

	public void ahAssociateCoordinateToLeafNode(HttpServletRequest request, HttpServletResponse response) {

		// request parameters
		double latitude = ServletRequestUtils.getDoubleParameter(request, "latitude", -1);
		double longitude = ServletRequestUtils.getDoubleParameter(request, "longitude", -1);
		int ahId = ServletRequestUtils.getIntParameter(request, "ahId", -1);

		// validate params
		boolean isValid = false;
		if (latitude != -1 && longitude != -1 && ahId != -1) {
			isValid = true;
		}

		// do assocatian if valid
		if (isValid) {
			AddressHierarchyService ahs = ((AddressHierarchyService) Context.getService(AddressHierarchyService.class));
			AddressHierarchy ah = ahs.getAddressHierarchy(ahId);
			ahs.associateCoordinatesToLeafNode(ah, latitude, longitude);
		}
	}

	// ============= is this used ? hope not ================== //
	public ModelAndView ahGetTypeNames(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView();
	}

	public ModelAndView ahDeleteTables(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		AddressHierarchyService ahs = ((AddressHierarchyService) Context.getService(AddressHierarchyService.class));
		ahs.truncateHierarchyTables();
		ahs.initializeHierarchyTables();
		View view = new HierarchyView();
		mav.setView(view);
		Map<String, List<AddressHierarchy>> meModel = new HashMap<String, List<AddressHierarchy>>();
		mav.addAllObjects(meModel);

		return mav;
	}

	private void loadFile(String path, int type) {
		String line = null;
		AddressHierarchyService ahs = ((AddressHierarchyService) Context.getService(AddressHierarchyService.class));
		AddressHierarchy addressHierarchy;
		AddressHierarchy parentLocation;
		String userGeneratedIdParam = null;
		String userGeneratedId;
		String parentIdParam = null;
		String parentUserGenId;
		double latitude = 0;
		double longitude = 0;
		double elevation = 0;

		try {
			InputStream is1 = getServletContext().getResourceAsStream(path);
			InputStreamReader isr1 = new InputStreamReader(is1);
			BufferedReader br = new BufferedReader(isr1);

			while ((line = br.readLine()) != null) {

				String[] tokens = line.split(",");
				if (tokens.length >= 3) {
					addressHierarchy = new AddressHierarchy();

					// userGeneratedId
					if (tokens[0] != null) {
						userGeneratedIdParam = tokens[0].trim();
					}
					if (userGeneratedIdParam != null && userGeneratedIdParam.length() > 0) {
						userGeneratedId = userGeneratedIdParam;
						addressHierarchy.setUserGeneratedId(userGeneratedId);
					}

					// parentId
					if (tokens[1] != null) {
						parentIdParam = tokens[1].trim();
					}
					if (parentIdParam != null && !parentIdParam.equals("-1") && parentIdParam.length() > 0) {
						parentUserGenId = parentIdParam;
						parentLocation = ahs.getLocationFromUserGenId(parentUserGenId);
						if (parentLocation != null) {
							addressHierarchy.setParent(ahs.getAddressHierarchy(parentLocation.getAddressHierarchyId()));
						}

					} else {
						addressHierarchy.setParent(null);
					}

					addressHierarchy.setLocationName(tokens[2].trim());
					// optional spatial information
					if (tokens.length == 6) {
						// latitude
						if (tokens[3] != null && tokens[3].trim().length() > 0) {
							try {
								latitude = Double.parseDouble(tokens[3].trim());
								addressHierarchy.setLatitude(latitude);
							} catch (NumberFormatException nfe) {
								log.error(nfe);
							}
						}

						// longitude
						if (tokens[4] != null && tokens[4].trim().length() > 0) {
							try {
								longitude = Double.parseDouble(tokens[4].trim());
								addressHierarchy.setLongitude(longitude);
							} catch (NumberFormatException nfe) {
								log.error(nfe);
							}
						}

						// elevation
						if (tokens[5] != null && tokens[5].trim().length() > 0) {
							try {
								elevation = Double.parseDouble(tokens[5].trim());
								addressHierarchy.setElevation(elevation);
							} catch (NumberFormatException nfe) {
								log.error(nfe);
							}
						}
					}

					addressHierarchy.setHierarchyType(ahs.getHierarchyType(type));
					ahs.saveAddressHierarchy(addressHierarchy);

				}
			}
		} catch (ConstraintViolationException cve) {
			StringBuffer sb = new StringBuffer();
			sb.append("-------- constraint violation ---------------");
			sb.append("just tried to insert " + line);
			log.error(sb);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (IllegalStateException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Load the system with the included files
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView ahInitializeSystem(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		View view = new HierarchyView();
		mav.setView(view);
		Map<String, List<AddressHierarchy>> meModel = new HashMap<String, List<AddressHierarchy>>();
		mav.addAllObjects(meModel);
		
		AddressHierarchyService ahs = ((AddressHierarchyService) Context.getService(AddressHierarchyService.class));
		
		int country = 0;
		int province = 0;
		int district = 0;
		int sector = 0;
		int cell = 0;
		int village = 0;
		
		for (AddressHierarchyType aht : ahs.getAddressHierarchyTypes()){
			if (aht.getName().equals("Country"))
				country = aht.getTypeId();
			else if (aht.getName().equals("Province"))
				province = aht.getTypeId();
			else if (aht.getName().equals("District"))
				district = aht.getTypeId();
			else if (aht.getName().equals("Sector"))
				sector = aht.getTypeId();
			else if (aht.getName().equals("Cell"))
				cell = aht.getTypeId();
			else if (aht.getName().equals("Umudugudu"))
				village = aht.getTypeId();
				
		}
		
		loadFile("/WEB-INF/view/module/addresshierarchyrwanda/resources/Country.txt", country);
		loadFile("/WEB-INF/view/module/addresshierarchyrwanda/resources/Province.txt", province);
		loadFile("/WEB-INF/view/module/addresshierarchyrwanda/resources/District.txt", district);
		loadFile("/WEB-INF/view/module/addresshierarchyrwanda/resources/Sector.txt", sector);
		loadFile("/WEB-INF/view/module/addresshierarchyrwanda/resources/Cell.txt", cell);
		loadFile("/WEB-INF/view/module/addresshierarchyrwanda/resources/Village.txt", village);

		return mav;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView ahGetAddressHierarchyCount(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		AddressHierarchyService ahs = ((AddressHierarchyService) Context.getService(AddressHierarchyService.class));
		HashMap<String, Integer> model = new HashMap<String, Integer>();
		View view = new IntegerView();
		int count = ahs.getAddressHierarchyCount();
		model.put("sd", count);
		mav.setView(view);
		mav.addAllObjects(model);

		return mav;
	}

	/**
	 * Searches the address hierarchy table for locations by name
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView ahsearch(HttpServletRequest request, HttpServletResponse response) {
		String searchString = request.getParameter("searchString");
		String typeIdParameter = request.getParameter("typeId");
		int typeId = -1;
		try {
			typeId = Integer.parseInt(typeIdParameter);
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		AddressHierarchyService ahs = ((AddressHierarchyService) Context.getService(AddressHierarchyService.class));
		List<AddressHierarchy> ahList = ahs.searchHierarchy(searchString, typeId);
		View hierarchyView = new HierarchyView();
		Map<String, List<AddressHierarchy>> meModel = new HashMap<String, List<AddressHierarchy>>();
		if (ahList != null && ahList.size() > 0) {
			int i = 0;
			meModel.put(String.valueOf(i), ahList);
		}
		return new ModelAndView(hierarchyView, meModel);
	}

	/**
	 * Edit a the name of a location in the Hierarchy
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView ahEditLocationName(HttpServletRequest request, HttpServletResponse response) {
		log.info("entering edit method");
		View view = new HierarchyView();
		ModelAndView mav = null;
		Map<String, List<AddressHierarchy>> meModel = new HashMap<String, List<AddressHierarchy>>();
		AddressHierarchy ah = null;
		try {
			int locationId = Integer.parseInt(request.getParameter("locationId"));
			String newName = request.getParameter("newName");
			log.info("locationId is");
			AddressHierarchyService ahs = ((AddressHierarchyService) Context.getService(AddressHierarchyService.class));
			ah = ahs.editLocationName(locationId, newName);
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		if (ah != null) {
			log.info("adding an ah to the list");
			List<AddressHierarchy> ahList = new ArrayList<AddressHierarchy>();
			ahList.add(ah);
			meModel.put("1", ahList);
		}

		mav = new ModelAndView(view, meModel);

		return mav;

	}

	public ModelAndView ahAddChild(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		View hierarchyView = new HierarchyView();
		mav.setView(hierarchyView);

		String parentIdParam = request.getParameter("parentId");
		String locationNameParam = request.getParameter("locationName");
		AddressHierarchyService ahs = ((AddressHierarchyService) Context.getService(AddressHierarchyService.class));
		List<AddressHierarchy> hierarchyList = new ArrayList<AddressHierarchy>();
		AddressHierarchy ah = null;
		try {
			int parentId = Integer.parseInt(parentIdParam);
			ah = ahs.addLocation(parentId, locationNameParam, -1);
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		hierarchyList.add(ah);
		HashMap<String, List<AddressHierarchy>> model = new HashMap<String, List<AddressHierarchy>>();
		model.put("1", hierarchyList);
		mav.addAllObjects(model);

		return mav;
	}

	/**
	 * Gets all the children of the location identified by the
	 * <code>parentId</code> parameter
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView ahGetChildren(HttpServletRequest request, HttpServletResponse response) {
		String parentIdParameter = request.getParameter("parentId");
		int parentId = -1;
		ModelAndView mav = new ModelAndView();
		mav.setView(new HierarchyView());
		HashMap<String, List<AddressHierarchy>> model = new HashMap<String, List<AddressHierarchy>>();
		try {
			parentId = Integer.parseInt(parentIdParameter);
			AddressHierarchyService ahs = ((AddressHierarchyService) Context.getService(AddressHierarchyService.class));
			List<AddressHierarchy> childList = ahs.getNextComponent(parentId);
			model.put("1", childList);
			mav.addAllObjects(model);

		} catch (RuntimeException re) {
			re.printStackTrace();
		}

		return mav;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView ahGetLocationTypes(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setView(new HierarchyTypeView());
		HashMap<String, List<AddressHierarchyType>> model = new HashMap<String, List<AddressHierarchyType>>();
		AddressHierarchyService ahs = ((AddressHierarchyService) Context.getService(AddressHierarchyService.class));

		List<AddressHierarchyType> types = ahs.getAddressHierarchyTypes();
		model.put("1", types);
		mav.addAllObjects(model);

		return mav;
	}

	public ModelAndView ahGetOMRSLocations(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setView(new LocationView());
		HashMap<String, List<Location>> model = new HashMap<String, List<Location>>();
		LocationService locService = ((LocationService) Context.getService(LocationService.class));
		List<Location> locs = locService.getAllLocations(false);

		model.put("1", locs);
		mav.addAllObjects(model);

		return mav;
	}

}
