/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspaces;

import csg.CSGManagerApp;
import csg.CSGManagerProp;
import csg.data.CSGData;
import csg.data.Template;
import java.io.File;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;

/**
 *
 * @author tonyohalleran
 */
public class CourseDetailsTab {

    CSGManagerApp app;
    CSGController controller;

    //TOP PANE NODES
    Label courseInfoLabel;
    Label subjectLabel;
    ComboBox subjectComboBox;
    Label numberLabel;
    ComboBox numberComboBox;
    Label semesterLabel;
    ComboBox semesterComboBox;
    Label yearLabel;
    ComboBox yearComboBox;
    Label titleLabel;
    TextField titleTextField;
    Label instructorNameLabel;
    TextField instructorNameTextField;
    Label instructorHomeLabel;
    TextField instructorHomeTextField;
    Label exportDirLabel;
    Label exportDirPathLabel;
    Button changeDirButton;

    GridPane courseInfoGridPane;

    //MIDDLE PANE NODES
    Label siteTemplateLabel;
    Label siteTemplateInfoLabel;
    Label templateDirLabel;
    Button selectTemplateDirLabel;
    Label sitePagesLabel;
    TableView<Template> siteTable;
    TableColumn<Template, Boolean> useColumn;
    TableColumn<Template, String> navbarTitleColumn;
    TableColumn<Template, String> fileNameColumn;
    TableColumn<Template, String> scriptColumn;

    VBox siteTemplateBox;

    //BOTTON PANE NODES - PAGE STYLE
    GridPane pageStyleGridPane;

    Label pageStyleLabel;
    Label bannerSchoolImgLabel;
    ImageView bannerSchoolImg;
    Button bannerSchoolImgChangeButton;
    Label leftFooterImgLabel;
    ImageView leftFooterImg;
    Button leftFooterImgChangeButton;
    Label rightFooterImgLabel;
    ImageView rightFooterImg;
    Button rightFooterImgChangeButton;
    Label stylesheetLabel;
    ComboBox stylesheetComboBox;
    Label noteLabel;
    
    
    // COURSE DETAILS TAB VBOX PANE
    VBox courseDetailsWorkspace;

    

    public VBox getCourseDetailsWorkspace() {
        return courseDetailsWorkspace;
    }

