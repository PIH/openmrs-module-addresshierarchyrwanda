package org.openmrs.module.addresshierarchyrwanda;

import java.util.Comparator;

public class AddressRowComparator implements Comparator<Object[]>{

	public int compare(Object[] row0, Object[] row1) {
		if(row0.length<0 || row1.length<0){
			return 0;
		}
		
		if((Integer)row0[0] > (Integer)row1[0])  
			return 1; 
		else if((Integer)row0[0] < (Integer)row1[0])
				return -1;
		else return 0;
		
	}

}
