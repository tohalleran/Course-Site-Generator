/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.style;

import csg.CSGManagerApp;
import csg.data.TeachingAssistant;
import csg.workspaces.CSGWorkspace;
import csg.workspaces.CourseDetailsTab;
import csg.workspaces.TADataTab;
import djf.AppTemplate;
import djf.components.AppStyleComponent;

import java.util.HashMap;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 *
 * @author tonyohalleran
 */
public class CSGStyle extends AppStyleComponent {

    // FIRST WE SHOULD DECLARE ALL OF THE STYLE TYPES WE PLAN TO USE
    
    // COURSE DETAILS TAB
    public static String CLASS_COURSE_DETAILS_CHANGE_BUTTON = "class_button";
    
  

    // TA DATA TAB
    // WE'LL USE THIS FOR ORGANIZING LEFT AND RIGHT CONTROLS
    public static String CLASS_PLAIN_PANE = "plain_pane";

    public static String CLASS_TAB_PANE = "tab_pane";
    public static String CLASS_TAB = "tab";
    

    // THESE ARE THE HEADERS FOR EACH SIDE
    public static String CLASS_HEADER_PANE = "header_pane";
    public static String CLASS_HEADER_LABEL = "header_label";

    // ON THE LEFT WE HAVE THE TA ENTRY
    public static String CLASS_TA_TABLE = "ta_table";
    public static String CLASS_TA_TABLE_COLUMN_HEADER = "ta_table_column_header";
    public static String CLASS_ADD_TA_PANE = "add_ta_pane";
    public static String CLASS_ADD_TA_TEXT_FIELD = "add_ta_text_field";
    public static String CLASS_ADD_TA_BUTTON = "add_ta_button";

    // ON THE RIGHT WE HAVE THE OFFICE HOURS GRID
    public static String CLASS_OFFICE_HOURS_GRID = "office_hours_grid";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_PANE = "office_hours_grid_time_column_header_pane";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_LABEL = "office_hours_grid_time_column_header_label";
    public static String CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_PANE = "office_hours_grid_day_column_header_pane";
    public static String CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_LABEL = "office_hours_grid_day_column_header_label";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_CELL_PANE = "office_hours_grid_time_cell_pane";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_CELL_LABEL = "office_hours_grid_time_cell_label";
    public static String CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE = "office_hours_grid_ta_cell_pane";
    public static String CLASS_OFFICE_HOURS_GRID_TA_CELL_LABEL = "office_hours_grid_ta_cell_label";

    // FOR HIGHLIGHTING CELLS, COLUMNS, AND ROWS
    public static String CLASS_HIGHLIGHTED_GRID_CELL = "highlighted_grid_cell";
    public static String CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN = "highlighted_grid_row_or_column";

    // THIS PROVIDES ACCESS TO OTHER COMPONENTS
    private AppTemplate app;

    /**
     * This constructor initializes all style for the application.
     *
     * @param initApp The application to be stylized.
     */
    public CSGStyle(AppTemplate initApp) {
        // KEEP THIS FOR LATER
        app = initApp;

        // LET'S USE THE DEFAULT STYLESHEET SETUP
        super.initStylesheet(app);

        // INIT THE STYLE FOR THE FILE TOOLBAR
        app.getGUI().initFileToolbarStyle();

        // AND NOW OUR WORKSPACE STYLE
        initCSGWorkspaceStyle();
    }

