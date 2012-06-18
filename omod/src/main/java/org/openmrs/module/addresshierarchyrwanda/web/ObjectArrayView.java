package org.openmrs.module.addresshierarchyrwanda.web;

import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.PersonAddress;
import org.openmrs.module.addresshierarchyrwanda.AddressValidator;
import org.springframework.web.servlet.View;


public class ObjectArrayView implements View{
	protected final Log log = LogFactory.getLog(getClass());
	
	
	public String getContentType() {
		// TODO Auto-generated method stub
		return "text/html";
	}
	
	

	public void render(Map map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Object[]> rows = (List<Object[]>) map.get(1);
		Iterator<Object[]> rowIterator = rows.iterator();
		StringBuffer sb = new StringBuffer();
		Object[] row = null;
		int columnIndex = 0;
		
		sb.append("{\"rows\":");
		sb.append("[");
		while(rowIterator != null && rowIterator.hasNext()){
			sb.append("{");
			row = rowIterator.next();
			if(row != null){
				columnIndex = 0;
				for(Object element:row){
					
					sb.append("\"columnIndex_"+columnIndex++ +"\":");
					sb.append("\"");
					if(element != null){
						sb.append(element);
					}
					sb.append("\"");
					sb.append(",");
				}
			}	
			sb.append("}");
			if(rowIterator.hasNext()){
				sb.append(",");
			}
		}
		sb.append("]");
		sb.append("}");
		OutputStreamWriter osw = new OutputStreamWriter(response.getOutputStream());
		osw.write(sb.toString());
		osw.flush();
	}
}
