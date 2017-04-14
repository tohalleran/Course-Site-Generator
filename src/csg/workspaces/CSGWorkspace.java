package csg.workspaces;


import csg.CSGManagerApp;
import csg.data.CSGData;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import properties_manager.PropertiesManager;
import static djf.settings.AppPropertyType.*;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

/**
 *
 * @author tonyohalleran
 */
public class CSGWorkspace extends AppWorkspaceComponent {
    
    // THIS PROVIDES US WITH ACCESS TO THE APP COMPONENTS
    CSGManagerApp app;
    
    //PANE TO HOLD ALL COURSE SITE GENERATOR TABS
    TabPane tabPane;
    
    //COURSE SITE GENERATOR TABS
    Tab courseDetails;
    Tab taData;
    Tab recitationData;
    Tab scheduleData;
    Tab projectData;
    
    //TAB WORKSPACES
    private TADataTab taDataTab;
    private CourseDetailsTab courseDetailsTab;
    private RecitationDataTab recitationDataTab;
    private ScheduleDataTab scheduleDataTab;
    private ProjectDataTab projectDataTab;
    
    public CSGWorkspace(CSGManagerApp initApp){
        // KEEP THIS FOR LATER
        app = initApp;
        
        // WE'LL NEED THIS TO GET LANGUAGE PROPERTIES FOR OUR UI
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        //INIT TAB PANE
        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        tabPane.prefWidthProperty().bind(app.getGUI().getPrimaryScene().widthProperty());
        tabPane.prefHeightProperty().bind(app.getGUI().getPrimaryScene().heightProperty());

        
        //INIT COURSE DETAILS TAB
        courseDetails = new Tab();
        String courseDetailsHeaderText = props.getProperty(COURSE_DETAILS_TAB_TEXT.toString());
        courseDetails.setText(courseDetailsHeaderText);
        courseDetailsTab = new CourseDetailsTab(app);
        courseDetails.setContent(courseDetailsTab.courseDetailsWorkspace);
        tabPane.getTabs().add(courseDetails);
        
        //INIT THE TA DATA TAB
        taData = new Tab();
        String taDataHeaderText = props.getProperty(TA_DATA_TAB_TEXT.toString());
        taData.setText(taDataHeaderText);
        taDataTab = new TADataTab(app);
        taData.setContent(taDataTab.taDataWorkspace);
        tabPane.getTabs().add(taData);
        
        //INIT RECITATION DATA TAB
        recitationData = new Tab();
        String recitationDataHeaderText = props.getProperty(RECITATION_DATA_TAB_TEXT.toString());
        recitationData.setText(recitationDataHeaderText);
        recitationDataTab = new RecitationDataTab(app);
        recitationData.setContent(recitationDataTab.recitationDataWorkspace);
        tabPane.getTabs().add(recitationData);
        
        //INIT SHEDULE DATA TAB
        scheduleData = new Tab();
        String scheduleDataHeaderText = props.getProperty(SCHEDULE_DATA_TAB_TEXT.toString());
        scheduleData.setText(scheduleDataHeaderText);
        scheduleDataTab = new ScheduleDataTab(app);
        scheduleData.setContent(scheduleDataTab.getScheduleDataWorkspace());
        tabPane.getTabs().add(scheduleData);
        
        //INIT PROJECT DATA TAB
        projectData = new Tab();
        String projectDataHeaderText = props.getProperty(PROJECT_DATA_TAB_TEXT.toString());
        projectData.setText(projectDataHeaderText);
        projectDataTab = new ProjectDataTab(app);
        projectData.setContent(projectDataTab.projectDataWorkspace);
        tabPane.getTabs().add(projectData);
        
        workspace = new Pane(tabPane);
        
            
    }
    
    

    @Override
    public void resetWorkspace() {
        //CLEAR OUT ALL OF THE TABS
//        tabPane.getTabs().clear();
        taDataTab.resetTADataWorkspace();
        
    }

    @Override
    public void reloadWorkspace(AppDataComponent dataComponent) {
        CSGData data =(CSGData) dataComponent;
        taDataTab.reloadOfficeHoursGrid(data);
    }

    public TADataTab getTaDataTab() {
        return taDataTab;
    }

    public RecitationDataTab getRecitationDataTab() {
        return recitationDataTab;
    }

    public ScheduleDataTab getScheduleDataTab() {
        return scheduleDataTab;
    }
    
    
    
    public TADataTab getTADataTab() {
        return taDataTab;
    }
    
    
    public TabPane getTabPane(){
        return tabPane;
    }
    
    public Tab getTAData(){
        return taData;
    }

    public CourseDetailsTab getCourseDetailsTab() {
        return courseDetailsTab;
    }

    public Tab getCourseDetails() {
        return courseDetails;
    }

    public Tab getTaData() {
        return taData;
    }

    public Tab getRecitationData() {
        return recitationData;
    }

    public Tab getScheduleData() {
        return scheduleData;
    }

    public Tab getProjectData() {
        return projectData;
    }
    
    
    
    
}
