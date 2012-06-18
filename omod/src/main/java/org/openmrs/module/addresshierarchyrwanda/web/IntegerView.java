package org.openmrs.module.addresshierarchyrwanda.web;

import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.View;

public class IntegerView implements View {
	protected final Log log = LogFactory.getLog(getClass());

	public String getContentType() {
		return "text/html";
	}
	
	/**
	 * 
	 */
	public void render(Map map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Collection<Integer> list = map.values();

		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"values\":");
		sb.append("[");
		int  lastPass = list.size()-1;
		int count = 0;
		for(Integer i:list) {
			sb.append("{");
			sb.append("\"value\":");
			sb.append("\"" + i + "\"");
			sb.append("}");
			if (lastPass > count)
				sb.append(",");
			count++;
		}
		sb.append("]");
		sb.append("}");
		OutputStreamWriter osw = new OutputStreamWriter(response.getOutputStream());
		osw.write(sb.toString());
		osw.flush();
	}
}
