/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspaces;

import csg.CSGManagerApp;
import csg.CSGManagerProp;
import csg.data.CSGData;
import csg.data.Student;
import csg.data.Team;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
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
public class ProjectDataTab {

    CSGManagerApp app;

    VBox projectDataWorkspace;
    
    Label projectsLabel;

    //TEAM PANE
    HBox teamLabelButtonBox;
    Label teamsHeaderLabel;
    Button dashButton;
    TableView teamTable;
    TableColumn nameColumn;
    TableColumn colorColumn;
    TableColumn textColorColumn;
    TableColumn linkColumn;

    GridPane addEditTeamGridPane;
    Label addEditLabel;
    Label nameLabel;
    TextField nameTextField;
    Label colorLabel;
    ColorPicker colorColorPicker;
    Label textColorLabel;
    ColorPicker textColorColorPicker;
    Label linkLabel;
    TextField linkTextField;
    Button addUpdateButton1;
    Button clearButton1;
    
    VBox teamsVBox;

    //STUDENT PANE
    HBox studentButtonBox;
    Label studentLabel;
    Button dashButton2;
    TableView studentTable;
    TableColumn firstNameColumn;
    TableColumn lastNameColumn;
    TableColumn teamColumn;
    TableColumn roleColumn;

    GridPane addEditStudentGridPane;
    Label addEditLabel2;
    Label firstNameLabel;
    TextField firstNameTextField;
    Label lastNameLabel;
    TextField lastNameTextField;
    Label teamLabel;
    ComboBox teamComboBox;
    Label roleLabel;
    TextField roleTextField;
    Button addUpdateButton2;
    Button clearButton2;
    
    VBox studentsVBox;

