/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspaces;

import csg.CSGManagerApp;
import csg.CSGManagerProp;
import csg.data.CSGData;
import csg.data.Schedule;
import java.time.LocalDate;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;

/**
 *
 * @author tonyohalleran
 */
public class ScheduleDataTab {

    CSGManagerApp app;
    CSGController controller;

    VBox scheduleDataWorkspace;

    Label scheduleLabel;

    //CALENDAR BOUNDARIES PANE
    GridPane calendarBoundariesGridPane;
    Label calendarBoundariesLabel;
    Label startingMondayLabel;
    DatePicker startingMondayDatePicker;
    Label endingFridayLabel;
    DatePicker endingFridayDatePicker;

    //SCHEDULE ITEMS PANE
    HBox scheduleItemsLabelButton;
    Label scheduleItemsLabel;
    Button dashButton;

    TableView scheduleTable;
    TableColumn typeColumn;
    TableColumn dateColumn;
    TableColumn titleColumn;
    TableColumn topicColumn;

    VBox addScheduleVBox;

    GridPane addEditScheduleGridPane;
    Label addEditLabel;
    Label typeLabel;
    ChoiceBox typeChoiceBox;
    Label dateLabel;
    DatePicker dateDatePicker;
    Label timeLabel;
    TextField timeTextField;
    Label titleLabel;
    TextField titleTextField;
    Label topicLabel;
    TextField topicTextField;
    Label linkLabel;
    TextField linkTextField;
    Label criteriaLabel;
    TextField criteriaTextField;
    Button addUpdateButton;
    Button clearButton;

