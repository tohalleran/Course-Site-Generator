/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import csg.CSGManagerApp;
import csg.CSGManagerProp;
import csg.files.TimeSlot;
import csg.workspaces.CSGWorkspace;
import csg.workspaces.RecitationDataTab;
import csg.workspaces.TADataTab;
import djf.components.AppDataComponent;
import djf.ui.AppMessageDialogSingleton;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import properties_manager.PropertiesManager;

/**
 *
 * @author tonyohalleran
 */
public class CSGData implements AppDataComponent {

    // WE'LL NEED ACCESS TO THE APP TO NOTIFY THE GUI WHEN DATA CHANGES
    CSGManagerApp app;

// DATA FOR TA DATA TAB
    // NOTE THAT THIS DATA STRUCTURE WILL DIRECTLY STORE THE
    // DATA IN THE ROWS OF THE TABLE VIEW
    ObservableList<TeachingAssistant> teachingAssistants;

    // THIS WILL STORE ALL THE OFFICE HOURS GRID DATA, WHICH YOU
    // SHOULD NOTE ARE StringProperty OBJECTS THAT ARE CONNECTED
    // TO UI LABELS, WHICH MEANS IF WE CHANGE VALUES IN THESE
    // PROPERTIES IT CHANGES WHAT APPEARS IN THOSE LABELS
    HashMap<String, StringProperty> officeHours;

    // THESE ARE THE LANGUAGE-DEPENDENT VALUES FOR
    // THE OFFICE HOURS GRID HEADERS. NOTE THAT WE
    // LOAD THESE ONCE AND THEN HANG ON TO THEM TO
    // INITIALIZE OUR OFFICE HOURS GRID
    ArrayList<String> gridHeaders;

    // THESE ARE THE TIME BOUNDS FOR THE OFFICE HOURS GRID. NOTE
    // THAT THESE VALUES CAN BE DIFFERENT FOR DIFFERENT FILES, BUT
    // THAT OUR APPLICATION USES THE DEFAULT TIME VALUES AND PROVIDES
    // NO MEANS FOR CHANGING THESE VALUES
    int startHour;
    int endHour;

    // DEFAULT VALUES FOR START AND END HOURS IN MILITARY HOURS
    public static final int MIN_START_HOUR = 9;
    public static final int MAX_END_HOUR = 20;

    // COURSE INFO DATA
    String courseSubject;
    String courseNumber;
    String courseSemester;
    String courseYear;
    String courseTitle;
    String instructorName;
    String instructorHome;
    File exportDir;
    File templateDir;
    String bannerSchoolImage;
    String leftFooterImage;
    String rightFooterImage;
    File stylesheet;

    ObservableList<Template> templates;

    // RECITATION DATA
    ObservableList<Recitation> recitations;

    // SCHEDULE DATA
    ObservableList<Schedule> schedules;
    Date startingMonday;
    Date endingFriday;

    // PROJECT DATA
    ObservableList<Team> teams;
    ObservableList<Student> students;

    /**
     * This constructor will setup the required data structures for use, but
     * will have to wait on the office hours grid, since it receives the
     * StringProperty objects from the Workspace.
     *
     * @param initApp The application this data manager belongs to.
     */
    public CSGData(CSGManagerApp initApp) {
// KEEP THIS FOR LATER
        app = initApp;

        // CONSTRUCT THE LIST OF TAs FOR THE TABLE
        teachingAssistants = FXCollections.observableArrayList();

        // CONSTRUCT LISTS FOR THE TABLE
        recitations = FXCollections.observableArrayList();
        schedules = FXCollections.observableArrayList();
        teams = FXCollections.observableArrayList();
        students = FXCollections.observableArrayList();
        templates = FXCollections.observableArrayList();

        courseSubject = "";
        courseNumber = "";
        courseSemester = "";
        courseYear = "";
        courseTitle = "";
        instructorName = "";
        instructorHome = "";
        exportDir = null;
        templateDir = null;
        bannerSchoolImage = "";
        leftFooterImage = "";
        rightFooterImage = "";
        stylesheet = null;
        startingMonday = null;
        endingFriday = null;

        // THESE ARE THE DEFAULT OFFICE HOURS
        startHour = MIN_START_HOUR;
        endHour = MAX_END_HOUR;

        //THIS WILL STORE OUR OFFICE HOURS
        officeHours = new HashMap();

        // THESE ARE THE LANGUAGE-DEPENDENT OFFICE HOURS GRID HEADERS
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        ArrayList<String> timeHeaders = props.getPropertyOptionsList(CSGManagerProp.OFFICE_HOURS_TABLE_HEADERS);
        ArrayList<String> dowHeaders = props.getPropertyOptionsList(CSGManagerProp.DAYS_OF_WEEK);

        gridHeaders = new ArrayList();
        gridHeaders.addAll(timeHeaders);
        gridHeaders.addAll(dowHeaders);

        // RECITATIONS TABLE DEFAULT DATES
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy");
//        LocalDate localDate = LocalDate.now();
//        String currentYear = dtf.format(localDate);
//        
//        startingMonday = "8/"
    }