    public CourseDetailsTab(CSGManagerApp initApp, CSGController initController) {
        app = initApp;
        controller = initController;

        // WE'LL NEED THIS TO GET LANGUAGE PROPERTIES FOR OUR UI
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        // TOP PANE IS COURSE DETAILS TAB
        courseInfoGridPane = new GridPane();

        String courserInfoLabelText = props.getProperty(CSGManagerProp.COURSE_INFO_LABEL_TEXT.toString());
        courseInfoLabel = new Label(courserInfoLabelText);
        courseInfoGridPane.add(courseInfoLabel, 0, 0);

        String subjectLabelText = props.getProperty(CSGManagerProp.SUBJECT_LABEL_TEXT.toString());
        subjectLabel = new Label(subjectLabelText);
        courseInfoGridPane.add(subjectLabel, 0, 1);
        subjectComboBox = new ComboBox();
        subjectComboBox.getItems().addAll("CSE");
        subjectComboBox.setEditable(true);
        courseInfoGridPane.add(subjectComboBox, 1, 1);

        String numberLabelText = props.getProperty(CSGManagerProp.NUMBER_LABEL_TEXT.toString());
        numberLabel = new Label(numberLabelText);
        courseInfoGridPane.add(numberLabel, 2, 1);
        numberComboBox = new ComboBox();
        numberComboBox.getItems().addAll("219");
        numberComboBox.setEditable(true);
        courseInfoGridPane.add(numberComboBox, 3, 1);

        String semesterLabelText = props.getProperty(CSGManagerProp.SEMESTER_LABEL_TEXT.toString());
        semesterLabel = new Label(semesterLabelText);
        courseInfoGridPane.add(semesterLabel, 0, 2);
        semesterComboBox = new ComboBox();
        semesterComboBox.getItems().addAll("Fall", "Spring");
        semesterComboBox.setEditable(true);
        courseInfoGridPane.add(semesterComboBox, 1, 2);

        String yearLabelText = props.getProperty(CSGManagerProp.YEAR_LABEL_TEXT.toString());
        yearLabel = new Label(yearLabelText);
        courseInfoGridPane.add(yearLabel, 2, 2);
        yearComboBox = new ComboBox();
        yearComboBox.getItems().addAll("2017");
        yearComboBox.setEditable(true);
        courseInfoGridPane.add(yearComboBox, 3, 2);

        String titleLabelText = props.getProperty(CSGManagerProp.TITLE_LABEL_TEXT.toString());
        titleLabel = new Label(titleLabelText);
        courseInfoGridPane.add(titleLabel, 0, 3);
        titleTextField = new TextField();
        courseInfoGridPane.add(titleTextField, 1, 3);

        String instructorNameLabelText = props.getProperty(CSGManagerProp.INSTRUCTOR_NAME_LABEL_TEXT.toString());
        instructorNameLabel = new Label(instructorNameLabelText);
        courseInfoGridPane.add(instructorNameLabel, 0, 4);
        instructorNameTextField = new TextField();
        courseInfoGridPane.add(instructorNameTextField, 1, 4);

        String instructorHomeLabelText = props.getProperty(CSGManagerProp.INSTRUCTOR_HOME_LABEL_TEXT.toString());
        instructorHomeLabel = new Label(instructorHomeLabelText);
        courseInfoGridPane.add(instructorHomeLabel, 0, 5);
        instructorHomeTextField = new TextField();
        courseInfoGridPane.add(instructorHomeTextField, 1, 5);

        String exportDirLabelText = props.getProperty(CSGManagerProp.EXPORT_DIR_LABEL_TEXT.toString());
        exportDirLabel = new Label(exportDirLabelText);
        courseInfoGridPane.add(exportDirLabel, 0, 6);
        String exportDirPathLabelText = props.getProperty(CSGManagerProp.EXPORT_DIR_PATH_LABEL_TEXT.toString());
        exportDirPathLabel = new Label(exportDirPathLabelText);
        courseInfoGridPane.add(exportDirPathLabel, 1, 6);
        String changeDirButtonText = props.getProperty(CSGManagerProp.CHANGE_DIR_BUTTON_TEXT.toString());
        changeDirButton = new Button(changeDirButtonText);
        courseInfoGridPane.add(changeDirButton, 2, 6);

        
        
        



        //MIDDLE PANE IS SITE TEMPLATE
        siteTemplateBox = new VBox();

        String siteTemplateLabelText = props.getProperty(CSGManagerProp.SITE_TEMPLATE_LABEL_TEXT.toString());
        siteTemplateLabel = new Label(siteTemplateLabelText);
        siteTemplateBox.getChildren().add(siteTemplateLabel);

        String siteTemplateInfoLabelText = props.getProperty(CSGManagerProp.SITE_TEMPLATE_INFO_LABEL_TEXT.toString());
        siteTemplateInfoLabel = new Label(siteTemplateInfoLabelText);
        siteTemplateBox.getChildren().add(siteTemplateInfoLabel);

        String templateDirLabelText = props.getProperty(CSGManagerProp.TEMPLATE_DIR_LABEL_TEXT.toString());
        templateDirLabel = new Label(templateDirLabelText);
        siteTemplateBox.getChildren().add(templateDirLabel);

        String selectTemplateDirButtonText = props.getProperty(CSGManagerProp.SELECT_TEMPLATE_DIR_BUTTON_TEXT.toString());
        Button selectTemplateDirButton = new Button(selectTemplateDirButtonText);
        siteTemplateBox.getChildren().add(selectTemplateDirButton);

        String sitePagesLabelText = props.getProperty(CSGManagerProp.SITE_PAGES_LABEL_TEXT.toString());
        sitePagesLabel = new Label(sitePagesLabelText);
        siteTemplateBox.getChildren().add(sitePagesLabel);

        // SITE TABLE
        siteTable = new TableView();
        siteTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        siteTable.setEditable(true);
        CSGData data = (CSGData) app.getDataComponent();
        ObservableList<Template> tableTemplateData = data.getTemplates();
        siteTable.setItems(tableTemplateData);
        String useColumnText = props.getProperty(CSGManagerProp.USE_COLUMN_TEXT.toString());
        useColumn = new TableColumn(useColumnText);
//        useColumn.setEditable(true);
        String navbarTitleColumnText = props.getProperty(CSGManagerProp.NAVBAR_TITLE_COLUMN_TEXT.toString());
        navbarTitleColumn = new TableColumn(navbarTitleColumnText);
        String fileNameColumnText = props.getProperty(CSGManagerProp.FILE_NAME_COLUMN_TEXT.toString());
        fileNameColumn = new TableColumn(fileNameColumnText);
        String scriptColumnText = props.getProperty(CSGManagerProp.SCRIPT_COLUMN_TEXT.toString());
        scriptColumn = new TableColumn(scriptColumnText);

        // ADD CELL VALUE FACTORY FOR COLUMNS
        // ADD COLUMNS
        useColumn.setCellValueFactory(
                new PropertyValueFactory<Template,Boolean>("use")
        );
        navbarTitleColumn.setCellValueFactory(
                new PropertyValueFactory<Template, String>("navbarTitle")
        );
        fileNameColumn.setCellValueFactory(
                new PropertyValueFactory<Template, String>("fileName")
        );
        scriptColumn.setCellValueFactory(
                new PropertyValueFactory<Template, String>("script")
        );
        useColumn.setCellFactory(
                CheckBoxTableCell.forTableColumn(useColumn)
        ); 
        
        
        siteTable.getColumns().add(useColumn);
        siteTable.getColumns().add(navbarTitleColumn);
        siteTable.getColumns().add(fileNameColumn);
        siteTable.getColumns().add(scriptColumn);

        // ADD TABLE TO PANE
        siteTemplateBox.getChildren().add(siteTable);
//        siteTable.prefHeightProperty().bind(siteTemplateBox.heightProperty().multiply(0.6));
//        siteTable.prefWidthProperty().bind(siteTemplateBox.widthProperty().multiply(0.2));
//        siteTable.setMinHeight(100);
        siteTable.setMaxHeight(500);
//        siteTable.setMinWidth(100);
        siteTable.setMaxWidth(1200);

//        siteTable.setStyle("-fx-background-color: #010764;");

        //BOTTON PANE - PAGE STYLE
        pageStyleGridPane = new GridPane();
        

        String pageStyleLabelText = props.getProperty(CSGManagerProp.PAGE_STYLE_LABEL_TEXT.toString());
        pageStyleLabel = new Label(pageStyleLabelText);
        pageStyleGridPane.add(pageStyleLabel, 0, 0);
        
        String bannerSchoolImgLabelText = props.getProperty(CSGManagerProp.BANNER_SCHOOL_IMG_LABEL_TEXT.toString());
        bannerSchoolImgLabel = new Label(bannerSchoolImgLabelText);
        pageStyleGridPane.add(bannerSchoolImgLabel, 0, 1);
        
        bannerSchoolImg = new ImageView();
        pageStyleGridPane.add(bannerSchoolImg, 1, 1);
        
        String imgChangeButtonText = props.getProperty(CSGManagerProp.IMG_CHANGE_BUTTON_TEXT.toString());
        bannerSchoolImgChangeButton = new Button(imgChangeButtonText);
        pageStyleGridPane.add(bannerSchoolImgChangeButton, 2, 1);
        
        String leftFooterImgLabelText = props.getProperty(CSGManagerProp.LEFT_FOOTER_IMG_LABEL_TEXT.toString());
        leftFooterImgLabel = new Label(leftFooterImgLabelText);
        pageStyleGridPane.add(leftFooterImgLabel, 0, 2);
        
        leftFooterImg = new ImageView();
        pageStyleGridPane.add(leftFooterImg, 1, 2);
        
        leftFooterImgChangeButton = new Button(imgChangeButtonText);
        pageStyleGridPane.add(leftFooterImgChangeButton, 2, 2);
        
        String rightFooterImgLabelText = props.getProperty(CSGManagerProp.RIGHT_FOOTER_IMG_LABEL_TEXT.toString());
        rightFooterImgLabel = new Label(rightFooterImgLabelText);
        pageStyleGridPane.add(rightFooterImgLabel, 0, 3);
        
        rightFooterImg = new ImageView();
        pageStyleGridPane.add(rightFooterImg, 1, 3);
        
        rightFooterImgChangeButton = new Button(imgChangeButtonText);
        pageStyleGridPane.add(rightFooterImgChangeButton, 2, 3);
        
        String stylesheetLabelText = props.getProperty(CSGManagerProp.STYLESHEET_LABEL_TEXT.toString());
        stylesheetLabel = new Label(stylesheetLabelText);
        pageStyleGridPane.add(stylesheetLabel, 0, 4);
        
        stylesheetComboBox = new ComboBox();
//        stylesheetComboBox.getItems().addAll("sea_wolf.css");
        pageStyleGridPane.add(stylesheetComboBox, 1, 4);
        
        String noteLabelText = props.getProperty(CSGManagerProp.NOTE_LABEL_TEXT.toString());
        noteLabel = new Label(noteLabelText);
        pageStyleGridPane.add(noteLabel, 0, 5);
        
        // ADD ALL INDIVIDUAL PANES INTO COURSE DETAILS WORKSPACE
        // WITH 8 PX SPACING
        courseDetailsWorkspace = new VBox(8);
        
        courseInfoGridPane.setStyle("-fx-background-color: #F5F5F5;");
        siteTemplateBox.setStyle("-fx-background-color: #F5F5F5;");
        pageStyleGridPane.setStyle("-fx-background-color: #F5F5F5;");
        
        courseDetailsWorkspace.getChildren().addAll(courseInfoGridPane, siteTemplateBox, pageStyleGridPane);
    //    courseDetailsWorkspace.prefHeightProperty().bind(observable);
        courseDetailsWorkspace.setStyle("-fx-background-color: #CCCDFE;");
        courseDetailsWorkspace.setPadding(new Insets(10, 50, 50, 50));
        courseDetailsWorkspace.prefHeightProperty().bind(app.getGUI().getWindow().heightProperty().multiply(0.80));
    
        
        
        
        // ACTION HANDLERS
        changeDirButton.setOnAction(e -> {
           controller.exportDirectoryHandler(exportDirPathLabel);
        });
        selectTemplateDirButton.setOnAction(e -> {
            controller.templateDirectoryHandler(templateDirLabel);
        });
        bannerSchoolImgChangeButton.setOnAction(e -> {
            controller.bannerImageHandler(bannerSchoolImg);
        });
        leftFooterImgChangeButton.setOnAction(e -> {
            controller.leftFooterHandler(leftFooterImg);
        });
        rightFooterImgChangeButton.setOnAction(e -> {
            controller.rightFooterHandler(rightFooterImg);
            
        });
        stylesheetComboBox.setOnAction(e -> {
            String cssFile = stylesheetComboBox.getValue().toString();
            controller.setStylesheetHandler(cssFile);
        });
        
    
    }
    
    
    public void resetCourseDetailsWorkspace(){
        bannerSchoolImg.setImage(null);
        leftFooterImg.setImage(null);
        rightFooterImg.setImage(null);
    }
    
    
    public void reloadCourseDetailsTab(CSGData dataComponent){
        
        if(!subjectComboBox.getItems().contains(dataComponent.getCourseSubject())) 
            subjectComboBox.getItems().add(dataComponent.getCourseSubject());
        subjectComboBox.getSelectionModel().select(dataComponent.getCourseSubject());
    
        if(!numberComboBox.getItems().contains(dataComponent.getCourseNumber()))
            numberComboBox.getItems().add(dataComponent.getCourseNumber());
        numberComboBox.getSelectionModel().select(dataComponent.getCourseNumber());
        
        if(!semesterComboBox.getItems().contains(dataComponent.getCourseSemester()))
            semesterComboBox.getItems().add(dataComponent.getCourseSemester());
        semesterComboBox.getSelectionModel().select(dataComponent.getCourseSemester());
        
        if(!yearComboBox.getItems().contains(dataComponent.getCourseYear()))
            yearComboBox.getItems().add(dataComponent.getCourseYear());
        yearComboBox.getSelectionModel().select(dataComponent.getCourseYear());
        
        
        titleTextField.setText(dataComponent.getCourseTitle());
        instructorNameTextField.setText(dataComponent.getInstructorName());
        instructorHomeTextField.setText(dataComponent.getInstructorHome());
        
        exportDirPathLabel.setText(dataComponent.getExportDir().toString());
        templateDirLabel.setText(dataComponent.getTemplateDir().toString());
        
//        if(!dataComponent.getBannerSchoolImage().equals(""))
//            bannerSchoolImg.setImage(new Image(dataComponent.getBannerSchoolImage()));
//        if(!dataComponent.getLeftFooterImage().equals(""))
//            leftFooterImg.setImage(new Image(dataComponent.getLeftFooterImage()));
//        if(!dataComponent.getRightFooterImage().equals(""))
//            rightFooterImg.setImage(new Image(dataComponent.getRightFooterImage()));
        
        if(!stylesheetComboBox.getItems().contains(dataComponent.getStylesheet()))
            stylesheetComboBox.getItems().add(dataComponent.getStylesheet());
        stylesheetComboBox.getSelectionModel().select(dataComponent.getStylesheet());
        
    }   
        
        
    
