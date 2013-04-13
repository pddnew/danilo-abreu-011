package com.classsocialnet.util;

import java.util.List;

public class ListUtils {

	public static String toString(List<? extends Object> list){
		
		StringBuilder sb = new StringBuilder();
		
		if(list.isEmpty())
			sb.append("No Results.");
		else
			sb.append("List : \n\n");
		
		for(int i = 0; i < list.size(); i++){
			Object o = list.get(i);
			if(i%2==0)
				sb.append("   \t   *   \t   " + o.toString() +"   \t   ");
			else
				sb.append("   \t   *   \t   " + o.toString() +"\n");
		}
			
		
		return sb.toString();
	}
}
