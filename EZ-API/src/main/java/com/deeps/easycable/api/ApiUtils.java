package com.deeps.easycable.api;

import java.util.Calendar;
import java.util.Date;

public class ApiUtils {
	
	public static Date getFirstDateOfCurrentMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}
}
