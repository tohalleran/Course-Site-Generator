/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.files;

import csg.CSGManagerApp;
import csg.data.CSGData;
import csg.data.TeachingAssistant;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;

/**
 *
 * @author tonyohalleran
 */
public class CSGFiles implements AppFileComponent {

    // THIS IS THE APP ITSELF
    CSGManagerApp app;

    // THESE ARE USED FOR IDENTIFYING JSON TYPES
    //COURSE DETAILS
    static final String JSON_COURSE_DETAIL_SUBJECT = "subject";
    static final String JSON_COURSE_DETAIL_NUMBER = "number";
    static final String JSON_COURSE_DETAIL_SEMESTER = "semester";
    static final String JSON_COURSE_DETAIL_YEAR = "year";
    static final String JSON_COURSE_DETAIL_TITLE = "title";
    static final String JSON_COURSE_DETAIL_INSTRUCTOR_NAME = "instructor_name";
    static final String JSON_COURSE_DETAIL_INSTRUCTOR_HOME = "instructor_home";
    static final String JSON_COURSE_DETAIL_EXPORT_DIR = "export_directory";

    static final String JSON_COURSE_DETAIL_TEMPLATE_DIR = "template_directory";

    static final String JSON_BANNER_SCHOOL_IMAGE = "banner_school_image";
    static final String JSON_LEFT_FOOTER_IMAGE = "left_footer_image";
    static final String JSON_RIGHT_FOOTER_IMAGE = "right_footer_image";
    static final String JSON_COURSE_DETAIL_STYLESHEET = "stylesheet";

    //TA DATA
    static final String JSON_START_HOUR = "startHour";
    static final String JSON_END_HOUR = "endHour";
    static final String JSON_OFFICE_HOURS = "officeHours";
    static final String JSON_DAY = "day";
    static final String JSON_TIME = "time";
    static final String JSON_NAME = "name";
    static final String JSON_UNDERGRAD_TAS = "undergrad_tas";
    static final String JSON_EMAIL = "email";

    // RECITATION DATA
    static final String JSON_RECITATIONS = "recitations";
    static final String JSON_RECITATIONS_SECTION = "section";
    static final String JSON_RECITATIONS_INSTRUCTOR = "instructor";
    static final String JSON_RECITATIONS_DAY_TIME = "dayTime";
    static final String JSON_RECITATIONS_LOCATION = "location";
    static final String JSON_RECITATIONS_TA1 = "ta1";
    static final String JSON_RECITATIONS_TA2 = "ta2";

    //SCHEDULE DATA
    static final String JSON_STARTING_MONDAY = "starting_monday";
    static final String JSON_ENDING_FRIDAY = "ending_friday";
    static final String JSON_SCHEDULE_ITEMS = "scheduleItems";
    static final String JSON_SCHEDULE_ITEMS_TYPE = "type";
    static final String JSON_SCHEDULE_DATE = "date";
    static final String JSON_SCHEDULE_TITLE = "title";
    static final String JSON_SCHEDULE_TOPIC = "topic";

    //PROJECT DATA
    static final String JSON_TEAMS = "teams";
    static final String JSON_TEAMS_NAME = "tName";
    static final String JSON_TEAMS_COLOR = "color";
    static final String JSON_TEAMS_TEXT_COLOR = "textColor";
    static final String JSON_TEAMS_LINK = "link";
    static final String JSON_STUDENTS = "students";
    static final String JSON_STUDENTS_FIRST_NAME = "firstName";
    static final String JSON_STUDENTS_LAST_NAME = "lastName";
    static final String JSON_STUDENTS_TEAM = "team";
    static final String JSON_STUDENTS_ROLE = "role";

    public CSGFiles(CSGManagerApp initApp) {
        app = initApp;
    }

    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
        // CLEAR THE OLD DATA OUT
        CSGData dataManager = (CSGData) data;

        // LOAD THE JSON FILE WITH ALL THE DATA
        JsonObject json = loadJSONFile(filePath);

