/**
 * <p> File: org.openmrs.module.jas.util.EnglishNumber.java </p>
 * <p> Project: jas-api </p>
 * <p> Copyright (c) 2011 HISP Technologies. </p>
 * <p> All rights reserved. </p>
 * <p> Author: Nguyen manh chuyen </p>
 * <p> Email: chuyennmth@gmail.com</p>
 * <p> Update by: Nguyen manh chuyen </p>
 * <p> Version: $1.0 </p>
 * <p> Create date: Jun 16, 2011 12:48:35 PM </p>
 * <p> Update date: Jun 16, 2011 12:48:35 PM </p>
 **/

package org.openmrs.module.jas.util;

/**
 * <p> Class: EnglishNumber </p>
 * <p> Package: org.openmrs.module.jas.util </p> 
 * <p> Author: Nguyen manh chuyen </p>
 * <p> Update by: Nguyen manh chuyen </p>
 * <p> Version: $1.0 </p>
 * <p> Create date: Jun 16, 2011 12:48:35 PM </p>
 * <p> Update date: Jun 16, 2011 12:48:35 PM </p>
 **/
public class EnglishNumber {
	private static final String[] ones = {
		" one", " two", " three", " four", " five",
		" six", " seven", " eight", " nine", " ten",
		" eleven", " twelve", " thirteen", " fourteen",
		" fifteen", " sixteen", " seventeen",
		" eighteen", " nineteen"
		}; private static final String[] tens = {
		" twenty", " thirty", " forty", " fifty",
		" sixty", " seventy", " eighty", " ninety"
		}; // // so quintillions is as big as it gets. the
		// program would automatically handle larger
		// numbers if this array were extended.
		//
		private static final String[] groups = {
		"",
		" thousand",
		" million",
		" billion",
		" trillion",
		" quadrillion",
		" quintillion"
		};
		private String string = new String();
		public String getString() { 
			return string; 
		
		}
		public EnglishNumber ( long n ) {
		// go through the number one group at a time.
		for (int i = groups.length-1; i >= 0; i--) {
		// is the number as big as this group?
		long cutoff =
		(long)Math.pow((double)10, (double)(i*3)); if ( n >= cutoff ) {
		int thispart = (int)(n / cutoff);
		// use the ones[] array for both the
		// hundreds and the ones digit. note // that tens[] starts at "twenty". if (thispart >= 100) { String += ones[thispart/100] + " hundred"; thispart = thispart % 100; }

		if (thispart >= 20) {
			string += tens[(thispart/10)-1];
			thispart = thispart % 10;
			}if (thispart >= 1) { string += ones[thispart]; }string += groups[i]; n = n % cutoff; } }if (string.length() == 0) { string = "zero"; } else {
			// remove initial space
			string = string.substring(1);
			}
		}
		public static void main(String[] args) {
			EnglishNumber number = new EnglishNumber(2);
			System.out.println(number.getString());
		}
}
