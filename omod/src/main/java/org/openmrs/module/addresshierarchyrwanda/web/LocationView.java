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
import org.openmrs.Location;
import org.springframework.web.servlet.View;


public class LocationView implements View{
	protected final Log log = LogFactory.getLog(getClass());
	
	
	public String getContentType() {
		// TODO Auto-generated method stub
		return "text/html";
	}
	
	/**
	 * Renders a JSON view of a list of Locations
	 */
	public void render(Map map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Collection<List<Location>> list = map.values();
		List<Location> locationList =null;
		Location location = null;
		
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		if(list.iterator().hasNext()){
			locationList = list.iterator().next();
			sb.append("\"locs\":");
			sb.append("[");
			Iterator<Location> locIterator = locationList.iterator();
			while(locIterator.hasNext()){
				sb.append("{");
				location= locIterator.next();
				sb.append("\"id\":");
				sb.append("\""+location.getLocationId()+"\"");
				sb.append(",");
				sb.append("\"name\":");
				sb.append("\""+location.getName()+"\"");
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
