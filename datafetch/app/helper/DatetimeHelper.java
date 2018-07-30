package helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatetimeHelper {
    public static Date getDateUptoHours(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.clear(); cal.setTime(date);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getDateUptoMinutes(Date date){
        Calendar cal = Calendar.getInstance();
        cal.clear(); cal.setTime(date);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getDateUptoDays(Date date){
        Calendar cal = Calendar.getInstance();
        cal.clear(); cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getDateFromString(String str) {
        try {
            return new SimpleDateFormat("yyyyMMddhhmmss").parse(str);
        }
        catch(ParseException pe){
            return new Date(0);
        }
    }
}