    /**
     * This function specifies all the style classes for all user interface
     * controls in the workspace.
     */
    private void initCSGWorkspaceStyle() {
        CSGWorkspace workspaceComponent = (CSGWorkspace) app.getWorkspaceComponent();

        //COURSE DETAILS TAB
        CourseDetailsTab courseDetailsTab = workspaceComponent.getCourseDetailsTab();
        //BUTTONS
        courseDetailsTab.getBannerSchoolImgChangeButton().getStyleClass().add(CLASS_COURSE_DETAILS_CHANGE_BUTTON);
        courseDetailsTab.getLeftFooterImgChangeButton().getStyleClass().add(CLASS_COURSE_DETAILS_CHANGE_BUTTON);
        courseDetailsTab.getRightFooterImgChangeButton().getStyleClass().add(CLASS_COURSE_DETAILS_CHANGE_BUTTON);

        //LABELS
        
        
        //TA DATA TAB
        // LEFT SIDE - THE HEADER
        TADataTab taDataTab = workspaceComponent.getTADataTab();
        taDataTab.getTAsHeaderBox().getStyleClass().add(CLASS_HEADER_PANE);
        taDataTab.getTAsHeaderLabel().getStyleClass().add(CLASS_HEADER_LABEL);

        workspaceComponent.getTabPane().getStyleClass().add(CLASS_TAB_PANE);
        workspaceComponent.getCourseDetailsTab().getCourseDetailsWorkspace().getStyleClass().add(CLASS_TAB);
    //    workspaceComponent.getTADataTab().getTADataWorkspace().getStyleClass().add(CLASS_TAB);
        workspaceComponent.getRecitationDataTab().getRecitationDataWorkspace().getStyleClass().add(CLASS_TAB);
        workspaceComponent.getScheduleDataTab().getScheduleDataWorkspace().getStyleClass().add(CLASS_TAB);
        workspaceComponent.getProjectDataTab().getProjectDataWorkspace().getStyleClass().add(CLASS_TAB);
        

        // LEFT SIDE - THE TABLE
        TableView<TeachingAssistant> taTable = taDataTab.getTATable();
        taTable.getStyleClass().add(CLASS_TA_TABLE);
        for (TableColumn tableColumn : taTable.getColumns()) {
            tableColumn.getStyleClass().add(CLASS_TA_TABLE_COLUMN_HEADER);
        }

        // LEFT SIDE - THE TA DATA ENTRY
        taDataTab.getAddBox().getStyleClass().add(CLASS_ADD_TA_PANE);
        taDataTab.getNameTextField().getStyleClass().add(CLASS_ADD_TA_TEXT_FIELD);
        taDataTab.getEmailTextField().getStyleClass().add(CLASS_ADD_TA_TEXT_FIELD);
        taDataTab.getAddButton().getStyleClass().add(CLASS_ADD_TA_BUTTON);
        taDataTab.getClearButton().getStyleClass().add(CLASS_ADD_TA_BUTTON);

        // RIGHT SIDE - THE HEADER
        taDataTab.getOfficeHoursSubheaderBox().getStyleClass().add(CLASS_HEADER_PANE);
        taDataTab.getOfficeHoursSubheaderLabel().getStyleClass().add(CLASS_HEADER_LABEL);
    }

    /**
     * This method initializes the style for all UI components in the office
     * hours grid. Note that this should be called every time a new TA Office
     * Hours Grid is created or loaded.
     */
    public void initOfficeHoursGridStyle() {
        // RIGHT SIDE - THE OFFICE HOURS GRID TIME HEADERS
        CSGWorkspace workspaceComponent = (CSGWorkspace) app.getWorkspaceComponent();
        TADataTab taDataTab = workspaceComponent.getTADataTab();
        taDataTab.getOfficeHoursGridPane().getStyleClass().add(CLASS_OFFICE_HOURS_GRID);
        setStyleClassOnAll(taDataTab.getOfficeHoursGridTimeHeaderPanes(), CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_PANE);
        setStyleClassOnAll(taDataTab.getOfficeHoursGridTimeHeaderLabels(), CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_LABEL);
        setStyleClassOnAll(taDataTab.getOfficeHoursGridDayHeaderPanes(), CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_PANE);
        setStyleClassOnAll(taDataTab.getOfficeHoursGridDayHeaderLabels(), CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_LABEL);
        setStyleClassOnAll(taDataTab.getOfficeHoursGridTimeCellPanes(), CLASS_OFFICE_HOURS_GRID_TIME_CELL_PANE);
        setStyleClassOnAll(taDataTab.getOfficeHoursGridTimeCellLabels(), CLASS_OFFICE_HOURS_GRID_TIME_CELL_LABEL);
        setStyleClassOnAll(taDataTab.getOfficeHoursGridTACellPanes(), CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);
        setStyleClassOnAll(taDataTab.getOfficeHoursGridTACellLabels(), CLASS_OFFICE_HOURS_GRID_TA_CELL_LABEL);
    }

    /**
     * This helper method initializes the style of all the nodes in the nodes
     * map to a common style, styleClass.
     */
    private void setStyleClassOnAll(HashMap nodes, String styleClass) {
        for (Object nodeObject : nodes.values()) {
            Node n = (Node) nodeObject;
            n.getStyleClass().add(styleClass);
        }
    }

}