    public ScheduleDataTab(CSGManagerApp initapp) {
        app = initapp;

        PropertiesManager props = PropertiesManager.getPropertiesManager();

        String scheduleLabelText = props.getProperty(CSGManagerProp.SCHEDULE_LABEL_TEXT.toString());
        scheduleLabel = new Label(scheduleLabelText);

        //CALENDAR BOUNDARIES PANE
        calendarBoundariesGridPane = new GridPane();

        String calendarBoundariesLabelText = props.getProperty(CSGManagerProp.CALENDAR_BOUNDARIES_LABEL_TEXT.toString());
        calendarBoundariesLabel = new Label(calendarBoundariesLabelText);
        calendarBoundariesGridPane.add(calendarBoundariesLabel, 0, 0);

        String startingMondayLabelText = props.getProperty(CSGManagerProp.STARTING_MONDAY_LABEL_TEXT.toString());
        startingMondayLabel = new Label(startingMondayLabelText);
        calendarBoundariesGridPane.add(startingMondayLabel, 0, 1);

        startingMondayDatePicker = new DatePicker();
        calendarBoundariesGridPane.add(startingMondayDatePicker, 1, 1);

        String endingFridayLabelText = props.getProperty(CSGManagerProp.ENDING_FRIDAY_LABEL_TEXT.toString());
        endingFridayLabel = new Label(endingFridayLabelText);
        calendarBoundariesGridPane.add(endingFridayLabel, 2, 1);

        endingFridayDatePicker = new DatePicker();
        calendarBoundariesGridPane.add(endingFridayDatePicker, 3, 1);

        //SCHEDULE ITEMS PANE
        scheduleItemsLabelButton = new HBox();

        String scheduleItemsLabelText = props.getProperty(CSGManagerProp.SCHEDULE_ITEMS_LABEL_TEXT.toString());
        scheduleItemsLabel = new Label(scheduleItemsLabelText);
        dashButton = new Button("-");
        scheduleItemsLabelButton.getChildren().addAll(scheduleItemsLabel, dashButton);

        // CREATE SCHEDULE TABLE
        scheduleTable = new TableView();
        String typeColumnText = props.getProperty(CSGManagerProp.TYPE_COLUMN_TEXT.toString());
        typeColumn = new TableColumn(typeColumnText);
        String dateColumnText = props.getProperty(CSGManagerProp.DATE_COLUMN_TEXT.toString());
        dateColumn = new TableColumn(dateColumnText);
        String titleColumnText = props.getProperty(CSGManagerProp.TITLE_COLUMN_TEXT.toString());
        titleColumn = new TableColumn(titleColumnText);
        String topicColumnText = props.getProperty(CSGManagerProp.TOPIC_COLUMN_TEXT.toString());
        topicColumn = new TableColumn(topicColumnText);

        scheduleTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        scheduleTable.setEditable(true);
        CSGData data = (CSGData) app.getDataComponent();
        ObservableList<Schedule> tableData = data.getSchedules();
        scheduleTable.setItems(tableData);

        typeColumn.setCellValueFactory(new PropertyValueFactory<Schedule, String>("type"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Schedule, String>("date"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<Schedule, String>("title"));
        topicColumn.setCellValueFactory(new PropertyValueFactory<Schedule, String>("topic"));

        scheduleTable.getColumns().addAll(typeColumn, dateColumn, titleColumn, topicColumn);

        // VBOX TO EDIT A SCHEDULE
        addScheduleVBox = new VBox();

        addScheduleVBox.getChildren().addAll(scheduleItemsLabelButton, scheduleTable);

        //ADD/EDIT SCHEDULE PARAMETERS
        addEditScheduleGridPane = new GridPane();

        String addEditLabelText = props.getProperty(CSGManagerProp.ADD_EDIT_LABEL_TEXT.toString());
        addEditLabel = new Label(addEditLabelText);
        addEditScheduleGridPane.add(addEditLabel, 0, 0);

        String typeLabelText = props.getProperty(CSGManagerProp.TYPE_LABEL_TEXT.toString());
        typeLabel = new Label(typeLabelText);
        addEditScheduleGridPane.add(typeLabel, 0, 1);

        typeChoiceBox = new ChoiceBox();
        typeChoiceBox.getItems().addAll("Holiday");
        addEditScheduleGridPane.add(typeChoiceBox, 1, 1);

        String dateLabelText = props.getProperty(CSGManagerProp.DATE_LABEL_TEXT.toString());
        dateLabel = new Label(dateLabelText);
        addEditScheduleGridPane.add(dateLabel, 0, 2);
        dateDatePicker = new DatePicker();
        addEditScheduleGridPane.add(dateDatePicker, 1, 2);

        String timeLabelText = props.getProperty(CSGManagerProp.TIME_LABEL_TEXT.toString());
        timeLabel = new Label(timeLabelText);
        addEditScheduleGridPane.add(timeLabel, 0, 3);
        timeTextField = new TextField();
        addEditScheduleGridPane.add(timeTextField, 1, 3);

        String titleLabelText = props.getProperty(CSGManagerProp.TIME_LABEL_TEXT.toString());
        titleLabel = new Label(titleLabelText);
        addEditScheduleGridPane.add(titleLabel, 0, 4);
        titleTextField = new TextField();
        addEditScheduleGridPane.add(titleTextField, 1, 4);

        String topicLabelText = props.getProperty(CSGManagerProp.TOPIC_LABEL_TEXT.toString());
        topicLabel = new Label(topicLabelText);
        addEditScheduleGridPane.add(topicLabel, 0, 5);
        topicTextField = new TextField();
        addEditScheduleGridPane.add(topicTextField, 1, 5);

        String linkLabelText = props.getProperty(CSGManagerProp.LINK_LABEL_TEXT.toString());
        linkLabel = new Label(linkLabelText);
        addEditScheduleGridPane.add(linkLabel, 0, 6);
        linkTextField = new TextField();
        addEditScheduleGridPane.add(linkTextField, 1, 6);

        String criteriaLabelText = props.getProperty(CSGManagerProp.CRITERIA_LABEL_TEXT.toString());
        criteriaLabel = new Label(criteriaLabelText);
        addEditScheduleGridPane.add(criteriaLabel, 0, 7);
        criteriaTextField = new TextField();
        addEditScheduleGridPane.add(criteriaTextField, 1, 7);

        String addUpdateButtonText = props.getProperty(CSGManagerProp.ADD_UPDATE_BUTTON_TEXT.toString());
        addUpdateButton = new Button(addUpdateButtonText);
        addEditScheduleGridPane.add(addUpdateButton, 0, 8);

        String clearButtonText = props.getProperty(CSGManagerProp.CLEAR_BUTTON_TEXT.toString());
        clearButton = new Button(clearButtonText);
        addEditScheduleGridPane.add(clearButton, 1, 8);

        addScheduleVBox.getChildren().addAll(addEditScheduleGridPane);

        scheduleDataWorkspace = new VBox(8);

        scheduleLabel.setStyle("-fx-background-color: #F5F5F5;");
        calendarBoundariesGridPane.setStyle("-fx-background-color: #F5F5F5;");
        addScheduleVBox.setStyle("-fx-background-color: #F5F5F5;");

        scheduleDataWorkspace.getChildren().addAll(scheduleLabel, calendarBoundariesGridPane,
                addScheduleVBox);
        scheduleDataWorkspace.setStyle("-fx-background-color: #CCCDFE;");

        scheduleDataWorkspace.setPadding(new Insets(10, 50, 50, 50));

        scheduleTable.prefHeightProperty().bind(scheduleDataWorkspace.heightProperty().multiply(0.4));
        //       scheduleTable.setStyle("-fx-background-color: #010764;");
        //    scheduleTable.prefWidthProperty().bind(scheduleDataWorkspace.widthProperty());

    
        
    controller = new CSGController(app);    
        
    //HANDLE ACTION EVENTS
    addUpdateButton.setOnAction(e -> {
        controller.addScheduleHandler();
    });
    dashButton.setOnAction(e -> {
        controller.deleteScheduleHandler();
    });
    
    
    
    
    
    }
    
    
    
    

    public void reloadScheduleDataTab() {
        CSGData data = (CSGData) app.getDataComponent();

        String startingMondayDate = data.getStartingMonday();
        if (!startingMondayDate.equals("")) {
            int monYear = Integer.parseInt(startingMondayDate.substring(0, 4));
            int monMonth = Integer.parseInt(startingMondayDate.substring(5, 7));
            int monDay = Integer.parseInt(startingMondayDate.substring(8));
            startingMondayDatePicker.setValue(LocalDate.of(monYear, monMonth, monDay));
        }

        String endingFridayDate = data.getEndingFriday();
        if (!endingFridayDate.equals("")) {
            int friYear = Integer.parseInt(endingFridayDate.substring(0, 4));
            int friMonth = Integer.parseInt(endingFridayDate.substring(5, 7));
            int friDay = Integer.parseInt(endingFridayDate.substring(8));
            endingFridayDatePicker.setValue(LocalDate.of(friYear, friMonth, friDay));
        }
    }

    public VBox getScheduleDataWorkspace() {
        return scheduleDataWorkspace;
    }

    public Label getScheduleLabel() {
        return scheduleLabel;
    }

    public GridPane getCalendarBoundariesGridPane() {
        return calendarBoundariesGridPane;
    }

    public Label getCalendarBoundariesLabel() {
        return calendarBoundariesLabel;
    }

    public Label getStartingMondayLabel() {
        return startingMondayLabel;
    }

    public DatePicker getStartingMondayDatePicker() {
        return startingMondayDatePicker;
    }

    public Label getEndingFridayLabel() {
        return endingFridayLabel;
    }

    public DatePicker getEndingFridayDatePicker() {
        return endingFridayDatePicker;
    }

    public HBox getScheduleItemsLabelButton() {
        return scheduleItemsLabelButton;
    }

    public Label getScheduleItemsLabel() {
        return scheduleItemsLabel;
    }

    public Button getDashButton() {
        return dashButton;
    }

    public TableView getScheduleTable() {
        return scheduleTable;
    }

    public TableColumn getTypeColumn() {
        return typeColumn;
    }

    public TableColumn getDateColumn() {
        return dateColumn;
    }

    public TableColumn getTitleColumn() {
        return titleColumn;
    }

    public TableColumn getTopicColumn() {
        return topicColumn;
    }

    public VBox getAddScheduleVBox() {
        return addScheduleVBox;
    }

    public GridPane getAddEditScheduleGridPane() {
        return addEditScheduleGridPane;
    }

    public Label getAddEditLabel() {
        return addEditLabel;
    }

    public Label getTypeLabel() {
        return typeLabel;
    }

    public ChoiceBox getTypeChoiceBox() {
        return typeChoiceBox;
    }

    public Label getDateLabel() {
        return dateLabel;
    }

    public DatePicker getDateDatePicker() {
        return dateDatePicker;
    }

    public Label getTimeLabel() {
        return timeLabel;
    }

    public TextField getTimeTextField() {
        return timeTextField;
    }

    public Label getTitleLabel() {
        return titleLabel;
    }

    public TextField getTitleTextField() {
        return titleTextField;
    }

    public Label getTopicLabel() {
        return topicLabel;
    }

    public TextField getTopicTextField() {
        return topicTextField;
    }

    public Label getLinkLabel() {
        return linkLabel;
    }

    public TextField getLinkTextField() {
        return linkTextField;
    }

    public Label getCriteriaLabel() {
        return criteriaLabel;
    }

    public TextField getCriteriaTextField() {
        return criteriaTextField;
    }

    public Button getAddUpdateButton() {
        return addUpdateButton;
    }

    public Button getClearButton() {
        return clearButton;
    }

}
