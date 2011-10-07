/**
 *  Copyright 2011 Health Information Systems Project of India
 *
 *  This file is part of Jas module.
 *
 *  Jas module is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  Jas module is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Jas module.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/

package org.openmrs.module.jas.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.math.NumberUtils;

public class StringNumberUtils {
	private static final int[] MIN_MAX = {1, 10000}; 
	public static Integer[] convertStringArraytoIntArray(String[] sarray) throws Exception {
			if (sarray != null && sarray.length > 0)
			{
				Integer intarray[] = new Integer[sarray.length];
				for (int i = 0; i < sarray.length; i++) {
					intarray[i] = NumberUtils.toInt(sarray[i]);
				}
				return intarray;
			}
			return null;
			}
	
	public static <T> List<T> convertArrayToList(T[] array){
		if(array == null || array.length == 0 )
			return null;
		List<T> list = new ArrayList<T>();
		for(T element : array){
			list.add(element);
		}
		return list;
		}

	
	public static  Collection difference(Collection a, Collection b) {
		if(b == null){
			return a;
		}
		if(a == null){
			return null;
		}
		return ListUtils.removeAll(a, b);
		}
	public static int getRandom()
	{
		Random rand = new Random();
		return rand.nextInt((MIN_MAX[1]+1) - MIN_MAX[0]) + MIN_MAX[0];
	}
	public static void main(String[] args) {
		/*List a = new ArrayList();
		a.add("1");
		a.add("2");
		a.add("3");
		a.add("4");
		a.add("5");
		
		List b =null;
	
		System.out.println(difference(a,b));
		System.out.println("a: "+a);
		System.out.println(b);*/

		for(int i=0; i<1000;i++){
			System.out.println(getRandom());
		}
		//String[] ab =new String[]{"1","2","3"};
		//List<Integer> inte = convertArrayToList(ab);
		//System.out.println(inte);
		
	}
}
