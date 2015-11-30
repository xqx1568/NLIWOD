package org.aksw.qa.commons.utils;

import java.util.HashSet;
import java.util.Set;

public class CollectionUtils {

	
	public static <E> Set<E> intersection(Set<E> arg1, Set<E> arg2){
		Set<E> intersect = new HashSet<E>();
		
		for(E entity : arg1){
			if(arg2.contains(entity)){
				intersect.add(entity);
			}
		}
		
		return intersect;
	}
	
}
