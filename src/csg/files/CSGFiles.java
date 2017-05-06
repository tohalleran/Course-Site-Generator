/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.files;

import csg.CSGManagerApp;
import csg.data.CSGData;
import csg.data.Recitation;
import csg.data.Schedule;
import csg.data.Student;
import csg.data.TeachingAssistant;
import csg.data.Team;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
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
import org.apache.commons.io.FileUtils;

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

    // WHICH JSON FILES NEED TO BE EXPORTED
    boolean homeBuilderJS;
    boolean syllabusBuilderJS;
    boolean scheduleBuilderJS;
    boolean hwBuilderJS;
    boolean projectsBuilderJS;

    public CSGFiles(CSGManagerApp initApp) {
        app = initApp;
    }

    public void setHomeBuilderJS(boolean homeBuilderJS) {
        this.homeBuilderJS = homeBuilderJS;
    }

    public void setSyllabusBuilderJS(boolean syllabusBuilderJS) {
        this.syllabusBuilderJS = syllabusBuilderJS;
    }

    public void setScheduleBuilderJS(boolean scheduleBuilderJS) {
        this.scheduleBuilderJS = scheduleBuilderJS;
    }

    public void setHwBuilderJS(boolean hwBuilderJS) {
        this.hwBuilderJS = hwBuilderJS;
    }

    public void setProjectsBuilderJS(boolean projectsBuilderJS) {
        this.projectsBuilderJS = projectsBuilderJS;
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
        for (int i = 0; i < jsonScheduleArray.size(); i++) {
            JsonObject jsonSchedule = jsonScheduleArray.getJsonObject(i);

            String type = jsonSchedule.getString(JSON_SCHEDULE_ITEMS_TYPE);
            String date = jsonSchedule.getString(JSON_SCHEDULE_DATE);
            String title = jsonSchedule.getString(JSON_SCHEDULE_TITLE);
            String topic = jsonSchedule.getString(JSON_SCHEDULE_TOPIC);
            String link = jsonSchedule.getString("link");
            String time = jsonSchedule.getString("time");
            String criteria = jsonSchedule.getString("criteria");
            dataManager.addSchedule(type, date, title, topic, link, time, criteria);

        }

        // PROJECT DATA TAB
        JsonArray jsonTeamArray = json.getJsonArray(JSON_TEAMS);
        for (int i = 0; i < jsonTeamArray.size(); i++) {
            JsonObject jsonTeam = jsonTeamArray.getJsonObject(i);

            String tName = jsonTeam.getString(JSON_TEAMS_NAME);
            String color = jsonTeam.getString(JSON_TEAMS_COLOR);
            String textColor = jsonTeam.getString(JSON_TEAMS_TEXT_COLOR);
            String link = jsonTeam.getString(JSON_TEAMS_LINK);
            dataManager.addTeam(tName, color, textColor, link);
        }

        JsonArray jsonStudentArray = json.getJsonArray(JSON_STUDENTS);
        for (int i = 0; i < jsonStudentArray.size(); i++) {
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

        // BUILD THE RECITATIONS JSON OBJECTS TO SAVE
        JsonArrayBuilder recArrayBuilder = Json.createArrayBuilder();
        ObservableList<Recitation> recitations = dataManager.getRecitations();
        for (Recitation rec : recitations) {
            JsonObject recJson = Json.createObjectBuilder()
                    .add(JSON_RECITATIONS_SECTION, rec.getSection())
                    .add(JSON_RECITATIONS_INSTRUCTOR, rec.getInstructor())
                    .add(JSON_RECITATIONS_DAY_TIME, rec.getDayTime())
                    .add(JSON_RECITATIONS_LOCATION, rec.getLocation())
                    .add(JSON_RECITATIONS_TA1, rec.getTa1())
                    .add(JSON_RECITATIONS_TA2, rec.getTa2()).build();
            recArrayBuilder.add(recJson);
        }
        JsonArray recArray = recArrayBuilder.build();

        // BUILD THE SCHEDULE ITEMS JSON OBJECT TO SAVE
        JsonArrayBuilder scheduleArrayBuilder = Json.createArrayBuilder();
        ObservableList<Schedule> schedules = dataManager.getSchedules();
        for (Schedule schedule : schedules) {
            JsonObject scheduleJson = Json.createObjectBuilder()
                    .add(JSON_SCHEDULE_ITEMS_TYPE, schedule.getType())
                    .add(JSON_SCHEDULE_DATE, schedule.getDate())
                    .add(JSON_SCHEDULE_TITLE, schedule.getTitle())
                    .add(JSON_SCHEDULE_TOPIC, schedule.getTopic())
                    .add("link", schedule.getLink())
                    .add("time", schedule.getTime())
                    .add("criteria", schedule.getCriteria())
                    .build();
            scheduleArrayBuilder.add(scheduleJson);
        }
        JsonArray scheduleArray = scheduleArrayBuilder.build();

        // BUILD THE TEAM JSON OBJECT TO SAVE
        JsonArrayBuilder teamArrayBuilder = Json.createArrayBuilder();
        ObservableList<Team> teams = dataManager.getTeams();
        for (Team team : teams) {
            JsonObject teamJson = Json.createObjectBuilder()
                    .add(JSON_TEAMS_NAME, team.getName())
                    .add(JSON_TEAMS_COLOR, team.getColor())
                    .add(JSON_TEAMS_TEXT_COLOR, team.getColorText())
                    .add(JSON_TEAMS_LINK, team.getLink()).build();
            teamArrayBuilder.add(teamJson);
        }
        JsonArray teamArray = teamArrayBuilder.build();

        // BUILD THE STUDENT JSON OBJECT TO SAVE
        JsonArrayBuilder studentArrayBuilder = Json.createArrayBuilder();
        ObservableList<Student> students = dataManager.getStudents();
        for (Student student : students) {
            JsonObject studentJson = Json.createObjectBuilder()
                    .add(JSON_STUDENTS_FIRST_NAME, student.getFirstName())
                    .add(JSON_STUDENTS_LAST_NAME, student.getLastName())
                    .add(JSON_STUDENTS_TEAM, student.getTeam())
                    .add(JSON_STUDENTS_ROLE, student.getRole()).build();
            studentArrayBuilder.add(studentJson);
        }
        JsonArray studentArray = studentArrayBuilder.build();

        // THEN PUT IT ALL TOGETHER IN A JsonObject
        JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add(JSON_COURSE_DETAIL_SUBJECT, "" + dataManager.getCourseSubject())
                .add(JSON_COURSE_DETAIL_NUMBER, "" + dataManager.getCourseNumber())
                .add(JSON_COURSE_DETAIL_SEMESTER, "" + dataManager.getCourseSemester())
                .add(JSON_COURSE_DETAIL_YEAR, "" + dataManager.getCourseYear())
                .add(JSON_COURSE_DETAIL_TITLE, "" + dataManager.getCourseTitle())
                .add(JSON_COURSE_DETAIL_INSTRUCTOR_NAME, "" + dataManager.getInstructorName())
                .add(JSON_COURSE_DETAIL_INSTRUCTOR_HOME, "" + dataManager.getInstructorHome())
                .add(JSON_COURSE_DETAIL_EXPORT_DIR, "" + dataManager.getExportDir())
                .add(JSON_COURSE_DETAIL_TEMPLATE_DIR, "" + dataManager.getTemplateDir())
                .add(JSON_BANNER_SCHOOL_IMAGE, "" + dataManager.getBannerSchoolImage())
                .add(JSON_LEFT_FOOTER_IMAGE, "" + dataManager.getLeftFooterImage())
                .add(JSON_RIGHT_FOOTER_IMAGE, "" + dataManager.getRightFooterImage())
                .add(JSON_COURSE_DETAIL_STYLESHEET, "" + dataManager.getStylesheet())
                .add(JSON_START_HOUR, "" + dataManager.getStartHour())
                .add(JSON_END_HOUR, "" + dataManager.getEndHour())
                .add(JSON_UNDERGRAD_TAS, undergradTAsArray)
                .add(JSON_OFFICE_HOURS, timeSlotsArray)
                .add(JSON_RECITATIONS, recArray)
                .add(JSON_STARTING_MONDAY, "" + dataManager.getStartingMonday())
                .add(JSON_ENDING_FRIDAY, "" + dataManager.getEndingFriday())
                .add(JSON_SCHEDULE_ITEMS, scheduleArray)
                .add(JSON_TEAMS, teamArray)
                .add(JSON_STUDENTS, studentArray)
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
       
        
        
        // FILEPATH IS THE CURRENTWORKFILE AKA SITESAVETEST

        // COPY DATA INTO THE SUB JSON FILES
        // CLEAR THE OLD DATA OUT
        CSGData dataManager = (CSGData) data;
 
        
        // NOW BUILD THE TA JSON OBJCTS TO SAVE
        JsonArrayBuilder taArrayBuilder = Json.createArrayBuilder();
        JsonArrayBuilder gradTAArrayBuilder = Json.createArrayBuilder();

        ObservableList<TeachingAssistant> tas = dataManager.getTeachingAssistants();
        for (TeachingAssistant ta : tas) {
            if (ta.getUndergrad()) {
                JsonObject taJson = Json.createObjectBuilder()
                        .add(JSON_NAME, ta.getName())
                        .add(JSON_EMAIL, ta.getEmail()).build();
                taArrayBuilder.add(taJson);
            } else {
                JsonObject taJson = Json.createObjectBuilder()
                        .add(JSON_NAME, ta.getName())
                        .add(JSON_EMAIL, ta.getEmail()).build();
                gradTAArrayBuilder.add(taJson);
            }
        }
        JsonArray undergradTAsArray = taArrayBuilder.build();
        JsonArray gradTAsArray = gradTAArrayBuilder.build();

        // NOW BUILD THE TIME SLOT JSON OBJCTS TO EXPORT
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

        JsonObject taTimeSlotJSO = Json.createObjectBuilder()
                .add(JSON_START_HOUR, "" + dataManager.getStartHour())
                .add(JSON_END_HOUR, "" + dataManager.getEndHour())
                .add(JSON_UNDERGRAD_TAS, undergradTAsArray)
                .add("grad_tas", gradTAsArray)
                .add(JSON_OFFICE_HOURS, timeSlotsArray)
                .build();

        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
        Map<String, Object> properties = new HashMap<>(1);
        properties.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
        StringWriter sw = new StringWriter();
        JsonWriter jsonWriter = writerFactory.createWriter(sw);
        jsonWriter.writeObject(taTimeSlotJSO);
        jsonWriter.close();

        // INIT THE WRITER
        File officeHrsGridDataFile = new File(dataManager.getTemplateDir() + "/js/OfficeHoursGridData.json");
        officeHrsGridDataFile.createNewFile();
        OutputStream os = new FileOutputStream(officeHrsGridDataFile);
        JsonWriter jsonFileWriter = Json.createWriter(os);
        jsonFileWriter.writeObject(taTimeSlotJSO);
        String prettyPrinted = sw.toString();
        PrintWriter pw = new PrintWriter(officeHrsGridDataFile);
        pw.write(prettyPrinted);
        pw.close();

        // BUILD THE RECITATIONS JSON OBJECTS TO EXPORT
        JsonArrayBuilder recArrayBuilder = Json.createArrayBuilder();
        ObservableList<Recitation> recitations = dataManager.getRecitations();
        for (Recitation rec : recitations) {
            JsonObject recJson = Json.createObjectBuilder()
                    .add(JSON_RECITATIONS_SECTION, "<strong>" + rec.getSection() + "</strong> "
                            + "(" + rec.getInstructor() + ")")
                    .add("day_time", rec.getDayTime())
                    .add(JSON_RECITATIONS_LOCATION, rec.getLocation())
                    .add("ta_1", rec.getTa1())
                    .add("ta_2", rec.getTa2()).build();
            recArrayBuilder.add(recJson);
        }
        JsonArray recArray = recArrayBuilder.build();

        JsonObject recJSO = Json.createObjectBuilder()
                .add(JSON_RECITATIONS, recArray)
                .build();

        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
        jsonWriter.writeObject(recJSO);
        jsonWriter.close();

        // INIT THE WRITER
        File recDataFile = new File(dataManager.getTemplateDir() + "/js/RecitationsData.json");
        recDataFile.createNewFile();
        os = new FileOutputStream(recDataFile);
        jsonFileWriter = Json.createWriter(os);
        jsonFileWriter.writeObject(recJSO);
        pw = new PrintWriter(recDataFile);
        pw.write(prettyPrinted);
        pw.close();

        // BUILD THE SCHEDULE ITEMS JSON OBJECT TO EXPORT
        JsonArrayBuilder holidayArrayBuilder = Json.createArrayBuilder();
        ObservableList<Schedule> schedules = dataManager.getSchedules();
        for (Schedule schedule : schedules) {
            if (schedule.getType().equalsIgnoreCase("holiday")) {
                if (schedule.getTitle().equalsIgnoreCase("SNOW DAY")) {
                    JsonObject holidayJson = Json.createObjectBuilder()
                            .add("month", schedule.getMonth())
                            .add("day", schedule.getDay())
                            .add(JSON_SCHEDULE_TITLE, schedule.getTitle() + "<br /><img src='./images/SnowDay.gif' />")
                            .add("link", schedule.getLink())
                            .build();
                    holidayArrayBuilder.add(holidayJson);

                } else {
                    JsonObject holidayJson = Json.createObjectBuilder()
                            .add("month", schedule.getMonth())
                            .add("day", schedule.getDay())
                            .add(JSON_SCHEDULE_TITLE, schedule.getTitle() + "<br /><br /><br />")
                            .add("link", schedule.getLink())
                            .build();
                    holidayArrayBuilder.add(holidayJson);
                }
            }
        }
        JsonArray holidayArray = holidayArrayBuilder.build();

        JsonArrayBuilder lectureArrayBuilder = Json.createArrayBuilder();
        for (Schedule schedule : schedules) {
            if (schedule.getType().equalsIgnoreCase("lecture")) {
                JsonObject lectureJson = Json.createObjectBuilder()
                        .add("month", schedule.getMonth())
                        .add("day", schedule.getDay())
                        .add(JSON_SCHEDULE_TITLE, schedule.getTitle())
                        .add(JSON_SCHEDULE_TOPIC, schedule.getTopic() + "<br /><br /><br />")
                        .add("link", schedule.getLink())
                        .build();
                lectureArrayBuilder.add(lectureJson);
            }
        }
        JsonArray lectureArray = lectureArrayBuilder.build();

        JsonArrayBuilder recEventArrayBuilder = Json.createArrayBuilder();
        for (Schedule schedule : schedules) {
            if (schedule.getType().equalsIgnoreCase("recitation")) {
                JsonObject recEventJson = Json.createObjectBuilder()
                        .add("month", schedule.getMonth())
                        .add("day", schedule.getDay())
                        .add(JSON_SCHEDULE_TITLE, schedule.getTitle())
                        .add(JSON_SCHEDULE_TOPIC, schedule.getTopic() + "<br /><br /><br />")
                        .build();
                recEventArrayBuilder.add(recEventJson);
            }
        }
        JsonArray recEventArray = recEventArrayBuilder.build();

        JsonArrayBuilder hwArrayBuilder = Json.createArrayBuilder();
        for (Schedule schedule : schedules) {
            if (schedule.getType().equalsIgnoreCase("HW")) {
                JsonObject hwJson = Json.createObjectBuilder()
                        .add("month", schedule.getMonth())
                        .add("day", schedule.getDay())
                        .add(JSON_SCHEDULE_TITLE, schedule.getTitle())
                        .add(JSON_SCHEDULE_TOPIC, schedule.getTopic() + "<br /><br /><br />")
                        .add("link", schedule.getLink())
                        .add("time", schedule.getTime())
                        .add("criteria", schedule.getCriteria())
                        .build();
                hwArrayBuilder.add(hwJson);
            }
        }
        JsonArray hwArray = hwArrayBuilder.build();

        JsonArrayBuilder referenceArrayBuilder = Json.createArrayBuilder();
        for (Schedule schedule : schedules) {
            if (schedule.getType().equalsIgnoreCase("Reference")) {
                JsonObject referenceJson = Json.createObjectBuilder()
                        .add("month", schedule.getMonth())
                        .add("day", schedule.getDay())
                        .add(JSON_SCHEDULE_TITLE, schedule.getTitle())
                        .add(JSON_SCHEDULE_TOPIC, schedule.getTopic() + "<br /><br /><br />")
                        .add("link", schedule.getLink())
                        .build();
                referenceArrayBuilder.add(referenceJson);
            }
        }
        JsonArray referenceArray = referenceArrayBuilder.build();

        JsonObject scheduleJSO = Json.createObjectBuilder()
                .add("startingMondayMonth", dataManager.getStartingMondayMonth())
                .add("startingMondayDay", dataManager.getStartingMondayDay())
                .add("endingFridayMonth", dataManager.getEndingFridayMonth())
                .add("endingFridayDay", dataManager.getEndingFridayDay())
                .add("holidays", holidayArray)
                .add("lectures", lectureArray)
                .add("references", referenceArray)
                .add("recitations", recEventArray)
                .add("hws", hwArray)
                .build();

        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
        jsonWriter.writeObject(scheduleJSO);
        jsonWriter.close();

        // INIT THE WRITER
        File scheduleDataFile = new File(dataManager.getTemplateDir() + "/js/ScheduleData.json");
        scheduleDataFile.createNewFile();
        os = new FileOutputStream(scheduleDataFile);
        jsonFileWriter = Json.createWriter(os);
        jsonFileWriter.writeObject(scheduleJSO);
        pw = new PrintWriter(scheduleDataFile);
        pw.write(prettyPrinted);
        pw.close();

        // BUILD THE PROJECT JSON OBJECT TO SAVE
        JsonArrayBuilder projectArrayBuilder = Json.createArrayBuilder();

        ObservableList<Team> teams = dataManager.getTeams();
        ObservableList<Student> students = dataManager.getStudents();
        for (Team team : teams) {
            JsonObject projectJson = Json.createObjectBuilder()
                    .add("name", team.getName())
                    .add("link", team.getLink()).build();

            JsonArrayBuilder studentArrayBuilder = Json.createArrayBuilder();
            for (Student student : students) {
                if (student.getTeam().equalsIgnoreCase(team.getName())) {
                    JsonObject studentJson = Json.createObjectBuilder()
                            .add("students", student.getFirstName() + " " + student.getLastName())
                            .build();
                    studentArrayBuilder.add(studentJson);
                }
            }

            JsonArray studentArray = studentArrayBuilder.build();
            JsonObject studentsJSO = Json.createObjectBuilder()
                    .add("students", studentArray).build();
            projectArrayBuilder.add(projectJson).add(studentsJSO);
        }
        JsonArray projectArray = projectArrayBuilder.build();

        JsonObject workJSO = Json.createObjectBuilder()
                .add("semester", dataManager.getCourseSemester() + " " + dataManager.getCourseYear())
                .add("projects", projectArray)
                .build();

        JsonObject projectJSO = Json.createObjectBuilder()
                .add("work", workJSO)
                .build();

        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
        jsonWriter.writeObject(projectJSO);
        jsonWriter.close();

        // INIT THE WRITER
        File projectDataFile = new File(dataManager.getTemplateDir() + "/js/ProjectsData.json");
        projectDataFile.createNewFile();
        os = new FileOutputStream(projectDataFile);
        jsonFileWriter = Json.createWriter(os);
        jsonFileWriter.writeObject(projectJSO);
        pw = new PrintWriter(projectDataFile);
        pw.write(prettyPrinted);
        pw.close();

        // TEAMS AND STUDENTS
        JsonArrayBuilder teamsArrayBuilder = Json.createArrayBuilder();
        for (Team team : teams) {

            JsonObject teamJson = Json.createObjectBuilder()
                    .add(JSON_NAME, team.getName())
                    .add("red", team.getRed())
                    .add("green", team.getGreen())
                    .add("blue", team.getBlue())
                    .add("text_color", team.getColorText())
                    .build();
            teamsArrayBuilder.add(teamJson);

        }
        JsonArray teamsArray = teamsArrayBuilder.build();

        JsonArrayBuilder studentsArrayBuilder = Json.createArrayBuilder();
        for (Student student : students) {

            JsonObject studentJson = Json.createObjectBuilder()
                    .add(JSON_STUDENTS_LAST_NAME, student.getLastName())
                    .add(JSON_STUDENTS_FIRST_NAME, student.getFirstName())
                    .add(JSON_STUDENTS_TEAM, student.getTeam())
                    .add(JSON_STUDENTS_ROLE, student.getRole())
                    .build();
            teamsArrayBuilder.add(studentJson);

        }
        JsonArray studentsArray = studentsArrayBuilder.build();

        JsonObject teamsStudentsJSO = Json.createObjectBuilder()
                .add(JSON_TEAMS, teamsArray)
                .add(JSON_STUDENTS, studentsArray)
                .build();

        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
        jsonWriter.writeObject(teamsStudentsJSO);
        jsonWriter.close();

        // INIT THE WRITER
        File teamsStudentsFile = new File(dataManager.getTemplateDir() + "/js/TeamsAndStudents.json");
        teamsStudentsFile.createNewFile();
        os = new FileOutputStream(teamsStudentsFile);
        jsonFileWriter = Json.createWriter(os);
        jsonFileWriter.writeObject(teamsStudentsJSO);
        pw = new PrintWriter(teamsStudentsFile);
        pw.write(prettyPrinted);
        pw.close();
        
        
        
        
        // HOME DATA
        JsonObject homeJSO = Json.createObjectBuilder()
                .add(JSON_COURSE_DETAIL_SUBJECT, dataManager.getCourseSubject())
                .add(JSON_COURSE_DETAIL_NUMBER, dataManager.getCourseNumber())
                .add(JSON_COURSE_DETAIL_SEMESTER, dataManager.getCourseSemester())
                .add(JSON_COURSE_DETAIL_YEAR, dataManager.getCourseYear())
                .add(JSON_COURSE_DETAIL_TITLE, dataManager.getCourseTitle())
                .add(JSON_COURSE_DETAIL_INSTRUCTOR_NAME, dataManager.getInstructorName())
                .add(JSON_COURSE_DETAIL_INSTRUCTOR_HOME, dataManager.getInstructorHome())
                .add(JSON_BANNER_SCHOOL_IMAGE, dataManager.getBannerSchoolImage())
                .add(JSON_LEFT_FOOTER_IMAGE, dataManager.getLeftFooterImage())
                .add(JSON_RIGHT_FOOTER_IMAGE, dataManager.getRightFooterImage())
                .build();

        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
        jsonWriter.writeObject(homeJSO);
        jsonWriter.close();

        // INIT THE WRITER
        File homeFile = new File(dataManager.getTemplateDir() + "/js/HomeData.json");
        homeFile.createNewFile();
        os = new FileOutputStream(homeFile);
        jsonFileWriter = Json.createWriter(os);
        jsonFileWriter.writeObject(homeJSO);
        pw = new PrintWriter(homeFile);
        pw.write(prettyPrinted);
        pw.close();
        
        
        

        // EXPORT ALL FILES IN THE PUBLIC_HTML
//        String fileExec = System.getProperty("user.dir");
        File siteTemplate = new File(dataManager.getTemplateDir());
        File exportDir = new File(dataManager.getExportDir());

        //  MOVE PUBLIC_HTML FOLDER TO SELECTED DIRECTORY
        FileUtils.copyDirectory(new File(siteTemplate + "/css"), exportDir);
        FileUtils.copyDirectory(new File(siteTemplate + "/images"), exportDir);
        FileUtils.copyDirectory(new File(siteTemplate + "/js"), exportDir);

        if (homeBuilderJS) {
            FileUtils.copyDirectory(new File(siteTemplate + "/index.html"), exportDir);
        }
        if (syllabusBuilderJS) {
            FileUtils.copyDirectory(new File(siteTemplate + "/syllabus.html"), exportDir);
        }
        if (scheduleBuilderJS) {
            FileUtils.copyDirectory(new File(siteTemplate + "/schedule.html"), exportDir);
        }
        if (hwBuilderJS) {
            FileUtils.copyDirectory(new File(siteTemplate + "/hws.html"), exportDir);
        }
        if (projectsBuilderJS) {
            FileUtils.copyDirectory(new File(siteTemplate + "/projects.html"), exportDir);
        }

    }

    public void testLoadData(AppDataComponent data, String filePath) throws IOException {
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
        for (int i = 0; i < jsonScheduleArray.size(); i++) {
            JsonObject jsonSchedule = jsonScheduleArray.getJsonObject(i);

            String type = jsonSchedule.getString(JSON_SCHEDULE_ITEMS_TYPE);
            String date = jsonSchedule.getString(JSON_SCHEDULE_DATE);
            String title = jsonSchedule.getString(JSON_SCHEDULE_TITLE);
            String topic = jsonSchedule.getString(JSON_SCHEDULE_TOPIC);
            String link = jsonSchedule.getString("link");
            String time = jsonSchedule.getString("time");
            String criteria = jsonSchedule.getString("criteria");
            dataManager.addSchedule(type, date, title, topic, link, time, criteria);

        }

        // PROJECT DATA TAB
        JsonArray jsonTeamArray = json.getJsonArray(JSON_TEAMS);
        for (int i = 0; i < jsonTeamArray.size(); i++) {
            JsonObject jsonTeam = jsonTeamArray.getJsonObject(i);

            String tName = jsonTeam.getString(JSON_TEAMS_NAME);
            String color = jsonTeam.getString(JSON_TEAMS_COLOR);
            String textColor = jsonTeam.getString(JSON_TEAMS_TEXT_COLOR);
            String link = jsonTeam.getString(JSON_TEAMS_LINK);
            dataManager.addTeam(tName, color, textColor, link);
        }

        JsonArray jsonStudentArray = json.getJsonArray(JSON_STUDENTS);
        for (int i = 0; i < jsonStudentArray.size(); i++) {
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
        dataManager.changeTimeFrames(Integer.parseInt(startHour), Integer.parseInt(endHour));

//        // NOW RELOAD THE WORKSPACE WITH THE LOADED DATA
//        app.getWorkspaceComponent().reloadWorkspace(app.getDataComponent());
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
            dataManager.addTesterOfficeHoursReservation(day, time, name);
        }

    }

}
