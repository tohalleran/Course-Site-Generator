/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspaces;

import csg.CSGManagerApp;
import static csg.CSGManagerProp.*;
import csg.CSGManagerProp;
import csg.data.CSGData;
import csg.data.Recitation;
import csg.data.Schedule;
import csg.data.TeachingAssistant;
import csg.files.TimeSlot;
import csg.transaction.addTATransaction;
import csg.transaction.deleteTATransaction;
import csg.transaction.endHourTrans;
import csg.style.CSGStyle.*;
import static csg.style.CSGStyle.CLASS_HIGHLIGHTED_GRID_CELL;
import static csg.style.CSGStyle.CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN;
import static csg.style.CSGStyle.CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE;
import csg.transaction.addRecitationTransaction;
import csg.transaction.addScheduleTrans;
import csg.transaction.updateTime_Transaction;
import csg.transaction.cellToggleTrans;
import csg.transaction.deleteRecTransaction;
import csg.transaction.deleteScheduleTransaction;
import csg.transaction.startHourTrans;
import csg.transaction.updateRecTrans;
import csg.transaction.updateScheduleTrans;
import csg.transaction.updateTATrans;

import djf.ui.AppGUI;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppYesNoCancelDialogSingleton;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static javafx.scene.input.DataFormat.URL;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import jtps.jTPS;
import jtps.jTPS_Transaction;
import properties_manager.PropertiesManager;

/**
 *
 * @author tonyohalleran
 */
public class CSGController {

    // THE APP PROVIDES ACCESS TO OTHER COMPONENTS AS NEEDED
    CSGManagerApp app;
    jTPS jTPS;

    /**
     * Constructor, note that the app must already be constructed.
     */
    public CSGController(CSGManagerApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;
        jTPS = new jTPS();
    }

    /**
     * This helper method should be called every time an edit happens.
     */
    private void markWorkAsEdited() {
        // MARK WORK AS EDITED
        AppGUI gui = app.getGUI();
        gui.getFileController().markAsEdited(gui);
    }

    public void exportDirectoryHandler(Label exportPathLabel) {
        // PROMPT THE USER FOR A DIRECETORY
        DirectoryChooser dc = new DirectoryChooser();
        dc.setInitialDirectory(new File(".."));
        dc.setTitle("Choose Export Directory");

        String exportPathLabelText = dc.showDialog(app.getGUI().getWindow()).toString();
        exportPathLabel.setText(exportPathLabelText);

        CSGData data = (CSGData) app.getDataComponent();
        data.setExportDir(exportPathLabelText);
    }

    public void templateDirectoryHandler(Label templateDirLabel) {
        // PROMPT THE USER FOR A DIRECETORY
        DirectoryChooser dc = new DirectoryChooser();
        dc.setInitialDirectory(new File(".."));
        dc.setTitle("Choose Template Directory");

        String templatePathLabelText = dc.showDialog(app.getGUI().getWindow()).toString();
        templateDirLabel.setText(templatePathLabelText);

        CSGData data = (CSGData) app.getDataComponent();
        data.setTemplateDir(templatePathLabelText);

        data.setSitePages(templatePathLabelText);

    }

