/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transaction;

import csg.data.CSGData;
import csg.files.TimeSlot;
import csg.workspaces.CSGWorkspace;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.control.Label;
import jtps.jTPS_Transaction;


/**
 *
 * @author tonyohalleran
 */
public class deleteTATransaction implements jTPS_Transaction{
    CSGData data;
    String name;
    String email;
    CSGWorkspace workspace;
    ArrayList<TimeSlot> officeHours;
            
    
    public deleteTATransaction(String name, String email, CSGData data, CSGWorkspace workspace, ArrayList<TimeSlot> officeHours){
        this.name = name;
        this.email = email;
        this.data = data;
        this.workspace = workspace;
        this.officeHours = officeHours;
    }
    @Override
    public void doTransaction() {
        data.removeTA(name);
                // AND BE SURE TO REMOVE ALL THE TA'S OFFICE HOURS
                HashMap<String, Label> labels = workspace.getTADataTab().getOfficeHoursGridTACellLabels();
                for (Label label : labels.values()) {
                    if (label.getText().equals(name)
                            || (label.getText().contains(name + "\n"))
                            || (label.getText().contains("\n" + name))) {
                        data.removeTAFromCell(label.textProperty(), name);
                    }
                }
    }

    @Override
    public void undoTransaction() {
        data.addTA(name, email);
        
        int endHour = data.getEndHour();
        int startHour = data.getStartHour();
        
        for (int i = 0; i < officeHours.size(); i++) {
                    TimeSlot taTS = officeHours.get(i);
                    String day = taTS.getDay();
                    String time = taTS.getTime();
                    String taName = taTS.getName();

                    int underscoreIndex = time.indexOf("_");
                    int timeInt = Integer.parseInt(time.substring(0, underscoreIndex));

                    if (time.contains("pm") && !time.contains("12")) {
                        timeInt += 12;
                    }
                    if (timeInt <= endHour && timeInt >= startHour 
                       && taName.equals(name)) {
                        data.addOfficeHoursReservation(day, time, taName);
                    }
                }
    }
    
}