    public void initCourseInfo(String courseSubject, String courseNumber, String courseSemester,
            String courseYear, String courseTitle, String instructorName, String instructorHome,
            String exportDir, String templateDir, String bannerSchoolImage, String leftFooterImage,
            String rightFooterImage, String stylesheet) {
        this.courseSubject = courseSubject;
        this.courseNumber = courseNumber;
        this.courseSemester = courseSemester;
        this.courseYear = courseYear;
        this.courseTitle = courseTitle;
        this.instructorName = instructorName;
        this.instructorHome = instructorHome;
        this.exportDir = new File(exportDir);
        this.templateDir = new File(templateDir);
        this.bannerSchoolImage = bannerSchoolImage;
        this.leftFooterImage = leftFooterImage;
        this.rightFooterImage = rightFooterImage;
        this.stylesheet = new File(stylesheet);
        
        
        
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        ImageView bannerImageView = workspace.getCourseDetailsTab().getBannerSchoolImg();
        ImageView leftImageView = workspace.getCourseDetailsTab().getLeftFooterImg();
        ImageView rightImageView = workspace.getCourseDetailsTab().getRightFooterImg();
        
        workspace.getController().setImageHandler(bannerImageView, bannerSchoolImage);
        workspace.getController().setImageHandler(leftImageView, leftFooterImage);
        workspace.getController().setImageHandler(rightImageView, rightFooterImage);
        
        
        setSitePages(templateDir);
    }

    public void initSchedule(String initStartMon, String initEndFri) {
        try {
            if (!initStartMon.equals("") || !initEndFri.equals("")) {
                Date startMonDate = new SimpleDateFormat("yyyy-MM-dd").parse(initStartMon);
                Date endFriDate = new SimpleDateFormat("yyyy-MM-dd").parse(initEndFri);
                Calendar startMonCal = Calendar.getInstance();
                startMonCal.setTime(startMonDate);
                Calendar endFriCal = Calendar.getInstance();
                endFriCal.setTime(endFriDate);

                if (startMonCal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY
                        && endFriCal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY
                        && startMonDate.before(endFriDate)) {
                    // DATE ARE VALID, PROCEED
                    startingMonday = startMonDate;
                    endingFriday = endFriDate;

                }
            }
        } catch (ParseException e) {
            System.out.println("Error in method initSchedule");
        }
    }

