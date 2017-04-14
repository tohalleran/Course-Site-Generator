/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspaces;

import csg.CSGManagerApp;
import csg.CSGManagerProp;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;

/**
 *
 * @author tonyohalleran
 */
public class RecitationDataTab {

    CSGManagerApp app;

    VBox recitationDataWorkspace;

    Label recitationsLabel;
    Button dashButton;
    HBox recitationLabelButton;
    
    TableView recitationTable;
    TableColumn sectionColumn;
    TableColumn instructorColumn;
    TableColumn dayTimeColumn;
    TableColumn locationColumn;
    TableColumn ta1Column;
    TableColumn ta2Column;

    //BOTTOM PANE - ADD/EDIT RECITATION
    Label addEditLabel;
    Label sectionLabel;
    TextField sectionTextField;
    Label instructorLabel;
    TextField instructorTextField;
    Label dayTimeLabel;
    TextField dayTimeTextField;
    Label locationLabel;
    TextField locationTextField;
    Label supervisingTA1Label;
    ComboBox supervisingTA1ComboBox;
    Label supervisingTA2Label;
    ComboBox supervisingTA2ComboBox;
    Button addUpdateButton;
    Button clearButton;

    GridPane addEditGridPane;

    public RecitationDataTab(CSGManagerApp initapp) {
        app = initapp;

        // WE'LL NEED THIS TO GET LANGUAGE PROPERTIES FOR OUR UI
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        String recitationsLabelText = props.getProperty(CSGManagerProp.RECITATIONS_LABEL_TEXT.toString());
        recitationsLabel = new Label(recitationsLabelText);
        dashButton = new Button("-");
        recitationLabelButton = new HBox();
        recitationLabelButton.getChildren().addAll(recitationsLabel, dashButton);
        
        //RECITATION TABLE
        recitationTable = new TableView();
        
        
        String sectionColumnText = props.getProperty(CSGManagerProp.SECTION_COLUMN_TEXT.toString());
        sectionColumn = new TableColumn(sectionColumnText);
        recitationTable.getColumns().add(sectionColumn);
        
        String instructorColumnText = props.getProperty(CSGManagerProp.INSTRUCTOR_COLUMN_TEXT.toString());
        instructorColumn = new TableColumn(instructorColumnText);
        recitationTable.getColumns().add(instructorColumn);

        String dayTimeColumnText = props.getProperty(CSGManagerProp.DAY_TIME_COLUMN_TEXT.toString());
        dayTimeColumn = new TableColumn(dayTimeColumnText);
        recitationTable.getColumns().add(dayTimeColumn);
        
        String locationColumnText = props.getProperty(CSGManagerProp.LOCATION_COLUMN_TEXT.toString());
        locationColumn = new TableColumn(locationColumnText);
        recitationTable.getColumns().add(locationColumn);
        
        ta1Column = new TableColumn("TA");
        ta2Column = new TableColumn("TA");
        recitationTable.getColumns().add(ta1Column);
        recitationTable.getColumns().add(ta2Column);
        

        //BOTTOM PANE - ADD/EDIT RECITATION
        addEditGridPane = new GridPane();
        
        String addEditLabelText = props.getProperty(CSGManagerProp.ADD_EDIT_LABEL_TEXT.toString());
        addEditLabel = new Label(addEditLabelText);
        addEditGridPane.add(addEditLabel, 0, 0);
        
        String sectionLabelText = props.getProperty(CSGManagerProp.SECTION_LABEL_TEXT.toString());
        sectionLabel = new Label(sectionLabelText);
        addEditGridPane.add(sectionLabel, 0 , 1);
        
        sectionTextField = new TextField();
        addEditGridPane.add(sectionTextField, 1, 1);
        
        String instructorLabelText = props.getProperty(CSGManagerProp.INSTRUCTOR_LABEL_TEXT.toString());
        instructorLabel = new Label(instructorLabelText);
        addEditGridPane.add(instructorLabel, 0, 2);
        
        instructorTextField = new TextField();
        addEditGridPane.add(instructorTextField, 1, 2);
        
        String dayTimeLabelText = props.getProperty(CSGManagerProp.DAY_TIME_LABEL_TEXT.toString());
        dayTimeLabel = new Label(dayTimeLabelText);
        addEditGridPane.add(dayTimeLabel, 0, 3);
        
        dayTimeTextField = new TextField();
        addEditGridPane.add(dayTimeTextField, 1,3);
        
        String locationLabelText = props.getProperty(CSGManagerProp.LOCATION_LABEL_TEXT.toString());
        locationLabel = new Label(locationLabelText);
        addEditGridPane.add(locationLabel, 0, 4);
        
        locationTextField = new TextField();
        addEditGridPane.add(locationTextField, 1, 4);
        
        String supervisingTALabelText = props.getProperty(CSGManagerProp.SUPERVISING_TA_LABEL_TEXT.toString());
        supervisingTA1Label = new Label(supervisingTALabelText);
        addEditGridPane.add(supervisingTA1Label, 0, 5);
        
        supervisingTA1ComboBox = new ComboBox();
        addEditGridPane.add(supervisingTA1ComboBox, 1, 5);
        
        supervisingTA2Label = new Label(supervisingTALabelText);
        addEditGridPane.add(supervisingTA2Label, 0, 6);
        
        supervisingTA2ComboBox = new ComboBox();
        addEditGridPane.add(supervisingTA2ComboBox, 1, 6);
        
        String addUpdateButtonText = props.getProperty(CSGManagerProp.ADD_UPDATE_BUTTON_TEXT.toString());
        addUpdateButton = new Button(addUpdateButtonText);
        addEditGridPane.add(addUpdateButton, 0, 7);
        
        String clearButtonText = props.getProperty(CSGManagerProp.CLEAR_BUTTON_TEXT.toString());
        clearButton = new Button(clearButtonText);
        addEditGridPane.add(clearButton, 1, 7);
        
        
        //PUT EVERYTHING IN WORKSPACE
        recitationDataWorkspace = new VBox(5);
        recitationDataWorkspace.getChildren().addAll(recitationLabelButton, recitationTable, addEditGridPane);

        recitationTable.prefHeightProperty().bind(recitationDataWorkspace.heightProperty().multiply(0.5));
    }

