package Task_Management_service.util;

import lombok.experimental.UtilityClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@UtilityClass
public class DateFormatter {
    public static Date formatDateToNewDate(Date date, SimpleDateFormat formatter) throws ParseException {
        String formattedDateString = formatter.format(date);
        return formatter.parse(formattedDateString);
    }
}