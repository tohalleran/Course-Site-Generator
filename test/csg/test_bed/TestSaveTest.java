/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.test_bed;

import csg.CSGManagerApp;
import csg.data.CSGData;
import csg.data.Recitation;
import csg.data.Schedule;
import csg.data.Student;
import csg.data.TeachingAssistant;
import csg.data.Team;
import csg.files.CSGFiles;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tonyohalleran
 */
public class TestSaveTest {

    public TestSaveTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of main method, of class TestSave.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        TestSave.main(args);

        CSGManagerApp app = new CSGManagerApp();
        CSGFiles file = new CSGFiles(app);
        CSGData data = new CSGData(app);
        
        // ADD GRID HEADERS
        for (int i = 0; i < data.getGridHeaders().size(); i++){
            data.getOfficeHours().put(i + "_0", new SimpleStringProperty(data.getGridHeaders().get(i)));
        }
        // ADD TIMESLOTS AND DATA
        data.getOfficeHours().put("0_1", new SimpleStringProperty("9:00am"));
        data.getOfficeHours().put("1_1", new SimpleStringProperty("9:30am"));
        data.getOfficeHours().put("2_1", new SimpleStringProperty("Tony O'Halleran"));
        data.getOfficeHours().put("3_1", new SimpleStringProperty("Tony O'Halleran"));
        data.getOfficeHours().put("4_1", new SimpleStringProperty("Tony O'Halleran"));
        data.getOfficeHours().put("5_1", new SimpleStringProperty("Tony O'Halleran"));
        data.getOfficeHours().put("6_1", new SimpleStringProperty("Tony O'Halleran"));
        
        data.getOfficeHours().put("0_2", new SimpleStringProperty("9:30am"));
        data.getOfficeHours().put("1_2", new SimpleStringProperty("10:00am"));
        data.getOfficeHours().put("2_2", new SimpleStringProperty("Tony O'Halleran"));
        data.getOfficeHours().put("3_2", new SimpleStringProperty("Tony O'Halleran"));
        data.getOfficeHours().put("4_2", new SimpleStringProperty("Tony O'Halleran"));
        data.getOfficeHours().put("5_2", new SimpleStringProperty("Tony O'Halleran"));
        data.getOfficeHours().put("6_2", new SimpleStringProperty("Tony O'Halleran"));
        

        try {

            file.testLoadData(data, "./work/SiteSaveTest.json");

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        String courseSubject = data.getCourseSubject();
        String courseNumber = data.getCourseNumber();
        String courseSemester = data.getCourseSemester();
        String courseYear = data.getCourseYear();
        String courseTitle = data.getCourseTitle();
        String instructorName = data.getInstructorName();
        String instructorHome = data.getInstructorHome();
        String exportDir = data.getExportDir();
        String templateDir = data.getTemplateDir();
        String bannerSchoolImage = data.getBannerSchoolImage();
        String leftFooterImage = data.getLeftFooterImage();
        String rightFooterImage = data.getRightFooterImage();
        String stylesheet = data.getStylesheet();
        
        ObservableList<TeachingAssistant> teachingAssistants = data.getTeachingAssistants();
        ObservableList<Recitation> recitations = data.getRecitations();
        ObservableList<Schedule> schedules = data.getSchedules();
        ObservableList<Team> teams = data.getTeams();
        ObservableList<Student> students = data.getStudents();

        
        assertEquals("CSE", courseSubject);
        assertEquals("219", courseNumber);
        assertEquals("Fall", courseSemester);
        assertEquals("2017", courseYear);
        assertEquals("Computer Science I", courseTitle);
        assertEquals("Richard McKenna", instructorName);
        assertEquals("https://www.cs.stonybrook.edu/~richard", instructorHome);
        assertEquals("~/Documents", exportDir);
        assertEquals("~/Desktop", templateDir);
        assertEquals("image.jpg", bannerSchoolImage);
        assertEquals("image2.jpg", leftFooterImage);
        assertEquals("image3.jpg", rightFooterImage);
        assertEquals("sea_wolf.css", stylesheet);
        
        assertEquals("sea_wolf.css", stylesheet);
        
        // TEST TAs
        assertEquals("Tony", teachingAssistants.get(0).getName());
        assertEquals("tony.ohalleran@stonybrook.edu", teachingAssistants.get(0).getEmail());
        
        // TEST RECITATIONs
        assertEquals("R02", recitations.get(0).getSection());
        assertEquals("Richard McKenna", recitations.get(0).getInstructor());
        assertEquals("Wed 3:30pm-4:23pm", recitations.get(0).getDayTime());
        assertEquals("Old CS 2114", recitations.get(0).getLocation());
        assertEquals("Jane Doe", recitations.get(0).getTa1());
        assertEquals("Joe Shmo", recitations.get(0).getTa2());
        
        // TEST SCHEDULES
        assertEquals("Holiday", schedules.get(0).getType());
        assertEquals("2017-02-09", schedules.get(0).getDate());
        assertEquals("SNOW DAY", schedules.get(0).getTitle());
        assertEquals("Event Programming", schedules.get(0).getTopic());
        
        assertEquals("2017-04-17", data.getStartingMonday());
        assertEquals("2017-04-28", data.getEndingFriday());
        
        // TEST TEAMS
        assertEquals("Atomic Comics", teams.get(0).getName());
        assertEquals("552211", teams.get(0).getColor());
        assertEquals("FFFFFF", teams.get(0).getColorText());
        assertEquals("https://c4-comics.appspot.com", teams.get(0).getLink());
        
        // TEST STUDENTS
        assertEquals("Jane", students.get(0).getFirstName());
        assertEquals("Doe", students.get(0).getLastName());
        assertEquals("Atomic Comics", students.get(0).getTeam());
        assertEquals("Lead Programmer", students.get(0).getRole());
        
        
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

}
