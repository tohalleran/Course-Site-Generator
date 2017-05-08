/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.test_bed;

import csg.CSGManagerApp;
import csg.data.CSGData;
import static csg.data.CSGData.MAX_END_HOUR;
import static csg.data.CSGData.MIN_START_HOUR;
import csg.files.CSGFiles;
import csg.workspaces.TADataTab;
import static djf.settings.AppStartupConstants.APP_PROPERTIES_FILE_NAME;
import java.io.IOException;
import java.util.HashMap;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;

/**
 *
 * @author tonyohalleran
 */
public class TestSave {
    public static void main(String[] args){
        CSGManagerApp app = new CSGManagerApp();
        app.loadProperties(APP_PROPERTIES_FILE_NAME);
//        CSGFiles file = (CSGFiles) app.getFileComponent();
//        CSGData data = (CSGData) app.getDataComponent();
        CSGFiles file = new CSGFiles(app);
        CSGData data = new CSGData(app);
        
        // ADD GRID HEADERS
        for (int i = 0; i < data.getGridHeaders().size(); i++){
            data.getOfficeHours().put(i + "_0", new SimpleStringProperty(data.getGridHeaders().get(i)));
        }
        // ADD TIMESLOTS AND DATA
        data.getOfficeHours().put("0_1", new SimpleStringProperty("9:00am"));
        data.getOfficeHours().put("1_1", new SimpleStringProperty("9:30am"));
        data.getOfficeHours().put("2_1", new SimpleStringProperty("Tony O'Halleran"));
        data.getOfficeHours().put("3_1", new SimpleStringProperty("Tony O'Halleran"));
        data.getOfficeHours().put("4_1", new SimpleStringProperty("Tony O'Halleran"));
        data.getOfficeHours().put("5_1", new SimpleStringProperty("Tony O'Halleran"));
        data.getOfficeHours().put("6_1", new SimpleStringProperty("Tony O'Halleran"));
        
        data.getOfficeHours().put("0_2", new SimpleStringProperty("9:30am"));
        data.getOfficeHours().put("1_2", new SimpleStringProperty("10:00am"));
        data.getOfficeHours().put("2_2", new SimpleStringProperty("Tony O'Halleran"));
        data.getOfficeHours().put("3_2", new SimpleStringProperty("Tony O'Halleran"));
        data.getOfficeHours().put("4_2", new SimpleStringProperty("Tony O'Halleran"));
        data.getOfficeHours().put("5_2", new SimpleStringProperty("Tony O'Halleran"));
        data.getOfficeHours().put("6_2", new SimpleStringProperty("Tony O'Halleran"));
        
        data.changeTimeFrames(9, 10);
        
        
        data.initCourseInfo("CSE", "219", "Fall", "2017",  "Computer Science I", "Richard McKenna", 
                "https://www.cs.stonybrook.edu/~richard", "~/Documents" , "~/Desktop", "", "",
            "", "sea_wolf.css");
        data.addTA("Tony O'Halleran", "tony.ohalleran@stonybrook.edu");
        data.addRecitation("R02", "Richard McKenna", "Wednesday, 3:30pm-4:23pm", "Old CS 2114",
           "Jane Doe", "Joe Shmo");
//        data.addSchedule("Holiday", "2017-02-09", "SNOW DAY", "Event Programming");
        data.initSchedule("2017-04-17", "2017-04-28");
        data.addTeam("Atomic Comics", "552211", "FFFFFF", "https://c4-comics.appspot.com");
        data.addStudent("Jane", "Doe", "Atomic Comics", "Lead Programmer");
        
        
            
            file.exportData(data, "./work/SiteSaveTest.json");
            
       
    }
    
    
}
