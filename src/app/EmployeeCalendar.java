package app;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that holds employee absent times.
 *
 * By Sille Jessing s184219
 *
 */

public class EmployeeCalendar {
    List<Integer> absentWeeks;

    public EmployeeCalendar() {
        this.absentWeeks = new ArrayList<>();
    }

    public void addAbsent(int week) throws Exception {
        if (1 <= week && week <= 52) {
            if (containWeek(week)) {
                throw new Exception("Week already absent");
            } else {
                absentWeeks.add(week);
            }
        } else {
            throw new Exception("Not a valid week");
        }
    }

    public boolean containWeek(int week) {
        for (Integer absentWeek : absentWeeks) {
            if (week == absentWeek) {
                return true;
            }
        }
        return false;
    }
}
