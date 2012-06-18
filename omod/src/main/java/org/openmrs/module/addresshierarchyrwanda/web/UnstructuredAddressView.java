package org.openmrs.module.addresshierarchyrwanda.web;

import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.View;


public class UnstructuredAddressView implements View{
	protected final Log log = LogFactory.getLog(getClass());
	
	
	public String getContentType() {
		// TODO Auto-generated method stub
		return "text/html";
	}
	
	

	public void render(Map map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		StringBuffer sb = new StringBuffer();		
		sb.append("unstructured view");
		OutputStreamWriter osw = new OutputStreamWriter(response.getOutputStream());
		osw.write(sb.toString());
		osw.flush();
	}
}
