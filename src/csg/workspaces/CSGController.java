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
import csg.data.TeachingAssistant;
import csg.files.TimeSlot;
import csg.transaction.addTATransaction;
import csg.transaction.deleteTATransaction;
import csg.transaction.endHourTrans;
import csg.style.CSGStyle.*;
import static csg.style.CSGStyle.CLASS_HIGHLIGHTED_GRID_CELL;
import static csg.style.CSGStyle.CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN;
import static csg.style.CSGStyle.CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE;
import csg.transaction.cellToggleTrans;
import csg.transaction.startHourTrans;
import csg.transaction.updateTATrans;
import djf.ui.AppGUI;
import djf.ui.AppMessageDialogSingleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
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
    jTPS jTPS = new jTPS();

    /**
     * Constructor, note that the app must already be constructed.
     */
    public CSGController(CSGManagerApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;
    }

    /**
     * This helper method should be called every time an edit happens.
     */
    private void markWorkAsEdited() {
        // MARK WORK AS EDITED
        AppGUI gui = app.getGUI();
        gui.getFileController().markAsEdited(gui);
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
            if(clearButton.isDisable())
                clearButton.setDisable(false);
            
            //  HANDLE UPDATE TA
            updateButton.setOnAction(e -> {
                handleUpdateTA(ta);
            });
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

}
