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
import org.openmrs.module.addresshierarchyrwanda.AddressHierarchy;
import org.openmrs.module.addresshierarchyrwanda.AddressHierarchyType;
import org.springframework.web.servlet.View;


public class HierarchyView implements View{
	protected final Log log = LogFactory.getLog(getClass());
	
	
	public String getContentType() {
		// TODO Auto-generated method stub
		return "text/html";
	}
	
	/**
	 * 
	 */
	public void render(Map map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Collection<List<AddressHierarchy>> list = map.values();
		List<AddressHierarchy> locationList =null;
		AddressHierarchy ah = null;
		
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		if(list.iterator().hasNext()){
			locationList = list.iterator().next();
			sb.append("\"addresses\":");
			sb.append("[");
			Iterator<AddressHierarchy> locIterator = locationList.iterator();
			while(locIterator.hasNext()){
				sb.append("{");
				ah = locIterator.next();
				
				sb.append("\"id\":");
				String idString = "";
				Integer tempId = -1;
				tempId = ah.getAddressHierarchyId();
				if(tempId!=null && tempId != -1){
					idString = tempId.toString();
					
				}
				sb.append("\""+idString+"\"");
				sb.append(",");
				
				sb.append("\"type\":");
				AddressHierarchyType type = null;
				type = ah.getHierarchyType();
				String typeName = "";
				if(type!=null){
					typeName = type.getName();
				}
				sb.append("\""+typeName+"\"");
				sb.append(",");
				
				String childTypeName = "";
				AddressHierarchyType tempChildType= ah.getHierarchyType().getChildType();
				if(tempChildType != null){
					childTypeName = tempChildType.getName();
				}
				sb.append("\"childType\":");
				sb.append("\""+childTypeName+"\"");
				sb.append(",");
				sb.append("\"display\":");
				sb.append("\""+ah.getLocationName()+"\"");
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
