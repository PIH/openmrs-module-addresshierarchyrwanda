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
import org.openmrs.PersonAddress;
import org.openmrs.module.addresshierarchyrwanda.AddressValidator;
import org.springframework.web.servlet.View;


public class AddressView implements View{
	protected final Log log = LogFactory.getLog(getClass());
	
	
	public String getContentType() {
		// TODO Auto-generated method stub
		return "text/html";
	}
	
	/**
	 * 
	 * 
	{
    "results": 2,
    "rows": [
        { "id": 1, "firstname": "Bill", occupation: "Gardener" },         // a row object
        { "id": 2, "firstname": "Ben" , occupation: "Horticulturalist" }  // another row object
    ]
}
	 */

	public void render(Map map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Collection<Set<PersonAddress>> addressList = map.values();
		PersonAddress pa =null;
		AddressValidator av = new AddressValidator();
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		Iterator<Set<PersonAddress>> setIterator= addressList.iterator();
		Iterator<PersonAddress> addressIterator = null;
		if(setIterator.hasNext()){
			addressIterator = setIterator.next().iterator();
		}
		sb.append("\"addresses\":");
		sb.append("[");
		while(addressIterator != null && addressIterator.hasNext()){
			sb.append("{");
			pa = addressIterator.next();
			if(pa != null){
				
				sb.append("\"structured\":");
				sb.append("\""+av.isAddressStructured(pa)+"\"");
				sb.append(",");
				
				sb.append("\"country\":");
				sb.append("\""+pa.getCountry()+"\"");
				sb.append(",");
				
				sb.append("\"stateProvince\":");
				sb.append("\""+pa.getStateProvince()+"\"");
				sb.append(",");
				
				sb.append("\"countyDistrict\":");
				sb.append("\""+pa.getCountyDistrict()+"\"");
				sb.append(",");
				
				sb.append("\"cityVillage\":");
				sb.append("\""+pa.getCityVillage()+"\"");
				sb.append(",");
				
				sb.append("\"neighborhoodCell\":");
				sb.append("\""+pa.getNeighborhoodCell()+"\"");
				sb.append(",");
				
				sb.append("\"address1\":");
				sb.append("\""+pa.getAddress1()+"\"");
				sb.append("");
				
				sb.append("}");
				if(addressIterator.hasNext()){
					sb.append(",");
				}
			}
		}
			sb.append("]");
		
		sb.append("}");
		OutputStreamWriter osw = new OutputStreamWriter(response.getOutputStream());
		osw.write(sb.toString());
		osw.flush();
	}
}