    public void setStartingMonday(String startMon) {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();

        try {
            Date startMonDate = new SimpleDateFormat("yyyy-MM-dd").parse(startMon);
            Calendar startMonCal = Calendar.getInstance();
            startMonCal.setTime(startMonDate);
            if (endingFriday != null) {

                if (startMonCal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY
                        && startMonDate.before(endingFriday)) {
                    // DATE ARE VALID, PROCEED
                    startingMonday = startMonDate;
                } else {
                    workspace.getScheduleDataTab().getStartingMondayDatePicker().getEditor().clear();

                    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                    dialog.show("Incompatible Monday Date", "Date must be a monday and be before ending "
                            + "friday date");
                }
            } else if (startMonCal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {

                // DATE ARE VALID, PROCEED
                startingMonday = startMonDate;
            } else {

                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show("Incompatible Monday Date", "Date must be a monday and be before ending "
                        + "friday date");
                workspace.getScheduleDataTab().getStartingMondayDatePicker().getEditor().clear();
            }

        } catch (ParseException e) {
            System.out.println("Error in method initSchedule");
        }
    }

    public void setEndingFriday(String endFri) {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        try {
            Date endFriDate = new SimpleDateFormat("yyyy-MM-dd").parse(endFri);
            Calendar endFriCal = Calendar.getInstance();
            endFriCal.setTime(endFriDate);
            if (startingMonday != null) {
                if (endFriCal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY
                        && startingMonday.before(endFriDate)) {

                    // DATE ARE VALID, PROCEED
                    endingFriday = endFriDate;
                } else {
                    workspace.getScheduleDataTab().getEndingFridayDatePicker().getEditor().clear();
                    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                    dialog.show("Incompatible Friday Date", "Date must be a friday and be after starting "
                            + "monday date");

                }
            } else if (endFriCal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {

                // DATE ARE VALID, PROCEED
                endingFriday = endFriDate;
            } else {

                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show("Incompatible Friday Date", "Date must be a friday and be after starting "
                        + "monday date");
                workspace.getScheduleDataTab().getEndingFridayDatePicker().getEditor().clear();
            }

        } catch (ParseException e) {
            System.out.println("Error in method setEndingFriday");
        }

    }

    /**
     * Called each time new work is created or loaded, it resets all data and
     * data structures such that they can be used for new values.
     */
    @Override
    public void resetData() {
        startHour = MIN_START_HOUR;
        endHour = MAX_END_HOUR;
        teachingAssistants.clear();
        officeHours.clear();

        courseSubject = "";
        courseNumber = "";
        courseSemester = "";
        courseYear = "";
        courseTitle = "";
        instructorName = "";
        instructorHome = "";
        exportDir = null;
        templateDir = null;
        bannerSchoolImage = "";
        leftFooterImage = "";
        rightFooterImage = "";
        stylesheet = null;

        recitations.clear();
        startingMonday = null;
        endingFriday = null;
        schedules.clear();
        teams.clear();
        students.clear();
        templates.clear();
    }
    // ACCESSOR METHODS
    // COURSE DETAILS TAB

    public String getCourseSubject() {
        return courseSubject;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public String getCourseSemester() {
        return courseSemester;
    }

    public String getCourseYear() {
        return courseYear;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public String getInstructorHome() {
        return instructorHome;
    }

    public String getBannerSchoolImage() {
        return bannerSchoolImage;
    }
    public String getBannerSchoolImageTrim(){
        File bannerImage = new File(bannerSchoolImage);
        return "./image/" + bannerImage.getName();
    }

    public void setBannerSchoolImage(String image) {
        bannerSchoolImage = image;
    }

    public String getLeftFooterImage() {
        return leftFooterImage;
    }
    public String getLeftFooterImageTrim() {
        File leftFooter = new File(leftFooterImage);
        return "./image/" + leftFooter.getName();
    }

    public void setLeftFooterImage(String image) {
        leftFooterImage = image;
    }

    public String getRightFooterImage() {
        return rightFooterImage;
    }
    
    public String getRightFooterImageTrim() {
        File rightFooter = new File(rightFooterImage);
        return "./image/" + rightFooter.getName();
    }
    
    public void setRightFooterImage(String image) {
        rightFooterImage = image;
    }

    public String getExportDir() {
        if (exportDir != null) {
            return exportDir.toString();
        }
        return "";
    }

    public void setExportDir(String exportDirText) {
        exportDir = new File(exportDirText);
    }

    public String getTemplateDir() {
        if (templateDir != null) {
            return templateDir.toString();
        }
        return "";
    }

    public void setTemplateDir(String newTemplateDir) {
        templateDir = new File(newTemplateDir);
    }

    public String getStylesheet() {
        if (stylesheet != null) {
            return stylesheet.toString();
        }
        return "";
    }

    public void setStylesheet(String cssFile) {
        stylesheet = new File(cssFile);
    }

    public void setCourseInfo(String subject, String number, String semester,
            String year, String title, String instructorName, String instructorHome) {

        courseSubject = subject;
        courseNumber = number;
        courseSemester = semester;
        courseYear = year;
        courseTitle = title;
        this.instructorName = instructorName;
        this.instructorHome = instructorHome;

    }
    
   
    public void setSitePages(String templatePathText) {

        // CREATE ALL FILES TO CHECK
        File home = new File(templatePathText + "/index.html");
        File syllabus = new File(templatePathText + "/syllabus.html");
        File schedule = new File(templatePathText + "/schedule.html");
        File hws = new File(templatePathText + "/hws.html");
        File projects = new File(templatePathText + "/projects.html");

        // GET CSS FILES
        File cssPath = new File(templatePathText + "/css");
        File[] cssFiles = cssPath.listFiles();

        
        if(home.exists() || syllabus.exists() || schedule.exists() || hws.exists()
                || projects.exists() ){
            
        if (home.exists()) {
            Template temp = new Template("Home", "index.html", "HomeBuilder.js");
            templates.add(temp);
        }
        if (syllabus.exists()) {
            Template temp = new Template("Syllabus", "syllabus.html", "SyllabusBuilder.js");
            templates.add(temp);
        }
        if (schedule.exists()) {
            Template temp = new Template("Schedule", "schedule.html", "ScheduleBuilder.js");
            templates.add(temp);
        }
        if (hws.exists()) {
            Template temp = new Template("HWs", "hws.html", "HWsBuilder.js");
            templates.add(temp);
        }
        if (projects.exists()) {
            Template temp = new Template("Projects", "projects.html", "ProjectsBuilder.js");
            templates.add(temp);
        }
        if (cssFiles.length != 0) {
            CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
            ComboBox cssComboBox = workspace.getCourseDetailsTab().getStylesheetComboBox();
            for (int i = 0; i < cssFiles.length; i++) {
                cssComboBox.getItems().add(cssFiles[i].getName());
            }
        }
        } 
        else{
        AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                    dialog.show("Error in Template Directory", "Template Directory must contain"
                            + " at least index.html and all associated files");
        
        }

    }

    public ObservableList getTemplates() {
        return templates;
    }

    // RECITATION TAB
    public ObservableList<Recitation> getRecitations() {
        return recitations;
    }

    // SCHEDULE TAB
    public String getStartingMonday() {
        if (startingMonday != null) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            return df.format(startingMonday);
        }
        return "";
    }

    public String getStartingMondayMonth() {
        if (startingMonday != null) {
            DateFormat df = new SimpleDateFormat("MM");
            return df.format(startingMonday);
        }
        return "";
    }
    
    public String getStartingMondayMonthTrim() {
        if (startingMonday != null) {
            DateFormat df = new SimpleDateFormat("MM");
            String mondayMonth = df.format(startingMonday);
            if(mondayMonth.substring(0,1).equals("0")){
                return mondayMonth.substring(1);
            }
            else 
                return mondayMonth;
        }
        return "";
    }
    
    public String getStartingMondayDay() {
        if (startingMonday != null) {
            DateFormat df = new SimpleDateFormat("dd");
            return df.format(startingMonday);
        }
        return "";
    }

    public String getStartingMondayDayTrim() {
        if (startingMonday != null) {
            DateFormat df = new SimpleDateFormat("dd");
            String mondayDay = df.format(startingMonday);
            if(mondayDay.substring(0,1).equals("0"))
                return mondayDay.substring(1);
            return mondayDay;
        }
        return "";
    }

    public String getEndingFriday() {
        if (endingFriday != null) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            return df.format(endingFriday);
        }
        return "";
    }

    public String getEndingFridayMonth() {
        if (endingFriday != null) {
            DateFormat df = new SimpleDateFormat("MM");
            return df.format(endingFriday);
        }
        return "";
    }
    
    public String getEndingFridayMonthTrim() {
        if (endingFriday != null) {
            DateFormat df = new SimpleDateFormat("MM");
            String fridayMonth = df.format(endingFriday);
            if(fridayMonth.substring(0,1).equals(0))
                return fridayMonth.substring(1);
            return fridayMonth;
        }
        return "";
    }

    public String getEndingFridayDay() {
        if (endingFriday != null) {
            DateFormat df = new SimpleDateFormat("dd");
            return df.format(endingFriday);
        }
        return "";
    }
    
    public String getEndingFridayDayTrim() {
        if (endingFriday != null) {
            DateFormat df = new SimpleDateFormat("dd");
            String fridayDay = df.format(endingFriday);
            if(fridayDay.substring(0,1).equals("0"))
                return fridayDay.substring(1);
            return fridayDay;
        }
        return "";
    }

    public Date getStartingMondayDate() {
        return startingMonday;
    }

    public Date getEndingFridayDate() {
        return endingFriday;
    }

    public ObservableList<Schedule> getSchedules() {
        return schedules;
    }

    // PROJECT DATA TAB
    public ObservableList<Team> getTeams() {
        return teams;
    }

    public ObservableList<Student> getStudents() {
        return students;
    }

    // TA DATA TAB
    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public ArrayList<String> getGridHeaders() {
        return gridHeaders;
    }

    public ObservableList getTeachingAssistants() {
        return teachingAssistants;
    }

    public String getCellKey(int col, int row) {
        return col + "_" + row;
    }

    public StringProperty getCellTextProperty(int col, int row) {
        String cellKey = getCellKey(col, row);
        return officeHours.get(cellKey);
    }

    public HashMap<String, StringProperty> getOfficeHours() {
        return officeHours;
    }

    public int getNumRows() {
        return ((endHour - startHour) * 2) + 1;
    }

    public String getTimeString(int militaryHour, boolean onHour) {
        String minutesText = "00";
        if (!onHour) {
            minutesText = "30";
        }

        // FIRST THE START AND END CELLS
        int hour = militaryHour;
        if (hour > 12) {
            hour -= 12;
        }
        String cellText = "" + hour + ":" + minutesText;
        if (militaryHour < 12) {
            cellText += "am";
        } else {
            cellText += "pm";
        }
        return cellText;
    }

    public String getCellKey(String day, String time) {
        int col = gridHeaders.indexOf(day);
        int row = 1;
        int hour = Integer.parseInt(time.substring(0, time.indexOf("_")));
        if (time.contains("pm") && !time.contains("12")) {
            hour += 12;
        }
        int milHour = hour;
//        if (hour < startHour)
//            milHour += 12;

        row += (milHour - startHour) * 2;
        if (row == 0) {
            row = 1;
        }
        if (time.contains("_30")) {
            row += 1;
        }
        return getCellKey(col, row);
    }

    public TeachingAssistant getTA(String testName) {
        for (TeachingAssistant ta : teachingAssistants) {
            if (ta.getName().equals(testName)) {
                return ta;
            }
        }
        return null;
    }

    /**
     * This method is for giving this data manager the string property for a
     * given cell.
     */
    public void setCellProperty(int col, int row, StringProperty prop) {
        String cellKey = getCellKey(col, row);
        officeHours.put(cellKey, prop);
    }

    /**
     * This method is for setting the string property for a given cell.
     */
    public void setGridProperty(ArrayList<ArrayList<StringProperty>> grid,
            int column, int row, StringProperty prop) {
        grid.get(row).set(column, prop);
    }

    private void initOfficeHours(int initStartHour, int initEndHour) {
        // NOTE THAT THESE VALUES MUST BE PRE-VERIFIED
        startHour = initStartHour;
        endHour = initEndHour;

        // EMPTY THE CURRENT OFFICE HOURS VALUES
        officeHours.clear();

        // WE'LL BUILD THE USER INTERFACE COMPONENT FOR THE
        // OFFICE HOURS GRID AND FEED THEM TO OUR DATA
        // STRUCTURE AS WE GO
        CSGWorkspace workspaceComponent = (CSGWorkspace) app.getWorkspaceComponent();
        TADataTab taDataTab = workspaceComponent.getTADataTab();
        taDataTab.reloadOfficeHoursGrid(this);
    }

    public void initHours(String startHourText, String endHourText) {
        int initStartHour = Integer.parseInt(startHourText);
        int initEndHour = Integer.parseInt(endHourText);
        if ((initStartHour >= MIN_START_HOUR)
                && (initEndHour <= MAX_END_HOUR)
                && (initStartHour <= initEndHour)) {
            // THESE ARE VALID HOURS SO KEEP THEM
            initOfficeHours(initStartHour, initEndHour);
        }
    }

    public boolean containsTA(String testName, String testEmail) {
        for (TeachingAssistant ta : teachingAssistants) {
            if (ta.getName().equals(testName)) {
                return true;
            }
            if (ta.getEmail().equals(testEmail)) {
                return true;
            }
        }
        return false;
    }

    public void addTA(String initName, String initEmail) {
        // MAKE THE TA
        TeachingAssistant ta = new TeachingAssistant(initName, initEmail);

        // ADD THE TA
        if (!containsTA(initName, initEmail)) {
            teachingAssistants.add(ta);
            CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
            RecitationDataTab recDataTab = workspace.getRecitationDataTab();
            recDataTab.getSupervisingTA1ComboBox().getItems().add(ta.getName());
            recDataTab.getSupervisingTA2ComboBox().getItems().add(ta.getName());
        }

        // SORT THE TAS
        Collections.sort(teachingAssistants);
    }
    
    public void addTA(String initName, String initEmail, boolean bool) {
//        boolean undergrad = Boolean.parseBoolean(bool);
        // MAKE THE TA
        TeachingAssistant ta = new TeachingAssistant(initName, initEmail, bool);

        // ADD THE TA
        if (!containsTA(initName, initEmail)) {
            teachingAssistants.add(ta);
            CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
            RecitationDataTab recDataTab = workspace.getRecitationDataTab();
            recDataTab.getSupervisingTA1ComboBox().getItems().add(ta.getName());
            recDataTab.getSupervisingTA2ComboBox().getItems().add(ta.getName());
        }

        // SORT THE TAS
        Collections.sort(teachingAssistants);
    }

    public void removeTA(String name) {
        for (TeachingAssistant ta : teachingAssistants) {
            if (name.equals(ta.getName())) {
                teachingAssistants.remove(ta);
                CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
            RecitationDataTab recDataTab = workspace.getRecitationDataTab();
            recDataTab.getSupervisingTA1ComboBox().getItems().remove(ta.getName());
            recDataTab.getSupervisingTA2ComboBox().getItems().remove(ta.getName());
                return;
            }
        }
    }

    public void removeRec(String section) {
        for (Recitation rec : recitations) {
            if (section.equals(rec.getSection())) {
                recitations.remove(rec);
                return;
            }
        }
    }

    public void removeSchedule(String date, String title) {
        for (Schedule schedule : schedules) {
            if (date.equals(schedule.getDate()) && title.equals(schedule.getTitle())) {
                schedules.remove(schedule);
                return;
            }
        }

    }

    public void removeTeam(String initname, String initcolor, String initcolorText, String initlink) {
        for (Team team : teams) {
            if (initname.equals(team.getName()) && initlink.equals(team.getLink())) {
                teams.remove(team);
                return;
            }
        }
    }

    public void removeStudent(String initFirstName, String initLastName, String initTeam, String initRole) {
        for (Student student : students) {
            if (initFirstName.equals(student.getFirstName()) && initLastName.equals(student.getLastName())
                    && initTeam.equals(student.getTeam()) && initRole.equals(student.getRole())) {
                students.remove(student);
                return;
            }
        }
    }

    public void addOfficeHoursReservation(String day, String time, String taName) {
        int underscoreIndex = time.indexOf("_");
        int timeInt = Integer.parseInt(time.substring(0, underscoreIndex));
        if (time.contains("pm")) {
            timeInt += 12;
        }
        if (timeInt >= startHour) {
            String cellKey = getCellKey(day, time);
            toggleTAOfficeHours(cellKey, taName);
        }
    }

    public void addTesterOfficeHoursReservation(String day, String time, String taName) {
        int underscoreIndex = time.indexOf("_");
        int timeInt = Integer.parseInt(time.substring(0, underscoreIndex));
        if (time.contains("pm")) {
            timeInt += 12;
        }
        if (timeInt >= startHour) {
            String cellKey = getCellKey(day, time);
            StringProperty cellProp = officeHours.get(cellKey);
            cellProp.setValue(taName);
        }
    }

    /**
     * This function toggles the taName in the cell represented by cellKey.
     * Toggle means if it's there it removes it, if it's not there it adds it.
     */
    public void toggleTAOfficeHours(String cellKey, String taName) {
        StringProperty cellProp = officeHours.get(cellKey);
        String cellText = cellProp.getValue();

        // IF IT ALREADY HAS THE TA, REMOVE IT
        if (cellText.contains(taName)) {
            removeTAFromCell(cellProp, taName);
        } // OTHERWISE ADD IT
        else if (cellText.length() == 0) {
            cellProp.setValue(taName);
        } else {
            cellProp.setValue(cellText + "\n" + taName);
        }
    }

    /**
     * This method removes taName from the office grid cell represented by
     * cellProp.
     */
    public void removeTAFromCell(StringProperty cellProp, String taName) {
        // GET THE CELL TEXT
        String cellText = cellProp.getValue();
        // IS IT THE ONLY TA IN THE CELL?
        if (cellText.equals(taName)) {
            cellProp.setValue("");
        } // IS IT THE FIRST TA IN A CELL WITH MULTIPLE TA'S?
        else if (cellText.indexOf(taName) == 0) {
            int startIndex = cellText.indexOf("\n") + 1;
            cellText = cellText.substring(startIndex);
            cellProp.setValue(cellText);
        } // IS IT IN THE MIDDLE OF A LIST OF TAs
        else if (cellText.indexOf(taName) < cellText.indexOf("\n", cellText.indexOf(taName))) {
            int startIndex = cellText.indexOf("\n" + taName);
            int endIndex = startIndex + taName.length() + 1;
            cellText = cellText.substring(0, startIndex) + cellText.substring(endIndex);
            cellProp.setValue(cellText);
        } // IT MUST BE THE LAST TA
        else {
            int startIndex = cellText.indexOf("\n" + taName);
            cellText = cellText.substring(0, startIndex);
            cellProp.setValue(cellText);
        }
    }

    public void updateTAFromCell(StringProperty cellProp, String taName, String taNewName) {
        // GET THE CELL TEXT
        String cellText = cellProp.getValue();

        //  REPLACE TA NAME IN TA HOURS GRID
        String updatedCellText = cellText.replaceAll(taName, taNewName);
        cellProp.setValue(updatedCellText);
    }

    public void updateTime(int newStartHour, int newEndHour) {
        HashMap<String, StringProperty> newOfficeHours;
        newOfficeHours = new HashMap();
        CSGWorkspace workspaceComponent = (CSGWorkspace) app.getWorkspaceComponent();
        int shiftIndex = (newEndHour - newStartHour) * 2 + 1;
        int oldStartRow = (newStartHour - startHour) * 2 + 1;
        int newStartRow = 1;
        if (newStartHour >= startHour && newEndHour <= endHour) // IF New Time is 10 and old time is 8.  New End is 16 , old end is 20         && newEndHour<=endHour
        {

            for (int col = 2; col < 7; col++) {
                for (int i = 0; i < ((newEndHour - newStartHour) * 2); i++) {

                    int row = oldStartRow;
                    String cellKey = col + "_" + row;
                    StringProperty prop = officeHours.get(cellKey);
                    String newCellKey = col + "_" + newStartRow;
                    newOfficeHours.put(newCellKey, prop);
                    oldStartRow++;
                    newStartRow++;

                }
                oldStartRow = (newStartHour - startHour) * 2 + 1;
                newStartRow = 1;
            }
        } else if (newStartHour >= startHour && newEndHour > endHour) {

            for (int col = 2; col < 7; col++) {
                for (int i = 0; i < ((endHour - newStartHour) * 2); i++) {

                    int row = oldStartRow;
                    String cellKey = col + "_" + row;
                    StringProperty prop = officeHours.get(cellKey);
                    String newCellKey = col + "_" + newStartRow;
                    newOfficeHours.put(newCellKey, prop);
                    oldStartRow++;
                    newStartRow++;

                }
                oldStartRow = (newStartHour - startHour) * 2 + 1;
                newStartRow = 1;
            }
        } else if (startHour > newStartHour && endHour >= newEndHour) {
            oldStartRow = 1;
            newStartRow = (startHour - newStartHour) * 2 + 1;
            for (int col = 2; col < 7; col++) {
                for (int i = 0; i < ((newEndHour - startHour) * 2); i++) {

                    int row = oldStartRow;
                    String cellKey = col + "_" + row;
                    StringProperty prop = officeHours.get(cellKey);
                    String newCellKey = col + "_" + newStartRow;
                    newOfficeHours.put(newCellKey, prop);
                    oldStartRow++;
                    newStartRow++;

                }
                oldStartRow = 1;
                newStartRow = (startHour - newStartHour) * 2 + 1;
            }
        } else if (startHour > newStartHour && newEndHour > endHour) {
            oldStartRow = 1;
            newStartRow = (startHour - newStartHour) * 2 + 1;
            for (int col = 2; col < 7; col++) {
                for (int i = 0; i < ((endHour - startHour) * 2); i++) {

                    int row = oldStartRow;
                    String cellKey = col + "_" + row;
                    StringProperty prop = officeHours.get(cellKey);
                    String newCellKey = col + "_" + newStartRow;
                    newOfficeHours.put(newCellKey, prop);
                    oldStartRow++;
                    newStartRow++;

                }
                oldStartRow = 1;
                newStartRow = (startHour - newStartHour) * 2 + 1;
            }
        }

        workspaceComponent.resetWorkspace();
        initOfficeHoursUpdate(newStartHour, newEndHour);

        for (HashMap.Entry<String, StringProperty> entry : newOfficeHours.entrySet()) {
            String key = entry.getKey();
            StringProperty prop = newOfficeHours.get(key);
            toggleTAOfficeHoursUpdate(key, prop);
        }
    }

    private void initOfficeHoursUpdate(int initStartHour, int initEndHour) {
        // NOTE THAT THESE VALUES MUST BE PRE-VERIFIED
        startHour = initStartHour;
        endHour = initEndHour;

        // EMPTY THE CURRENT OFFICE HOURS VALUES
        officeHours.clear();

        // WE'LL BUILD THE USER INTERFACE COMPONENT FOR THE
        // OFFICE HOURS GRID AND FEED THEM TO OUR DATA
        // STRUCTURE AS WE GO
        CSGWorkspace workspaceComponent = (CSGWorkspace) app.getWorkspaceComponent();
        TADataTab taDataTab = workspaceComponent.getTaDataTab();
        taDataTab.reloadOfficeHoursGrid(this);
    }

    public void toggleTAOfficeHoursUpdate(String cellKey, StringProperty prop) {
        String cellText2 = prop.getValue();
        StringProperty cellProp = officeHours.get(cellKey);
        //String cellText = cellProp.getValue();
        cellProp.set(cellText2);

    }

    public boolean containsRecitation(String testSection, String testInstructor, String testDayTime,
            String testLocation, String ta1, String ta2) {
        for (Recitation rec : recitations) {
            if (rec.getSection().equals(testSection) && rec.getDayTime().equals(testDayTime)
                    && rec.getInstructor().equals(testInstructor) && rec.getLocation().equals(testLocation)
                    && rec.getTa1().equals(ta1) && rec.getTa2().equals(ta2)) {
                return true;
            }

        }
        return false;
    }

    public void addRecitation(String initSection, String initInstructor, String initDayTime, String initLocation,
            String initTA1, String initTA2) {
        // MAKE THE TA
        Recitation rec = new Recitation(initSection, initInstructor, initDayTime, initLocation,
                initTA1, initTA2);

        // ADD THE TA
        if (!containsRecitation(initSection, initInstructor, initDayTime, initLocation,
                initTA1, initTA2)) {
            recitations.add(rec);
        }

        // SORT THE TAS
        Collections.sort(recitations);
    }

    public boolean containsSchedule(String testDate, String testTitle) {
        for (Schedule schedule : schedules) {
            if (schedule.getDate().equals(testDate) && schedule.getTitle().equals(testTitle)) {
                return true;
            }

        }
        return false;
    }

    public void addSchedule(String type, String date, String title, String topic, String link,
            String time, String criteria) {
        // MAKE THE EVENT
        Schedule schedule = new Schedule(type, date, title, topic, link, time, criteria);

        // ADD THE EVENT
        if (!containsSchedule(date, title)) {
            schedules.add(schedule);
        }

        // SORT THE EVENTS
        Collections.sort(schedules);
    }

    public void addTeam(String name, String color, String textColor, String link) {
        // MAKE THE TEAM
        Team team = new Team(name, color, textColor, link);

        // ADD THE TA
        if (!containsTeam(name, link)) {
            teams.add(team);
            
            // ADD TEAM TO TEAM COMBOBOX
            CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
            ComboBox teamComboBox = workspace.getProjectDataTab().getTeamComboBox();
            teamComboBox.getItems().add(name);
        }

        // SORT THE TAS
        Collections.sort(teams);
    }

    public boolean containsTeam(String testName, String testLink) {
        for (Team team : teams) {
            if (team.getName().equals(testName) && team.getLink().equals(testLink)) {
                return true;

            }
        }
        return false;
    }

    public void addStudent(String firstName, String lastName, String team, String role) {
        // MAKE THE TEAM
        Student student = new Student(firstName, lastName, team, role);

        // ADD THE TA
        if (!containsStudent(firstName, lastName, team)) {
            students.add(student);
        }

        // SORT THE TAS
        Collections.sort(schedules);
    }

    public boolean containsStudent(String testFirstName, String testLastName, String testTeam) {
        for (Student student : students) {
            if (student.getFirstName().equals(testFirstName) && student.getLastName().equals(testLastName)) {
                return true;
            }
        }
        return false;
    }

    public void changeTimeFrames(int initStartHour, int initEndHour) {
        startHour = initStartHour;
        endHour = initEndHour;
    }
}
