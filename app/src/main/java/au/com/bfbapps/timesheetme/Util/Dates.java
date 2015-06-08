package au.com.bfbapps.timesheetme.Util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Dates {
	public static final String DATE_FORMAT = "dd/MM/yyyy";
	public static final String TIME_FORMAT = "hh:mm a";

	/**
	 * Convert String date into Java Date
	 * @param date date to convert
	 * @return string format of date in dd/mm/yyyy
	 */
	public static Date ConvertStringToDate(String date){
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

		try {
			return formatter.parse(date);
		} catch (ParseException e) {
			return new Date();
		}
	}

	/**
	 * Convert Java Date into String Date for DB Storage
	 * @param date date to convert
	 * @return
	 */
	public static String ConvertDateToString(Date date){
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		return formatter.format(date);
	}


	/**
	 * Convert Java Date to String for UI Use
	 * @param time date to convert
	 * @return time in string format
	 */
	public static String ConvertTimeToString(Date time){
		SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT);
		return formatter.format(time);
	}

	public static Date ConvertTimeToDate(String time){
		SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT);
		try {
			return formatter.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
			Log.d("DATE", "UNABLE TO PARSE");
		}
		return null;
	}

	/**
	 * Add an amount of dates to a date
	 * @param date start date
	 * @param daysToAdd amount of dates to add (can be negative)
	 * @return new Date
	 */
	public static Date AddDaysToDate(Date date, int daysToAdd){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, daysToAdd);
		return c.getTime();
	}

	public static String GetDayNameFromDate(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		switch (c.get(Calendar.DAY_OF_WEEK)){
			case 1:
				return "Sunday";
			case 2:
				return "Monday";
			case 3:
				return "Tuesday";
			case 4:
				return "Wednesday";
			case 5:
				return "Thursday";
			case 6:
				return "Friday";
			case 7:
				return "Saturday";
		}
		return "";
	}
}
