package app;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateServer {

    /**
     * @return the current date without the current time
     *
     * Borrowed from LibraryApp, by Hubert Baumeister
     */

    public Calendar getDate() {
        Calendar calendar = new GregorianCalendar();
        Calendar c = new GregorianCalendar(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        return c;
    }
}