    public Label getCourseInfoLabel() {
        return courseInfoLabel;
    }

    public Label getSubjectLabel() {
        return subjectLabel;
    }

    public ComboBox getSubjectComboBox() {
        return subjectComboBox;
    }

    public Label getNumberLabel() {
        return numberLabel;
    }

    public ComboBox getNumberComboBox() {
        return numberComboBox;
    }

    public Label getSemesterLabel() {
        return semesterLabel;
    }

    public ComboBox getSemesterComboBox() {
        return semesterComboBox;
    }

    public Label getYearLabel() {
        return yearLabel;
    }

    public ComboBox getYearComboBox() {
        return yearComboBox;
    }

    public Label getTitleLabel() {
        return titleLabel;
    }

    public TextField getTitleTextField() {
        return titleTextField;
    }

    public Label getInstructorNameLabel() {
        return instructorNameLabel;
    }

    public TextField getInstructorNameTextField() {
        return instructorNameTextField;
    }

    public Label getInstructorHomeLabel() {
        return instructorHomeLabel;
    }

    public TextField getInstructorHomeTextField() {
        return instructorHomeTextField;
    }

    public Label getExportDirLabel() {
        return exportDirLabel;
    }

    public Label getExportDirPath() {
        return exportDirPathLabel;
    }