        //COURSE DETAILS TAB
        //LOAD COURSE DETAILS DATA
        String courseSubject = json.getString(JSON_COURSE_DETAIL_SUBJECT);
        String courseNumber = json.getString(JSON_COURSE_DETAIL_NUMBER);
        String courseSemester = json.getString(JSON_COURSE_DETAIL_SEMESTER);
        String courseYear = json.getString(JSON_COURSE_DETAIL_YEAR);
        String courseTitle = json.getString(JSON_COURSE_DETAIL_TITLE);
        String instructorName = json.getString(JSON_COURSE_DETAIL_INSTRUCTOR_NAME);
        String instructorHome = json.getString(JSON_COURSE_DETAIL_INSTRUCTOR_HOME);
        String exportDir = json.getString(JSON_COURSE_DETAIL_EXPORT_DIR);
        String templateDir = json.getString(JSON_COURSE_DETAIL_TEMPLATE_DIR);
        String bannerSchoolImage = json.getString(JSON_BANNER_SCHOOL_IMAGE);
        String leftFooterImage = json.getString(JSON_LEFT_FOOTER_IMAGE);
        String rightFooterImage = json.getString(JSON_RIGHT_FOOTER_IMAGE);
        String stylesheet = json.getString(JSON_COURSE_DETAIL_STYLESHEET);
        dataManager.initCourseInfo(courseSubject, courseNumber, courseSemester, courseYear, courseTitle,
                instructorName, instructorHome, exportDir, templateDir, bannerSchoolImage, leftFooterImage,
                rightFooterImage, stylesheet);

        // RECITATION DATA TAB
        // ADD ALL RECITATIONS FROM FILE INTO LIST
        JsonArray jsonRecArray = json.getJsonArray(JSON_RECITATIONS);
        for (int i = 0; i < jsonRecArray.size(); i++) {
            JsonObject jsonRec = jsonRecArray.getJsonObject(i);

            String recitationSection = jsonRec.getString(JSON_RECITATIONS_SECTION);
            String recitationInstructor = jsonRec.getString(JSON_RECITATIONS_INSTRUCTOR);
            String recitationDayTime = jsonRec.getString(JSON_RECITATIONS_DAY_TIME);
            String recitationLocation = jsonRec.getString(JSON_RECITATIONS_LOCATION);
            String recitationTA1 = jsonRec.getString(JSON_RECITATIONS_TA1);
            String recitationTA2 = jsonRec.getString(JSON_RECITATIONS_TA2);
            dataManager.addRecitation(recitationSection, recitationInstructor, recitationDayTime,
                    recitationLocation, recitationTA1, recitationTA2);
        }
        
        // SCHEDULE DATA TAB
        String startingMonday = json.getString(JSON_STARTING_MONDAY);
        String endingFriday = json.getString(JSON_ENDING_FRIDAY);
        dataManager.initSchedule(startingMonday, endingFriday);
        
        JsonArray jsonScheduleArray = json.getJsonArray(JSON_SCHEDULE_ITEMS);
        for (int i = 0; i < jsonScheduleArray.size(); i++){
            JsonObject jsonSchedule = jsonScheduleArray.getJsonObject(i);
            
            String type = jsonSchedule.getString(JSON_SCHEDULE_ITEMS_TYPE);
            String date = jsonSchedule.getString(JSON_SCHEDULE_DATE);
            String title = jsonSchedule.getString(JSON_SCHEDULE_TITLE);        
            String topic = jsonSchedule.getString(JSON_SCHEDULE_TOPIC);        
            dataManager.addSchedule(type, date, title, topic);
    
        }
        
        // PROJECT DATA TAB
        JsonArray jsonTeamArray = json.getJsonArray(JSON_TEAMS);
        for (int i = 0; i < jsonTeamArray.size(); i++){
            JsonObject jsonTeam = jsonTeamArray.getJsonObject(i);
            
            String tName = jsonTeam.getString(JSON_TEAMS_NAME);
            String color = jsonTeam.getString(JSON_TEAMS_COLOR);
            String textColor = jsonTeam.getString(JSON_TEAMS_TEXT_COLOR);
            String link = jsonTeam.getString(JSON_TEAMS_LINK);
            dataManager.addTeam(tName, color, textColor, link);
        }
        
        JsonArray jsonStudentArray = json.getJsonArray(JSON_STUDENTS);
        for (int i = 0; i < jsonStudentArray.size(); i++){
            JsonObject jsonStudent = jsonStudentArray.getJsonObject(i);
            
            String firstName = jsonStudent.getString(JSON_STUDENTS_FIRST_NAME);
            String lastName = jsonStudent.getString(JSON_STUDENTS_LAST_NAME);
            String team = jsonStudent.getString(JSON_STUDENTS_TEAM);
            String role = jsonStudent.getString(JSON_STUDENTS_ROLE);
            dataManager.addStudent(firstName, lastName, team, role);
        
        }
        

