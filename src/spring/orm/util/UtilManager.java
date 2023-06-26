package spring.orm.util;

import java.util.List;

public class UtilManager {

	public static String converWeekdays(List<String> weekdays) {
        if (weekdays.size() == 1) {
            return weekdays.get(0);
        } else {
            StringBuilder sb = new StringBuilder();
            for (String day : weekdays) {
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
                    default:
                        System.out.println("Invalid weekday: " + day);
                        break;
                }
            }
            System.out.println(sb);
            return sb.toString();
        }
    }
}