    public Button getChangeDirButton() {
        return changeDirButton;
    }

    public GridPane getCourseInfoGridPane() {
        return courseInfoGridPane;
    }

    public Label getSiteTemplateLabel() {
        return siteTemplateLabel;
    }

    public Label getSiteTemplateInfoLabel() {
        return siteTemplateInfoLabel;
    }

    public Label getTemplateDirLabel() {
        return templateDirLabel;
    }

    public Button getSelectTemplateDirLabel() {
        return selectTemplateDirLabel;
    }

    public Label getSitePagesLabel() {
        return sitePagesLabel;
    }

    public TableView getSiteTable() {
        return siteTable;
    }

    public TableColumn getUseColumn() {
        return useColumn;
    }

    public TableColumn getNavbarTitleColumn() {
        return navbarTitleColumn;
    }

    public TableColumn getFileNameColumn() {
        return fileNameColumn;
    }

    public TableColumn getScriptColumn() {
        return scriptColumn;
    }

    public VBox getSiteTemplateBox() {
        return siteTemplateBox;
    }

    public GridPane getPageStyleGridPane() {
        return pageStyleGridPane;
    }

    public Label getPageStyleLabel() {
        return pageStyleLabel;
    }

    public Label getBannerSchoolImgLabel() {
        return bannerSchoolImgLabel;
    }

    public ImageView getBannerSchoolImg() {
        return bannerSchoolImg;
    }

    public Button getBannerSchoolImgChangeButton() {
        return bannerSchoolImgChangeButton;
    }

    public Label getLeftFooterImgLabel() {
        return leftFooterImgLabel;
    }

    public ImageView getLeftFooterImg() {
        return leftFooterImg;
    }

    public Button getLeftFooterImgChangeButton() {
        return leftFooterImgChangeButton;
    }

    public Label getRightFooterImgLabel() {
        return rightFooterImgLabel;
    }

    public ImageView getRightFooterImg() {
        return rightFooterImg;
    }

    public Button getRightFooterImgChangeButton() {
        return rightFooterImgChangeButton;
    }

    public Label getStylesheetLabel() {
        return stylesheetLabel;
    }

    public ComboBox getStylesheetComboBox() {
        return stylesheetComboBox;
    }

    public Label getNoteLabel() {
        return noteLabel;
    }
}
