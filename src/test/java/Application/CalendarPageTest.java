import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import Application.Controllers.calendarPageController;
import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CalendarPageTest{
    private LocalDate sampleDate= LocalDate.parse("2025-04-16");

    @Test
    public void testMissingEventName(){
        assertEquals("Necessary fields missing"
                ,calendarPageController.calenderPageFunctions.checkEventInputs("","Study period",sampleDate,sampleDate,"N201"));
    }
    @Test
    public void testMissingEventType(){
        assertEquals("Necessary fields missing"
                ,calendarPageController.calenderPageFunctions.checkEventInputs("Software development study",""
                        ,sampleDate,sampleDate,"N201"));
    }
    @Test
    public void testMissingEventDates(){
        assertEquals("Necessary fields missing"
                ,calendarPageController.calenderPageFunctions.checkEventInputs("Software development study","Study period"
                        ,null,null,"N201"));
    }
    @Test
    public void testMissingEventLocation(){//should be allowed to succeed without location
        assertEquals(null,calendarPageController.calenderPageFunctions.checkEventInputs("Software development study","Study period"
                ,sampleDate,sampleDate,""));
    }
    @Test
    public void testNoFieldsMissing(){
        assertEquals(null,calendarPageController.calenderPageFunctions.checkEventInputs("Software development study","Study period"
                ,sampleDate,sampleDate,"N201"));
    }
    @Test
    public void testFormatDateLongMinuteAndDate(){
        assertEquals("2025-04-16 12:30:00",calendarPageController.calenderPageFunctions.formatDatetime(sampleDate,12,30));
    }
    @Test
    public void testFormatDateShortMinuteAndDate(){
        assertEquals("2025-04-16 06:05:00",calendarPageController.calenderPageFunctions.formatDatetime(sampleDate,6,5));
    }
}