    public ProjectDataTab(CSGManagerApp initApp) {
        app = initApp;

        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        String projectLabelText = props.getProperty(CSGManagerProp.PROJECTS_LABEL_TEXT.toString());
        projectsLabel = new Label(projectLabelText);

        //TEAM PANE
        teamLabelButtonBox = new HBox();
        
        String teamsHeaderLabelText = props.getProperty(CSGManagerProp.TEAMS_HEADER_LABEL_TEXT.toString());
        teamsHeaderLabel = new Label(teamsHeaderLabelText);
        dashButton = new Button("-");
        
        teamLabelButtonBox.getChildren().addAll(teamsHeaderLabel, dashButton);
        
        teamTable = new TableView();
        String nameColumnText = props.getProperty(CSGManagerProp.NAME_COLUMN_TEXT.toString());
        nameColumn = new TableColumn(nameColumnText);
        String colorColumnText = props.getProperty(CSGManagerProp.COLOR_COLUMN_TEXT.toString());
        colorColumn = new TableColumn(colorColumnText);
        String textColorColumnText = props.getProperty(CSGManagerProp.TEXT_COLOR_COLUMN_TEXT.toString());
        textColorColumn = new TableColumn(textColorColumnText);
        String linkColumnText = props.getProperty(CSGManagerProp.LINK_COLUMN_TEXT.toString());
        linkColumn = new TableColumn(linkColumnText);
        
        teamTable.getColumns().addAll(nameColumn, colorColumn, textColorColumn, linkColumn);
        teamTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        teamTable.setEditable(true);
        CSGData data = (CSGData) app.getDataComponent();
        ObservableList<Team> tableData = data.getTeams();
        teamTable.setItems(tableData);

        addEditTeamGridPane = new GridPane();
        
        String addEditLabelText = props.getProperty(CSGManagerProp.ADD_EDIT_LABEL_TEXT.toString());
        addEditLabel = new Label(addEditLabelText);
        addEditTeamGridPane.add(addEditLabel, 0, 0);
        
        String nameLabelText = props.getProperty(CSGManagerProp.NAME_LABEL_TEXT.toString());
        nameLabel = new Label(nameLabelText);
        addEditTeamGridPane.add(nameLabel, 0, 1);
        nameTextField = new TextField();
        addEditTeamGridPane.add(nameTextField, 1,1);
        
        String colorLabelText = props.getProperty(CSGManagerProp.COLOR_LABEL_TEXT.toString());
        colorLabel = new Label(colorLabelText);
        addEditTeamGridPane.add(colorLabel, 0, 2);
        colorColorPicker = new ColorPicker();
        addEditTeamGridPane.add(colorColorPicker, 1,2);
        
        String textColorLabelText = props.getProperty(CSGManagerProp.TEXT_COLOR_LABEL_TEXT.toString());
        textColorLabel = new Label(textColorLabelText);
        addEditTeamGridPane.add(textColorLabel, 2, 2);
        textColorColorPicker = new ColorPicker();
        addEditTeamGridPane.add(textColorColorPicker, 3, 2);
        
        String linkLabelText = props.getProperty(CSGManagerProp.LINK_LABEL_TEXT.toString());
        linkLabel = new Label(linkLabelText);
        addEditTeamGridPane.add(linkLabel, 0, 3);
        linkTextField = new TextField();
        addEditTeamGridPane.add(linkTextField, 1,3);
        
        String addUpdateButtonText = props.getProperty(CSGManagerProp.ADD_UPDATE_BUTTON_TEXT.toString());
        addUpdateButton1 = new Button(addUpdateButtonText);
        addEditTeamGridPane.add(addUpdateButton1, 0, 4);
        String clearButtonText = props.getProperty(CSGManagerProp.CLEAR_BUTTON_TEXT.toString());
        clearButton1 = new Button(clearButtonText);
        addEditTeamGridPane.add(clearButton1, 1,4);
        
        
        teamsVBox = new VBox(8);
        teamsVBox.getChildren().addAll(teamLabelButtonBox, teamTable, addEditTeamGridPane);

        //STUDENT PANE
        studentButtonBox = new HBox();
        String studentLabelText = props.getProperty(CSGManagerProp.STUDENT_LABEL_TEXT.toString());
        studentLabel = new Label(studentLabelText);
        dashButton2 = new Button("-");
        studentButtonBox.getChildren().addAll(studentLabel, dashButton2);
        
        studentTable = new TableView();
        String firstNameColumnText = props.getProperty(CSGManagerProp.FIRST_NAME_COLUMN_TEXT.toString());
        firstNameColumn = new TableColumn(firstNameColumnText);
        String lastNameColumnText = props.getProperty(CSGManagerProp.LAST_NAME_COLUMN_TEXT.toString());
        lastNameColumn = new TableColumn(lastNameColumnText);
        String teamColumnText = props.getProperty(CSGManagerProp.TEAM_COLUMN_TEXT.toString());
        teamColumn = new TableColumn(teamColumnText);
        String roleColumnText = props.getProperty(CSGManagerProp.ROLE_COLUMN_TEXT.toString());
        roleColumn = new TableColumn(roleColumnText);
        
        studentTable.getColumns().addAll(firstNameColumn, lastNameColumn, teamColumn, roleColumn);
        studentTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        studentTable.setEditable(true);
        ObservableList<Student> studentTableData = data.getStudents();
        studentTable.setItems(studentTableData);

        addEditStudentGridPane = new GridPane();
        String addEditLabel2Text = props.getProperty(CSGManagerProp.ADD_EDIT_LABEL2_TEXT.toString());
        addEditLabel2 = new Label(addEditLabel2Text);
        addEditStudentGridPane.add(addEditLabel2, 0 ,0);
                
        String firstNameLabelText = props.getProperty(CSGManagerProp.FIRST_NAME_LABEL_TEXT.toString());
        firstNameLabel = new Label(firstNameLabelText);
        addEditStudentGridPane.add(firstNameLabel, 0, 1);
        firstNameTextField = new TextField();
        addEditStudentGridPane.add(firstNameTextField, 1,1);
        
        String lastNameLabelText = props.getProperty(CSGManagerProp.LAST_NAME_LABEL_TEXT.toString());
        lastNameLabel = new Label(lastNameLabelText);
        addEditStudentGridPane.add(lastNameLabel, 0,2);
        lastNameTextField = new TextField();
        addEditStudentGridPane.add(lastNameTextField, 1,2);
        
        String teamLabelText = props.getProperty(CSGManagerProp.TEAM_LABEL_TEXT.toString());
        teamLabel = new Label(teamLabelText);
        addEditStudentGridPane.add(teamLabel, 0,3);
        teamComboBox = new ComboBox();
        addEditStudentGridPane.add(teamComboBox, 1, 3);
        
        String roleLabelText = props.getProperty(CSGManagerProp.ROLE_LABEL_TEXT.toString());
        roleLabel = new Label(roleLabelText);
        addEditStudentGridPane.add(roleLabel, 0,4);
        roleTextField = new TextField();
        addEditStudentGridPane.add(roleTextField, 1,4);
        
        addUpdateButton2 = new Button(addUpdateButtonText);
        addEditStudentGridPane.add(addUpdateButton2, 0,5);
        clearButton2 = new Button(clearButtonText);
        addEditStudentGridPane.add(clearButton2, 1,5);
        
        studentsVBox = new VBox();
        studentsVBox.getChildren().addAll(studentButtonBox, studentTable, addEditStudentGridPane);
        
        projectDataWorkspace = new VBox(8);
        
        projectsLabel.setStyle("-fx-background-color: #F5F5F5;");
        teamsVBox.setStyle("-fx-background-color: #F5F5F5;");
        studentsVBox.setStyle("-fx-background-color: #F5F5F5;");
        
        projectDataWorkspace.getChildren().addAll(projectsLabel, teamsVBox, studentsVBox);
        projectDataWorkspace.setStyle("-fx-background-color: #CCCDFE;");
        
        projectDataWorkspace.setPadding(new Insets(10, 50, 50, 50));
        
        teamTable.prefHeightProperty().bind(projectDataWorkspace.heightProperty().multiply(0.2));
//        teamTable.setStyle("-fx-background-color: #010764;");
        studentTable.prefHeightProperty().bind(projectDataWorkspace.heightProperty().multiply(0.2));
//        studentTable.setStyle("-fx-background-color: #010764;");
    }

