package spring.orm.util;

import java.util.List;

public class UtilManager {

	public static String converWeekdays(List<String> weekdays) {
		/*
		 * This method takes a list of weekdays as input and converts them into a string
		 * representation.
		 */
		if (weekdays.size() == 1) {
			// If there is only one weekday in the list, return that weekday.
			return weekdays.get(0);
		} else {
			StringBuilder sb = new StringBuilder();
			// Create a StringBuilder to build the string representation of the weekdays.

			for (String day : weekdays) {
				// Iterate through each weekday in the list.

				switch (day.toUpperCase()) {
				case "MON":
					sb.append("M");
					break;
				case "TUE":
					sb.append("T");
					break;
				case "WED":
					sb.append("W");
					break;
				case "THU":
					sb.append("H");
					break;
				case "FRI":
					sb.append("F");
					break;
				case "SAT":
					sb.append("S");
					break;
				case "SUN":
					sb.append("U");
					break;
				
				}
			}

			

			return sb.toString();
			// Return the final string representation of the weekdays.
		}
	}
}