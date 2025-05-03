import Application.Database.DatabaseConnection;
import Application.Database.UserSignupDAO;
import Application.Database.UserTimetable;
import Application.Database.UserTimetableDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import Application.Controllers.calendarPageController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CalendarPageTest{
    private LocalDate sampleDate= LocalDate.parse("2025-04-16");

    @Test
    public void missingFields(){
        assertEquals("Necessary fields missing",calendarPageController.checkEventInputs("","",null,null,""));
    }

    @Test
    public void testMissingEventName(){
        assertEquals("Necessary fields missing"
                ,calendarPageController.checkEventInputs("","Study period",sampleDate,sampleDate,"N201"));}
    @Test
    public void testMissingEventType(){
        assertEquals("Necessary fields missing"
                ,calendarPageController.checkEventInputs("Software development study",""
                        ,sampleDate,sampleDate,"N201"));}
    @Test
    public void testMissingEventStartDate(){
        assertEquals("Necessary fields missing"
                ,calendarPageController.checkEventInputs("Software development study","Study period"
                        ,null,sampleDate,"N201"));}

    @Test
    public void testMissingEventEndDate(){
        assertEquals("Necessary fields missing"
                ,calendarPageController.checkEventInputs("Software development study","Study period"
                        ,sampleDate,null,"N201"));}

    @Test
    public void testMissingEventLocation(){//should be allowed to succeed without location
        assertEquals(null,calendarPageController.checkEventInputs("Software development study","Study period"
                ,sampleDate,sampleDate,""));}
    @Test
    public void testNoFieldsMissing(){
        assertEquals(null,calendarPageController.checkEventInputs("Software development study","Study period"
                ,sampleDate,sampleDate,"N201"));}
    @Test
    public void testLocationNoLetters(){
        assertEquals("Location format incorrect",calendarPageController.checkEventInputs("Software development study","Study period"
                ,sampleDate,sampleDate,"2015"));}
    @Test
    public void testLocationExtraLetters(){
        assertEquals("Location format incorrect",calendarPageController.checkEventInputs("Software development study","Study period"
                ,sampleDate,sampleDate,"NOOO"));}
    @Test
    public void testLocationOneLetter(){
        assertEquals("Location format incorrect",calendarPageController.checkEventInputs("Software development study","Study period"
                ,sampleDate,sampleDate,"N"));}
    @Test
    public void testLocationOneNumber(){
        assertEquals("Location format incorrect",calendarPageController.checkEventInputs("Software development study","Study period"
                ,sampleDate,sampleDate,"5"));}


    @Test
    public void testFormatDateLongMinuteAndHour(){
        assertEquals("2025-04-16 12:30:00",calendarPageController.formatDatetime(sampleDate,12,30));}
    @Test
    public void testFormatDateShortMinuteAndHour(){
        assertEquals("2025-04-16 06:05:00",calendarPageController.formatDatetime(sampleDate,6,5));}

    @Test
    public void testFormatDateShortMinute(){
        assertEquals("2025-04-16 20:05:00",calendarPageController.formatDatetime(sampleDate,20,5));}

    @Test
    public void testFormatDateShortHour(){
        assertEquals("2025-04-16 06:25:00",calendarPageController.formatDatetime(sampleDate,6,25));}

    @Test
    public void testFormatDateBoundaryValues(){
        assertEquals("2025-04-16 10:10:00",calendarPageController.formatDatetime(sampleDate,10,10));}

    @Test
    public void testFormatDateBoundaryMoreValues(){
        assertEquals("2025-04-16 11:09:00",calendarPageController.formatDatetime(sampleDate,11,9));}

    @Test
    public void testGetEvents2Events() throws SQLException {
        //copied this test dao code from UserSignupDaoTest
        Connection testConnection;
        UserTimetableDAO dao;
        testConnection = DriverManager.getConnection("jdbc:sqlite::memory:");
        dao = new UserTimetableDAO(testConnection); // uses clean, in-memory DB

        UserTimetable testEvent1 = new UserTimetable(1,"Software Development Study","n87654321"
        ,"Study period","2025-04-16 09:30:00","2025-04-16 11:30:00","",0);
        UserTimetable testEvent2 = new UserTimetable(2,"Late Work Schedule","n87654321"
        ,"Work schedule","2025-04-16 22:00:00","2025-04-17 4:00:00","S507",1);
        UserTimetable testEvent3 = new UserTimetable(3,"Lunch Break","n88347891"
        ,"Food break","2025-04-20 12:15:00","2025-04-20 12:45:00","",0);

        dao.insertEvent(testEvent1);
        dao.insertEvent(testEvent2);
        dao.insertEvent(testEvent3);

        ArrayList<UserTimetable> expectedResult = new ArrayList<UserTimetable>();
        expectedResult.add(testEvent1);
        expectedResult.add(testEvent2);
        assertEquals(expectedResult.toString(),calendarPageController.getEvents(dao,"n87654321").toString());
    }

    @Test
    public void testGetEvents1Event() throws SQLException {
        //copied this test dao code from UserSignupDaoTest
        Connection testConnection;
        UserTimetableDAO dao;
        testConnection = DriverManager.getConnection("jdbc:sqlite::memory:");
        dao = new UserTimetableDAO(testConnection); // uses clean, in-memory DB

        UserTimetable testEvent1 = new UserTimetable(1,"Software Development Study","n87654321"
                ,"Study period","2025-04-16 09:30:00","2025-04-16 11:30:00","",0);
        UserTimetable testEvent2 = new UserTimetable(2,"Late Work Schedule","n87654321"
                ,"Work schedule","2025-04-16 22:00:00","2025-04-17 4:00:00","S507",1);
        UserTimetable testEvent3 = new UserTimetable(3,"Lunch Break","n88347891"
                ,"Food break","2025-04-20 12:15:00","2025-04-20 12:45:00","",0);

        dao.insertEvent(testEvent1);
        dao.insertEvent(testEvent2);
        dao.insertEvent(testEvent3);

        ArrayList<UserTimetable> expectedResult = new ArrayList<UserTimetable>();
        expectedResult.add(testEvent3);
        assertEquals(expectedResult.toString(),calendarPageController.getEvents(dao,"n88347891").toString());
    }

    @Test
    public void testGetEventsNoEvents() throws SQLException {
        //copied this test dao code from UserSignupDaoTest
        Connection testConnection;
        UserTimetableDAO dao;
        testConnection = DriverManager.getConnection("jdbc:sqlite::memory:");
        dao = new UserTimetableDAO(testConnection); // uses clean, in-memory DB

        UserTimetable testEvent1 = new UserTimetable(1,"Software Development Study","n87654321"
                ,"Study period","2025-04-16 09:30:00","2025-04-16 11:30:00","",0);
        UserTimetable testEvent2 = new UserTimetable(2,"Late Work Schedule","n87654321"
                ,"Work schedule","2025-04-16 22:00:00","2025-04-17 4:00:00","S507",1);
        UserTimetable testEvent3 = new UserTimetable(3,"Lunch Break","n88347891"
                ,"Food break","2025-04-20 12:15:00","2025-04-20 12:45:00","",0);

        dao.insertEvent(testEvent1);
        dao.insertEvent(testEvent2);
        dao.insertEvent(testEvent3);

        ArrayList<UserTimetable> expectedResult = new ArrayList<UserTimetable>();
        assertEquals(expectedResult.toString(),calendarPageController.getEvents(dao,"n24563478").toString());
    }


}