    public void bannerImageHandler(ImageView bannerImageView) {

        try {
            // PROMPT THE USER FOR A DIRECETORY
            FileChooser fc = new FileChooser();
            fc.setInitialDirectory(new File(".."));
            fc.setTitle("Choose Banner School Image");

            //Set extension filter
            FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            fc.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

            File bannerImage = fc.showOpenDialog(app.getGUI().getWindow());

            CSGData data = (CSGData) app.getDataComponent();
            BufferedImage bi = ImageIO.read(bannerImage);
            Image image = SwingFXUtils.toFXImage(bi, null);
            bannerImageView.setImage(image);
            data.setBannerSchoolImage(bannerImage.toString());

        } catch (IOException ioe) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show("Banner Image Error", "An error occured importing image");

        }

    }

    public void leftFooterHandler(ImageView leftFooterImageView) {

        try {
            // PROMPT THE USER FOR A DIRECETORY
            FileChooser fc = new FileChooser();
            fc.setInitialDirectory(new File(".."));
            fc.setTitle("Choose Left Footer School Image");

            //Set extension filter
            FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            fc.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

            File leftFooter = fc.showOpenDialog(app.getGUI().getWindow());

            CSGData data = (CSGData) app.getDataComponent();
            BufferedImage bi = ImageIO.read(leftFooter);
            Image image = SwingFXUtils.toFXImage(bi, null);
            leftFooterImageView.setImage(image);
            data.setLeftFooterImage(leftFooter.toString());

        } catch (IOException ioe) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show("Left Footer Image Error", "An error occured importing image");

        }
    }

    public void rightFooterHandler(ImageView rightFooterImageView) {
        try {
            // PROMPT THE USER FOR A DIRECETORY
            FileChooser fc = new FileChooser();
            fc.setInitialDirectory(new File(".."));
            fc.setTitle("Choose Right Footer School Image");

            //Set extension filter
            FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            fc.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

            File rightFooter = fc.showOpenDialog(app.getGUI().getWindow());

            CSGData data = (CSGData) app.getDataComponent();
            BufferedImage bi = ImageIO.read(rightFooter);
            Image image = SwingFXUtils.toFXImage(bi, null);
            rightFooterImageView.setImage(image);
            data.setRightFooterImage(rightFooter.toString());

        } catch (IOException ioe) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show("Right Footer Image Error", "An error occured importing image");

        }

    }

    /**
     * This method responds to when the user requests to add a new TA via the
     * UI. Note that it must first do some validation to make sure a unique name
     * and email address has been provided.
     */
    public void handleAddTA() {
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher;

        // WE'LL NEED THE WORKSPACE TO RETRIEVE THE USER INPUT VALUES
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TextField nameTextField = workspace.getTADataTab().getNameTextField();
        TextField emailTextField = workspace.getTADataTab().getEmailTextField();
        String name = nameTextField.getText();
        String email = emailTextField.getText();

        // CHECK EMAIL IS VALID
        matcher = pattern.matcher(email);

        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        CSGData data = (CSGData) app.getDataComponent();

        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        // DID THE USER NEGLECT TO PROVIDE A TA NAME?
        if (name.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));
        } // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
        else if (email.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
        } // DID THE USER PROVIDE A VALID EMAIL?
        else if (!matcher.matches()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(INVALID_TA_EMAIL_TITLE), props.getProperty(INVALID_TA_EMAIL_MESSAGE));
        } // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
        else if (data.containsTA(name, email)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE), props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE));
        } // EVERYTHING IS FINE, ADD A NEW TA
        else {
            // ADD THE NEW TA TO THE DATA
            data.addTA(name, email);

            // CLEAR THE TEXT FIELDS
            nameTextField.setText("");
            emailTextField.setText("");

            // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
            nameTextField.requestFocus();

            // WE'VE CHANGED STUFF
            markWorkAsEdited();

            jTPS_Transaction trans = new addTATransaction(name, email, data);
            jTPS.addTransaction(trans);
        }
    }

    public void handleClear() {
        // WE'LL NEED THE WORKSPACE TO CLEAR TEXTFIELD VALUES
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TextField nameTextField = workspace.getTADataTab().getNameTextField();
        TextField emailTextField = workspace.getTADataTab().getEmailTextField();

        if (!(nameTextField.getText().equals("")) && !(emailTextField.getText().equals(""))) {

            nameTextField.setText("");
            emailTextField.setText("");

            // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
            nameTextField.requestFocus();

            //  CHANGE BUTTON BACK TO ADD
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            String addButtonText = props.getProperty(CSGManagerProp.ADD_BUTTON_TEXT.toString());
            Button addButton = workspace.getTADataTab().getAddButton();
            addButton.setText(addButtonText);

            //  HANDLE UPDATE TA
            addButton.setOnAction(e -> {
                handleAddTA();
            });
        } else {
            Button clearButton = workspace.getTADataTab().getClearButton();
            clearButton.setDisable(true);
        }

    }

    public void handleStartMon(LocalDate date) {
        CSGData data = (CSGData) app.getDataComponent();
        data.setStartingMonday(date.toString());
    }

    public void handleEndFri(LocalDate date) {
        CSGData data = (CSGData) app.getDataComponent();
        data.setEndingFriday(date.toString());
    }

    public void handleEditTA() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TableView taTable = workspace.getTADataTab().getTATable();

        // GET THE TA
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            TeachingAssistant ta = (TeachingAssistant) selectedItem;
            String taName = ta.getName();

            //  EDIT TA OPTION IN TEXTFIELDS
            TextField nameTextField = workspace.getTADataTab().getNameTextField();
            TextField emailTextField = workspace.getTADataTab().getEmailTextField();
            String taEmail = ta.getEmail();
            nameTextField.setText(taName);
            emailTextField.setText(taEmail);

            //  CHANGE BUTTON TO UPDATE TA
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            String updateButtonText = props.getProperty(CSGManagerProp.UPDATE_BUTTON_TEXT.toString());
            Button updateButton = workspace.getTADataTab().getAddButton();
            updateButton.setText(updateButtonText);

            // MAKE SURE THE CLEAR BUTTON IS ENABLED
            Button clearButton = workspace.getTADataTab().getClearButton();
            if (clearButton.isDisable()) {
                clearButton.setDisable(false);
            }

            //  HANDLE UPDATE TA
            updateButton.setOnAction(e -> {
                handleUpdateTA(ta);
            });
        }
    }
    public void handleClearRecitation(){
        
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TextField sectionTextField = workspace.getRecitationDataTab().getSectionTextField();
        TextField instructorTextField = workspace.getRecitationDataTab().getInstructorTextField();
        TextField dayTimeTextField = workspace.getRecitationDataTab().getDayTimeTextField();
        TextField locationTextField = workspace.getRecitationDataTab().getLocationTextField();
        
        if(!sectionTextField.getText().equals("") && !instructorTextField.getText().equals("")
                && !dayTimeTextField.getText().equals("")){
         // CLEAR THE TEXT FIELDS
            sectionTextField.setText("");
            instructorTextField.setText("");
            dayTimeTextField.setText("");
            locationTextField.setText("");
            
            sectionTextField.requestFocus();
            
            //  CHANGE BUTTON BACK TO ADD
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            String addButtonText = props.getProperty(CSGManagerProp.ADD_BUTTON_TEXT.toString());
            Button addButton = workspace.getRecitationDataTab().getAddUpdateButton();
            addButton.setText(addButtonText);

            //  HANDLE UPDATE TA
            addButton.setOnAction(e -> {
                addRecHandler();
            });
    } else {
            Button clearButton = workspace.getRecitationDataTab().getClearButton();
            clearButton.setDisable(true);
        }
    }
    
    public void handleClearSchedule(){
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        ComboBox typeComboBox = workspace.getScheduleDataTab().getTypeComboBox();
        
        TextField timeTextField = workspace.getScheduleDataTab().getTimeTextField();
        TextField titleTextField = workspace.getScheduleDataTab().getTitleTextField();
        TextField topicTextField = workspace.getScheduleDataTab().getTopicTextField();
        TextField linkTextField = workspace.getScheduleDataTab().getLinkTextField();
        TextField criteriaTextField = workspace.getScheduleDataTab().getCriteriaTextField();
        
        if(!titleTextField.getText().equals("")){
         // CLEAR THE TEXT FIELDS
            typeComboBox.getSelectionModel().clearSelection();
            timeTextField.setText("");
            titleTextField.setText("");
            topicTextField.setText("");
            linkTextField.setText("");
            criteriaTextField.setText("");
            
            titleTextField.requestFocus();
            
            //  CHANGE BUTTON BACK TO ADD
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            String addButtonText = props.getProperty(CSGManagerProp.ADD_BUTTON_TEXT.toString());
            Button addButton = workspace.getScheduleDataTab().getAddUpdateButton();
            addButton.setText(addButtonText);

            //  HANDLE UPDATE TA
            addButton.setOnAction(e -> {
                addScheduleHandler();
            });
    } else {
            Button clearButton = workspace.getScheduleDataTab().getClearButton();
            clearButton.setDisable(true);
        }
    }
    
    public void handleEditSchedule() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TableView scheduleTable = workspace.getScheduleDataTab().getScheduleTable();

        // GET THE TA
        Object selectedItem = scheduleTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Schedule schedule = (Schedule) selectedItem;
            String time = schedule.getTime();
            String type = schedule.getType();
            LocalDate date = schedule.getLocalDate();
            String title = schedule.getTitle();
            String topic = schedule.getTopic();
            String link = schedule.getLink();
            String criteria = schedule.getCriteria();

            ComboBox typeComboBox = workspace.getScheduleDataTab().getTypeComboBox();
            DatePicker datePicker = workspace.getScheduleDataTab().getDateDatePicker();
            TextField timeTextField = workspace.getScheduleDataTab().getTimeTextField();
            TextField titleTextField = workspace.getScheduleDataTab().getTitleTextField();
            TextField topicTextField = workspace.getScheduleDataTab().getTopicTextField();
            TextField linkTextField = workspace.getScheduleDataTab().getLinkTextField();
            TextField criteriaTextField = workspace.getScheduleDataTab().getCriteriaTextField();

            timeTextField.setText(time);
            typeComboBox.setValue(type);
            datePicker.setValue(date);
            titleTextField.setText(title);
            topicTextField.setText(topic);
            linkTextField.setText(link);
            criteriaTextField.setText(criteria);

            //  CHANGE BUTTON TO UPDATE TA
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            String updateButtonText = props.getProperty(CSGManagerProp.UPDATE_BUTTON_TEXT.toString());
            Button updateButton = workspace.getScheduleDataTab().getAddUpdateButton();
            updateButton.setText(updateButtonText);

            // MAKE SURE THE CLEAR BUTTON IS ENABLED
            Button clearButton = workspace.getScheduleDataTab().getClearButton();
            if (clearButton.isDisable()) {
                clearButton.setDisable(false);
            }

            //  HANDLE UPDATE TA
            updateButton.setOnAction(e -> {
                handleUpdateSchedule(schedule);
            });
        }
    }

    public void handleUpdateSchedule(Schedule schedule) {
        // WE'LL NEED THE WORKSPACE TO RETRIEVE THE USER INPUT VALUES
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        ComboBox typeComboBox = workspace.getScheduleDataTab().getTypeComboBox();
        DatePicker datePicker = workspace.getScheduleDataTab().getDateDatePicker();
        TextField timeTextField = workspace.getScheduleDataTab().getTimeTextField();
        TextField titleTextField = workspace.getScheduleDataTab().getTitleTextField();
        TextField topicTextField = workspace.getScheduleDataTab().getTopicTextField();
        TextField linkTextField = workspace.getScheduleDataTab().getLinkTextField();
        TextField criteriaTextField = workspace.getScheduleDataTab().getCriteriaTextField();

        String time = timeTextField.getText();
        String type = typeComboBox.getEditor().getText();
        String date = datePicker.getValue().toString();
        String title = titleTextField.getText();
        String topic = topicTextField.getText();
        String link = linkTextField.getText();
        String criteria = criteriaTextField.getText();

        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        CSGData data = (CSGData) app.getDataComponent();

        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        // DID THE USER NEGLECT TO PROVIDE A TA NAME?
        if (type.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show("Missing Type", "Add a type");
        } // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
        else if (date.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show("Missing Date", "Add date");
        } // DID THE USER PROVIDE A VALID EMAIL?
        else if (title.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show("Missing Title", "Add a Title");
        } 
         
         // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
        // EVERYTHING IS FINE, ADD A NEW Schedule
        else {

            //add transaction
            jTPS_Transaction trans = new updateScheduleTrans(schedule.getType(), schedule.getDate(), 
                    schedule.getTitle(), schedule.getTopic(),schedule.getLink(), schedule.getTime(), 
                    schedule.getCriteria(), data ,type, date, title, topic, link, time, criteria);
            jTPS.addTransaction(trans);

            // CHANGE BUTTON BACK TO ADD
            String addButtonText = props.getProperty(CSGManagerProp.ADD_BUTTON_TEXT.toString());
            Button addButton = workspace.getScheduleDataTab().getAddUpdateButton();
            addButton.setText(addButtonText);

            // CLEAR THE TEXT FIELDS
            typeComboBox.getSelectionModel().clearSelection();
            timeTextField.setText("");
            titleTextField.setText("");
            topicTextField.setText("");
            linkTextField.setText("");
            criteriaTextField.setText("");

            // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
            titleTextField.requestFocus();

            // WE'VE CHANGED STUFF
            markWorkAsEdited();
        }
    }

    public void handleEditRec() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TableView recTable = workspace.getRecitationDataTab().getRecitationTable();

        // GET THE TA
        Object selectedItem = recTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Recitation rec = (Recitation) selectedItem;
            String section = rec.getSection();
            String instructor = rec.getInstructor();
            String dayTime = rec.getDayTime();
            String location = rec.getLocation();
            String ta1 = rec.getTa1();
            String ta2 = rec.getTa2();

            TextField sectionTextField = workspace.getRecitationDataTab().getSectionTextField();
            TextField instructorTextField = workspace.getRecitationDataTab().getInstructorTextField();
            TextField dayTimeTextField = workspace.getRecitationDataTab().getDayTimeTextField();
            TextField locationTextField = workspace.getRecitationDataTab().getLocationTextField();
            ComboBox ta1ChoiceBox = workspace.getRecitationDataTab().getSupervisingTA1ComboBox();
            ComboBox ta2ChoiceBox = workspace.getRecitationDataTab().getSupervisingTA2ComboBox();

            sectionTextField.setText(section);
            instructorTextField.setText(instructor);
            dayTimeTextField.setText(dayTime);
            locationTextField.setText(location);
            ta1ChoiceBox.setValue(ta1);
            ta2ChoiceBox.setValue(ta2);

            //  CHANGE BUTTON TO UPDATE TA
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            String updateButtonText = props.getProperty(CSGManagerProp.UPDATE_BUTTON_TEXT.toString());
            Button updateButton = workspace.getRecitationDataTab().getAddUpdateButton();
            updateButton.setText(updateButtonText);

            // MAKE SURE THE CLEAR BUTTON IS ENABLED
            Button clearButton = workspace.getRecitationDataTab().getClearButton();
            if (clearButton.isDisable()) {
                clearButton.setDisable(false);
            }

            //  HANDLE UPDATE TA
            updateButton.setOnAction(e -> {
                handleUpdateRec(rec);
            });
        }

    }

    public void handleUpdateRec(Recitation rec) {
        // WE'LL NEED THE WORKSPACE TO RETRIEVE THE USER INPUT VALUES
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TextField sectionTextField = workspace.getRecitationDataTab().getSectionTextField();
        TextField instructorTextField = workspace.getRecitationDataTab().getInstructorTextField();
        TextField dayTimeTextField = workspace.getRecitationDataTab().getDayTimeTextField();
        TextField locationTextField = workspace.getRecitationDataTab().getLocationTextField();
        ComboBox ta1ChoiceBox = workspace.getRecitationDataTab().getSupervisingTA1ComboBox();
        ComboBox ta2ChoiceBox = workspace.getRecitationDataTab().getSupervisingTA2ComboBox();

        String section = sectionTextField.getText();
        String instructor = instructorTextField.getText();
        String dayTime = dayTimeTextField.getText();
        String location = locationTextField.getText();
        String ta1 = ta1ChoiceBox.getEditor().getText();
        String ta2 = ta2ChoiceBox.getEditor().getText();

        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        CSGData data = (CSGData) app.getDataComponent();

        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        // DID THE USER NEGLECT TO PROVIDE A TA NAME?
        if (section.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show("Missing Section", "Add a section");
        } // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
        else if (instructor.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show("Missing Instructor", "Add instructor");
        } // DID THE USER PROVIDE A VALID EMAIL?
        else if (dayTime.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show("Missing Day Time", "Add a day and time");
        } else if (location.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show("Missing Location", "Add a location");
        } // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
        else if (data.containsRecitation(section, instructor, dayTime, location, ta1, ta2)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show("Not Unique Recitation", "Add a different recitation");
        } // EVERYTHING IS FINE, ADD A NEW TA
        else {

            // add trans
            jTPS_Transaction trans = new updateRecTrans(section, instructor,
                    dayTime, location, ta1, ta2, rec.getSection(), rec.getInstructor(),
                    rec.getDayTime(), rec.getLocation(), rec.getTa1(), rec.getTa2(), data);
            jTPS.addTransaction(trans);

            // CHANGE BUTTON BACK TO ADD
            String addButtonText = props.getProperty(CSGManagerProp.ADD_BUTTON_TEXT.toString());
            Button addButton = workspace.getRecitationDataTab().getAddUpdateButton();
            addButton.setText(addButtonText);

            // CLEAR THE TEXT FIELDS
            sectionTextField.setText("");
            instructorTextField.setText("");
            dayTimeTextField.setText("");
            locationTextField.setText("");

            // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
            sectionTextField.requestFocus();

            // WE'VE CHANGED STUFF
            markWorkAsEdited();

        }

    }

    public void handleUpdateTA(TeachingAssistant ta) {
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher;

        //  GET UPDATED INFO FOR TA
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TextField nameTextField = workspace.getTADataTab().getNameTextField();
        TextField emailTextField = workspace.getTADataTab().getEmailTextField();
        String name = nameTextField.getText();
        String email = emailTextField.getText();

        // CHECK EMAIL IS VALID
        matcher = pattern.matcher(email);

        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        CSGData data = (CSGData) app.getDataComponent();

        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        // DID THE USER NEGLECT TO PROVIDE A TA NAME?
        if (name.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));
        } // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
        else if (email.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
        } // DID THE USER PROVIDE A VALID EMAIL?
        else if (!matcher.matches()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(INVALID_TA_EMAIL_TITLE), props.getProperty(INVALID_TA_EMAIL_MESSAGE));
        } // EVERYTHING IS FINE, ADD A NEW TA
        else {
            //  EDIT TA NAME IN TA'S HOURS
            HashMap<String, Label> labels = workspace.getTADataTab().getOfficeHoursGridTACellLabels();
            String taOrigName = ta.getName();

            //  CREATE TRANSACTION
            jTPS_Transaction trans = new updateTATrans(taOrigName, name, ta.getEmail(), labels, data);
            jTPS.addTransaction(trans);

            for (Label label : labels.values()) {
                if (label.getText().equals(taOrigName)
                        || (label.getText().contains(taOrigName + "\n"))
                        || (label.getText().contains("\n" + taOrigName))) {
                    data.updateTAFromCell(label.textProperty(), taOrigName, name);
                }
            }
            //  GO UPDATE TA
            data.removeTA(ta.getName());
            data.addTA(name, email);

            // CHANGE BUTTON BACK TO ADD
            String addButtonText = props.getProperty(CSGManagerProp.ADD_BUTTON_TEXT.toString());
            Button addButton = workspace.getTADataTab().getAddButton();
            addButton.setText(addButtonText);

            // CLEAR OUT TEXTFIELDS
            nameTextField.setText("");
            emailTextField.setText("");

            // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
            nameTextField.requestFocus();

            // WE'VE CHANGED STUFF
            markWorkAsEdited();

        }
    }

    /**
     * This function provides a response for when the user presses a keyboard
     * key. Note that we're only responding to Delete, to remove a TA.
     *
     * @param code The keyboard code pressed.
     */
    public void handleKeyPress(KeyCode code) {
        // DID THE USER PRESS THE DELETE KEY?
        if (code == KeyCode.DELETE || code == KeyCode.BACK_SPACE) {
            // GET THE TABLE
            CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
            TableView taTable = workspace.getTADataTab().getTATable();

            // IS A TA SELECTED IN THE TABLE?
            Object selectedItem = taTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {

                // GET THE TA AND REMOVE IT
                TeachingAssistant ta = (TeachingAssistant) selectedItem;
                String taName = ta.getName();
                CSGData data = (CSGData) app.getDataComponent();

                ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(data);
                jTPS_Transaction transaction = new deleteTATransaction(taName, ta.getEmail(), data, workspace, officeHours);

                data.removeTA(taName);

                // AND BE SURE TO REMOVE ALL THE TA'S OFFICE HOURS
                HashMap<String, Label> labels = workspace.getTADataTab().getOfficeHoursGridTACellLabels();
                for (Label label : labels.values()) {
                    if (label.getText().equals(taName)
                            || (label.getText().contains(taName + "\n"))
                            || (label.getText().contains("\n" + taName))) {
                        data.removeTAFromCell(label.textProperty(), taName);
                    }
                }
                // WE'VE CHANGED STUFF
                markWorkAsEdited();
                jTPS.addTransaction(transaction);

            }
        }
    }

    public void handleDeleteRecitation() {

        // GET THE TABLE
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TableView recTable = workspace.getRecitationDataTab().getRecitationTable();

        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = recTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {

            // GET THE TA AND REMOVE IT
            Recitation rec = (Recitation) selectedItem;
            String section = rec.getSection();
            CSGData data = (CSGData) app.getDataComponent();

            jTPS_Transaction transaction = new deleteRecTransaction(section, rec.getInstructor(),
                    rec.getDayTime(), rec.getLocation(), rec.getTa1(), rec.getTa2(), data);

            // WE'VE CHANGED STUFF
            markWorkAsEdited();
            jTPS.addTransaction(transaction);

        }

    }

    public void deleteScheduleHandler() {
        // GET THE TABLE
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TableView scheduleTable = workspace.getScheduleDataTab().getScheduleTable();

        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = scheduleTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {

            // GET THE TA AND REMOVE IT
            Schedule schedule = (Schedule) selectedItem;
            String date = schedule.getDate();
            CSGData data = (CSGData) app.getDataComponent();

            jTPS_Transaction transaction = new deleteScheduleTransaction(schedule.getType(), date,
                    schedule.getTitle(), schedule.getTopic(), schedule.getLink(),
                    schedule.getTime(), schedule.getCriteria(), data);

            // WE'VE CHANGED STUFF
            markWorkAsEdited();
            jTPS.addTransaction(transaction);

        }

    }

    public void addRecHandler() {
        // WE'LL NEED THE WORKSPACE TO RETRIEVE THE USER INPUT VALUES
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TextField sectionTextField = workspace.getRecitationDataTab().getSectionTextField();
        TextField instructorTextField = workspace.getRecitationDataTab().getInstructorTextField();
        TextField dayTimeTextField = workspace.getRecitationDataTab().getDayTimeTextField();
        TextField locationTextField = workspace.getRecitationDataTab().getLocationTextField();
        ComboBox ta1ChoiceBox = workspace.getRecitationDataTab().getSupervisingTA1ComboBox();
        ComboBox ta2ChoiceBox = workspace.getRecitationDataTab().getSupervisingTA2ComboBox();

        String section = sectionTextField.getText();
        String instructor = instructorTextField.getText();
        String dayTime = dayTimeTextField.getText();
        String location = locationTextField.getText();
        String ta1 = ta1ChoiceBox.getEditor().getText();
        String ta2 = ta2ChoiceBox.getEditor().getText();

        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        CSGData data = (CSGData) app.getDataComponent();

        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        // DID THE USER NEGLECT TO PROVIDE A TA NAME?
        if (section.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show("Missing Section", "Add a section");
        } // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
        else if (instructor.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show("Missing Instructor", "Add instructor");
        } // DID THE USER PROVIDE A VALID EMAIL?
        else if (dayTime.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show("Missing Day Time", "Add a day and time");
        } else if (location.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show("Missing Location", "Add a location");
        
        } // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
        else if (data.containsRecitation(section, instructor, dayTime, location, ta1, ta2)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show("Not Unique Recitation", "Add a different recitation");
        } // EVERYTHING IS FINE, ADD A NEW TA
        else {

            // CLEAR THE TEXT FIELDS
            sectionTextField.setText("");
            instructorTextField.setText("");
            dayTimeTextField.setText("");
            locationTextField.setText("");

            // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
            sectionTextField.requestFocus();

            // WE'VE CHANGED STUFF
            markWorkAsEdited();

            jTPS_Transaction trans = new addRecitationTransaction(section, instructor,
                    dayTime, location, ta1, ta2, data);
            jTPS.addTransaction(trans);
        }

    }

    public void addScheduleHandler() {
        // WE'LL NEED THE WORKSPACE TO RETRIEVE THE USER INPUT VALUES
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        ComboBox typeComboBox = workspace.getScheduleDataTab().getTypeComboBox();
        DatePicker datePicker = workspace.getScheduleDataTab().getDateDatePicker();
        TextField timeTextField = workspace.getScheduleDataTab().getTimeTextField();
        TextField titleTextField = workspace.getScheduleDataTab().getTitleTextField();
        TextField topicTextField = workspace.getScheduleDataTab().getTopicTextField();
        TextField linkTextField = workspace.getScheduleDataTab().getLinkTextField();
        TextField criteriaTextField = workspace.getScheduleDataTab().getCriteriaTextField();

        String time = timeTextField.getText();
        String type = typeComboBox.getEditor().getText();
        String date = datePicker.getValue().toString();
        String title = titleTextField.getText();
        String topic = topicTextField.getText();
        String link = linkTextField.getText();
        String criteria = criteriaTextField.getText();

        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        CSGData data = (CSGData) app.getDataComponent();

        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        // DID THE USER NEGLECT TO PROVIDE A TA NAME?
        if (type.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show("Missing Type", "Add a type");
        } // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
        else if (date.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show("Missing Date", "Add date");
        } // DID THE USER PROVIDE A VALID EMAIL?
        else if (title.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show("Missing Title", "Add a Title");
        } 
        else if (link.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show("Missing Link", "Add Link");
        } 
         // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
        // EVERYTHING IS FINE, ADD A NEW Schedule
        else {

            //add transaction
            jTPS_Transaction trans = new addScheduleTrans(type, date, title, topic, link, time, criteria, data);
            jTPS.addTransaction(trans);

            // CLEAR THE TEXT FIELDS
            typeComboBox.getSelectionModel().clearSelection();
            titleTextField.setText("");
            timeTextField.setText("");
            topicTextField.setText("");
            linkTextField.setText("");
            criteriaTextField.setText("");

            // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
            titleTextField.requestFocus();

            // WE'VE CHANGED STUFF
            markWorkAsEdited();

        }

    }

    /**
     * This function provides a response for when the user clicks on the office
     * hours grid to add or remove a TA to a time slot.
     *
     * @param pane The pane that was toggled.
     */
    public void handleCellToggle(Pane pane) {
        // GET THE TABLE
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TableView taTable = workspace.getTADataTab().getTATable();

        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // GET THE TA
            TeachingAssistant ta = (TeachingAssistant) selectedItem;
            String taName = ta.getName();
            CSGData data = (CSGData) app.getDataComponent();
            String cellKey = pane.getId();

            // AND TOGGLE THE OFFICE HOURS IN THE CLICKED CELL
            //           data.toggleTAOfficeHours(cellKey, taName);
            // CREATE TRANSACTION
            jTPS_Transaction trans = new cellToggleTrans(taName, cellKey, data);
            jTPS.addTransaction(trans);

            // WE'VE CHANGED STUFF
            markWorkAsEdited();
        }
    }

    void handleGridCellMouseExited(Pane pane) {
        String cellKey = pane.getId();
        CSGData data = (CSGData) app.getDataComponent();
        int column = Integer.parseInt(cellKey.substring(0, cellKey.indexOf("_")));
        int row = Integer.parseInt(cellKey.substring(cellKey.indexOf("_") + 1));
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();

        Pane mousedOverPane = workspace.getTADataTab().getTACellPane(data.getCellKey(column, row));
        mousedOverPane.getStyleClass().clear();
        mousedOverPane.getStyleClass().add(CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);

        // THE MOUSED OVER COLUMN HEADER
        Pane headerPane = workspace.getTADataTab().getOfficeHoursGridDayHeaderPanes().get(data.getCellKey(column, 0));
        headerPane.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);

        // THE MOUSED OVER ROW HEADERS
        headerPane = workspace.getTADataTab().getOfficeHoursGridTimeCellPanes().get(data.getCellKey(0, row));
        headerPane.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        headerPane = workspace.getTADataTab().getOfficeHoursGridTimeCellPanes().get(data.getCellKey(1, row));
        headerPane.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);

        // AND NOW UPDATE ALL THE CELLS IN THE SAME ROW TO THE LEFT
        for (int i = 2; i < column; i++) {
            cellKey = data.getCellKey(i, row);
            Pane cell = workspace.getTADataTab().getTACellPane(cellKey);
            cell.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
            cell.getStyleClass().add(CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);
        }

        // AND THE CELLS IN THE SAME COLUMN ABOVE
        for (int i = 1; i < row; i++) {
            cellKey = data.getCellKey(column, i);
            Pane cell = workspace.getTADataTab().getTACellPane(cellKey);
            cell.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
            cell.getStyleClass().add(CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);
        }
    }

    void handleGridCellMouseEntered(Pane pane) {
        String cellKey = pane.getId();
        CSGData data = (CSGData) app.getDataComponent();
        int column = Integer.parseInt(cellKey.substring(0, cellKey.indexOf("_")));
        int row = Integer.parseInt(cellKey.substring(cellKey.indexOf("_") + 1));
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();

        // THE MOUSED OVER PANE
        Pane mousedOverPane = workspace.getTADataTab().getTACellPane(data.getCellKey(column, row));
        mousedOverPane.getStyleClass().clear();
        mousedOverPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_CELL);

        // THE MOUSED OVER COLUMN HEADER
        Pane headerPane = workspace.getTADataTab().getOfficeHoursGridDayHeaderPanes().get(data.getCellKey(column, 0));
        headerPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);

        // THE MOUSED OVER ROW HEADERS
        headerPane = workspace.getTADataTab().getOfficeHoursGridTimeCellPanes().get(data.getCellKey(0, row));
        headerPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        headerPane = workspace.getTADataTab().getOfficeHoursGridTimeCellPanes().get(data.getCellKey(1, row));
        headerPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);

        // AND NOW UPDATE ALL THE CELLS IN THE SAME ROW TO THE LEFT
        for (int i = 2; i < column; i++) {
            cellKey = data.getCellKey(i, row);
            Pane cell = workspace.getTADataTab().getTACellPane(cellKey);
            cell.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        }

        // AND THE CELLS IN THE SAME COLUMN ABOVE
        for (int i = 1; i < row; i++) {
            cellKey = data.getCellKey(column, i);
            Pane cell = workspace.getTADataTab().getTACellPane(cellKey);
            cell.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        }
    }

    public void handleStartHour(String startHour) {
        CSGData data = (CSGData) app.getDataComponent();

        int colonIndex = startHour.indexOf(":");
        String startHourValue = startHour.substring(0, colonIndex);
        String origStartHour = String.valueOf(data.getStartHour());

        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();

        //  SAVE CURRENT OFFICE HOURS
        ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(data);

        jTPS_Transaction trans = new startHourTrans(data, workspace,
                startHourValue, origStartHour, officeHours);
        jTPS.addTransaction(trans);

        markWorkAsEdited();

    }

    public void handleEndHour(String endHour) {
        CSGData data = (CSGData) app.getDataComponent();

        int colonIndex = endHour.indexOf(":");
        String endHourValue = endHour.substring(0, colonIndex);
        String origEndHour = String.valueOf(data.getEndHour());

        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();

        //  SAVE CURRENT OFFICE HOURS
        ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(data);

        jTPS_Transaction trans = new endHourTrans(data, workspace, endHourValue,
                officeHours, origEndHour);
        jTPS.addTransaction(trans);

        markWorkAsEdited();

    }

    public void handleUndo() {
        jTPS.undoTransaction();
    }

    public void handleRedo() {
        jTPS.doTransaction();
    }

    void handleChangeTime(String startTime, String endTime) {
        //TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        // WARNING MESSAGE
        AppYesNoCancelDialogSingleton yesNoDialog = AppYesNoCancelDialogSingleton.getSingleton();
        yesNoDialog.show(props.getProperty(WARNING_TIME_TITLE), props.getProperty(WARNING_TIME_MESSAGE));

        // AND NOW GET THE USER'S SELECTION
        String selection = yesNoDialog.getSelection();
        if (selection.equals(AppYesNoCancelDialogSingleton.YES)) {

            int start = convertToMilitaryTime(startTime);
            int end = convertToMilitaryTime(endTime);
            System.out.println(start);

            //TAWorkspace workspace = (TAWorkspace)app.getDataComponent();
            if (start == end || start == -1 || end == -1) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(INVALID_TIME_TITLE), props.getProperty(INVALID_TIME_MESSAGE));       //REMEMBER TO CHANGE TO PROPER ERROR MESSAGE                              

            } else if (start > end) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(INVALID_TIME_TITLE), props.getProperty(INVALID_TIME_MESSAGE));       //REMEMBER TO CHANGE TO PROPER ERROR MESSAGE                              

            } else if (end < start) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(INVALID_TIME_TITLE), props.getProperty(INVALID_TIME_MESSAGE));       //REMEMBER TO CHANGE TO PROPER ERROR MESSAGE                              

            } else {    //At this point the time varialbes are good to go. 
                CSGData data = (CSGData) app.getDataComponent();

                jTPS_Transaction transaction = new updateTime_Transaction(start, end, data);
                jTPS.addTransaction(transaction);

                //workspace.resetWorkspace(); 
                //workspace.reloadWorkspace(oldData);
                markWorkAsEdited();
                //workspace.reloadOfficeHoursGrid(data);
            }
        }

    }

    public int convertToMilitaryTime(String time) {
        int milTime = 0;
        if (time == null) {
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(INVALID_TA_EMAIL_TITLE), props.getProperty(INVALID_TA_EMAIL_MESSAGE));       //REMEMBER TO CHANGE TO PROPER ERROR MESSAGE                              
        } else if (time.equalsIgnoreCase("12:00pm")) {
            milTime = 12;
        } else {
            int index = time.indexOf(":");
            String subStringTime = time.substring(0, index);
            milTime = Integer.parseInt(subStringTime);
            if (time.contains("p")) {
                milTime += 12;
            }
        }
        return milTime;
    }

}