    public VBox getProjectDataWorkspace() {
        return projectDataWorkspace;
    }

    public Label getProjectsLabel() {
        return projectsLabel;
    }

    public HBox getTeamLabelButtonBox() {
        return teamLabelButtonBox;
    }

    public Label getTeamsHeaderLabel() {
        return teamsHeaderLabel;
    }

    public Button getDashButton() {
        return dashButton;
    }

    public TableView getTeamTable() {
        return teamTable;
    }

    public TableColumn getNameColumn() {
        return nameColumn;
    }

    public TableColumn getColorColumn() {
        return colorColumn;
    }

    public TableColumn getTextColorColumn() {
        return textColorColumn;
    }

    public TableColumn getLinkColumn() {
        return linkColumn;
    }

    public GridPane getAddEditTeamGridPane() {
        return addEditTeamGridPane;
    }

    public Label getAddEditLabel() {
        return addEditLabel;
    }

    public Label getNameLabel() {
        return nameLabel;
    }

    public TextField getNameTextField() {
        return nameTextField;
    }

    public Label getColorLabel() {
        return colorLabel;
    }

    public ColorPicker getColorColorPicker() {
        return colorColorPicker;
    }

    public Label getTextColorLabel() {
        return textColorLabel;
    }

    public ColorPicker getTextColorColorPicker() {
        return textColorColorPicker;
    }

    public Label getLinkLabel() {
        return linkLabel;
    }

    public TextField getLinkTextField() {
        return linkTextField;
    }

    public Button getAddUpdateButton1() {
        return addUpdateButton1;
    }

    public Button getClearButton1() {
        return clearButton1;
    }

    public VBox getTeamsVBox() {
        return teamsVBox;
    }

    public HBox getStudentButtonBox() {
        return studentButtonBox;
    }

    public Label getStudentLabel() {
        return studentLabel;
    }

    public Button getDashButton2() {
        return dashButton2;
    }

    public TableView getStudentTable() {
        return studentTable;
    }

    public TableColumn getFirstNameColumn() {
        return firstNameColumn;
    }

    public TableColumn getLastNameColumn() {
        return lastNameColumn;
    }

    public TableColumn getTeamColumn() {
        return teamColumn;
    }

    public TableColumn getRoleColumn() {
        return roleColumn;
    }

    public GridPane getAddEditStudentGridPane() {
        return addEditStudentGridPane;
    }

    public Label getAddEditLabel2() {
        return addEditLabel2;
    }

    public Label getFirstNameLabel() {
        return firstNameLabel;
    }

    public TextField getFirstNameTextField() {
        return firstNameTextField;
    }

    public Label getLastNameLabel() {
        return lastNameLabel;
    }

    public TextField getLastNameTextField() {
        return lastNameTextField;
    }

    public Label getTeamLabel() {
        return teamLabel;
    }

    public ComboBox getTeamComboBox() {
        return teamComboBox;
    }

    public Label getRoleLabel() {
        return roleLabel;
    }

    public TextField getRoleTextField() {
        return roleTextField;
    }

    public Button getAddUpdateButton2() {
        return addUpdateButton2;
    }

    public Button getClearButton2() {
        return clearButton2;
    }

    public VBox getStudentsVBox() {
        return studentsVBox;
    }
    
    
}