        // TA DATA TAB
        // LOAD THE START AND END HOURS
        String startHour = json.getString(JSON_START_HOUR);
        String endHour = json.getString(JSON_END_HOUR);
        dataManager.initHours(startHour, endHour);

        // NOW RELOAD THE WORKSPACE WITH THE LOADED DATA
        app.getWorkspaceComponent().reloadWorkspace(app.getDataComponent());

        // NOW LOAD ALL THE UNDERGRAD TAs
        JsonArray jsonTAArray = json.getJsonArray(JSON_UNDERGRAD_TAS);
        for (int i = 0; i < jsonTAArray.size(); i++) {
            JsonObject jsonTA = jsonTAArray.getJsonObject(i);
            String name = jsonTA.getString(JSON_NAME);
            String email = jsonTA.getString(JSON_EMAIL);
            dataManager.addTA(name, email);
        }

        // AND THEN ALL THE OFFICE HOURS
        JsonArray jsonOfficeHoursArray = json.getJsonArray(JSON_OFFICE_HOURS);
        for (int i = 0; i < jsonOfficeHoursArray.size(); i++) {
            JsonObject jsonOfficeHours = jsonOfficeHoursArray.getJsonObject(i);
            String day = jsonOfficeHours.getString(JSON_DAY);
            String time = jsonOfficeHours.getString(JSON_TIME);
            String name = jsonOfficeHours.getString(JSON_NAME);
            dataManager.addOfficeHoursReservation(day, time, name);
        }

    }

    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
        InputStream is = new FileInputStream(jsonFilePath);
        JsonReader jsonReader = Json.createReader(is);
        JsonObject json = jsonReader.readObject();
        jsonReader.close();
        is.close();
        return json;
    }

    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {
        // GET THE DATA
        CSGData dataManager = (CSGData) data;

        // NOW BUILD THE TA JSON OBJCTS TO SAVE
        JsonArrayBuilder taArrayBuilder = Json.createArrayBuilder();
        ObservableList<TeachingAssistant> tas = dataManager.getTeachingAssistants();
        for (TeachingAssistant ta : tas) {
            JsonObject taJson = Json.createObjectBuilder()
                    .add(JSON_NAME, ta.getName())
                    .add(JSON_EMAIL, ta.getEmail()).build();
            taArrayBuilder.add(taJson);
        }
        JsonArray undergradTAsArray = taArrayBuilder.build();

        // NOW BUILD THE TIME SLOT JSON OBJCTS TO SAVE
        JsonArrayBuilder timeSlotArrayBuilder = Json.createArrayBuilder();
        ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(dataManager);
        for (TimeSlot ts : officeHours) {
            JsonObject tsJson = Json.createObjectBuilder()
                    .add(JSON_DAY, ts.getDay())
                    .add(JSON_TIME, ts.getTime())
                    .add(JSON_NAME, ts.getName()).build();
            timeSlotArrayBuilder.add(tsJson);
        }
        JsonArray timeSlotsArray = timeSlotArrayBuilder.build();

        // THEN PUT IT ALL TOGETHER IN A JsonObject
        JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add(JSON_START_HOUR, "" + dataManager.getStartHour())
                .add(JSON_END_HOUR, "" + dataManager.getEndHour())
                .add(JSON_UNDERGRAD_TAS, undergradTAsArray)
                .add(JSON_OFFICE_HOURS, timeSlotsArray)
                .build();

        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
        Map<String, Object> properties = new HashMap<>(1);
        properties.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
        StringWriter sw = new StringWriter();
        JsonWriter jsonWriter = writerFactory.createWriter(sw);
        jsonWriter.writeObject(dataManagerJSO);
        jsonWriter.close();

        // INIT THE WRITER
        OutputStream os = new FileOutputStream(filePath);
        JsonWriter jsonFileWriter = Json.createWriter(os);
        jsonFileWriter.writeObject(dataManagerJSO);
        String prettyPrinted = sw.toString();
        PrintWriter pw = new PrintWriter(filePath);
        pw.write(prettyPrinted);
        pw.close();
    }

    // IMPORTING/EXPORTING DATA IS USED WHEN WE READ/WRITE DATA IN AN
    // ADDITIONAL FORMAT USEFUL FOR ANOTHER PURPOSE, LIKE ANOTHER APPLICATION
    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void exportData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
