package org.openmrs.module.addresshierarchyrwanda.web;

import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.addresshierarchyrwanda.AddressHierarchyType;
import org.springframework.web.servlet.View;


public class HierarchyTypeView implements View{
	protected final Log log = LogFactory.getLog(getClass());
	
	
	public String getContentType() {
		// TODO Auto-generated method stub
		return "text/html";
	}
	
	/**
	 * Renders a JSON view of a list of HierarchyTypes
	 */
	public void render(Map map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Collection<List<AddressHierarchyType>> list = map.values();
		List<AddressHierarchyType> locationList =null;
		AddressHierarchyType hierarchyType = null;
		
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		if(list.iterator().hasNext()){
			locationList = list.iterator().next();
			sb.append("\"types\":");
			sb.append("[");
			Iterator<AddressHierarchyType> locIterator = locationList.iterator();
			while(locIterator.hasNext()){
				sb.append("{");
				hierarchyType = locIterator.next();
				sb.append("\"id\":");
				sb.append("\""+hierarchyType.getTypeId()+"\"");
				sb.append(",");
				sb.append("\"name\":");
				sb.append("\""+hierarchyType.getName()+"\"");
				sb.append("}");
				if(locIterator.hasNext()){
					sb.append(",");
				}
			}
			sb.append("]");
		}
		sb.append("}");
		OutputStreamWriter osw = new OutputStreamWriter(response.getOutputStream());
		osw.write(sb.toString());
		osw.flush();
	}
}