    public VBox getRecitationDataWorkspace() {
        return recitationDataWorkspace;
    }

    public Label getRecitationsLabel() {
        return recitationsLabel;
    }

    public Button getDashButton() {
        return dashButton;
    }

    public HBox getRecitationLabelButton() {
        return recitationLabelButton;
    }

    public TableView getRecitationTable() {
        return recitationTable;
    }

    public TableColumn getSectionColumn() {
        return sectionColumn;
    }

    public TableColumn getInstructorColumn() {
        return instructorColumn;
    }

    public TableColumn getDayTimeColumn() {
        return dayTimeColumn;
    }

    public TableColumn getLocationColumn() {
        return locationColumn;
    }

    public TableColumn getTa1Column() {
        return ta1Column;
    }

    public TableColumn getTa2Column() {
        return ta2Column;
    }

    public Label getAddEditLabel() {
        return addEditLabel;
    }

    public Label getSectionLabel() {
        return sectionLabel;
    }

    public TextField getSectionTextField() {
        return sectionTextField;
    }

    public Label getInstructorLabel() {
        return instructorLabel;
    }

    public TextField getInstructorTextField() {
        return instructorTextField;
    }

    public Label getDayTimeLabel() {
        return dayTimeLabel;
    }

    public TextField getDayTimeTextField() {
        return dayTimeTextField;
    }

    public Label getLocationLabel() {
        return locationLabel;
    }

    public TextField getLocationTextField() {
        return locationTextField;
    }

    public Label getSupervisingTA1Label() {
        return supervisingTA1Label;
    }

    public ComboBox getSupervisingTA1ComboBox() {
        return supervisingTA1ComboBox;
    }

    public Label getSupervisingTA2Label() {
        return supervisingTA2Label;
    }

    public ComboBox getSupervisingTA2ComboBox() {
        return supervisingTA2ComboBox;
    }

    public Button getAddUpdateButton() {
        return addUpdateButton;
    }

    public Button getClearButton() {
        return clearButton;
    }

    public GridPane getAddEditGridPane() {
        return addEditGridPane;
    }
    
    
}
