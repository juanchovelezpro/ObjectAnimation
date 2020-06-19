package model;

import java.util.Comparator;

public class ComparatorObjectName implements Comparator<Object> {

	@Override
	public int compare(Object o1, Object o2) {

		if (o1.getNameID().compareTo(o2.getNameID()) > 0) {

			return 1;

		} else if(o1.getNameID().compareTo(o2.getNameID()) < 0 ) {

			return -1;

		}else {
			
			return 0;
			
		}

	}

}