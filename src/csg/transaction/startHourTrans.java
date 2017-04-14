/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transaction;

import static csg.CSGManagerProp.INVALID_TIME_MESSAGE;
import static csg.CSGManagerProp.INVALID_TIME_TITLE;
import static csg.CSGManagerProp.WARNING_TIME_MESSAGE;
import static csg.CSGManagerProp.WARNING_TIME_TITLE;
import csg.data.CSGData;
import csg.files.TimeSlot;
import csg.workspaces.CSGWorkspace;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppYesNoCancelDialogSingleton;
import java.util.ArrayList;
import jtps.jTPS_Transaction;
import properties_manager.PropertiesManager;


/**
 *
 * @author tonyohalleran
 */
public class startHourTrans implements jTPS_Transaction {

    CSGData data;
    CSGWorkspace workspace;
    String startHourValue;
    String origStartHour;
    ArrayList<TimeSlot> officeHours;

    public startHourTrans(CSGData data, CSGWorkspace workspace, String startHourValue, String origStartHour, ArrayList<TimeSlot> officeHours) {
        this.data = data;
        this.workspace = workspace;
        this.startHourValue = startHourValue;
        this.origStartHour = origStartHour;
        this.officeHours = officeHours;
    }

    @Override
    public void doTransaction() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        if (Integer.parseInt(startHourValue) < data.getEndHour()) {
            // WARNING MESSAGE
            AppYesNoCancelDialogSingleton dialog = AppYesNoCancelDialogSingleton.getSingleton();
            dialog.show(props.getProperty(WARNING_TIME_TITLE), props.getProperty(WARNING_TIME_MESSAGE));

            if (dialog.getSelection().equals("Yes")) {
                //  CLEAR WORKSPACE
                workspace.resetWorkspace();

                String endHour = String.valueOf(data.getEndHour());

                //  UPDATE TA HOURS GRID
                data.initHours(startHourValue, endHour);

                //  ADD IN TA'S HOURS
                for (int i = 0; i < officeHours.size(); i++) {
                    TimeSlot taTS = officeHours.get(i);
                    String day = taTS.getDay();
                    String time = taTS.getTime();
                    String name = taTS.getName();

                    int underscoreIndex = time.indexOf("_");
                    int timeInt = Integer.parseInt(time.substring(0, underscoreIndex));

                    if (time.contains("pm") && !time.contains("12")) {
                        timeInt += 12;
                    }
                    if (timeInt >= Integer.parseInt(startHourValue)) {
                        data.addOfficeHoursReservation(day, time, name);
                    }
                }
            }
        } else {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(INVALID_TIME_TITLE), props.getProperty(INVALID_TIME_MESSAGE));
        }
    }

    @Override
    public void undoTransaction() {
        //  CLEAR WORKSPACE
        workspace.resetWorkspace();

        String endHour = String.valueOf(data.getEndHour());

        //  UPDATE TA HOURS GRID
        data.initHours(origStartHour, endHour);

        //  ADD IN TA'S HOURS
        for (int i = 0; i < officeHours.size(); i++) {
            TimeSlot taTS = officeHours.get(i);
            String day = taTS.getDay();
            String time = taTS.getTime();
            String name = taTS.getName();

            int underscoreIndex = time.indexOf("_");
            int timeInt = Integer.parseInt(time.substring(0, underscoreIndex));

            if (time.contains("pm") && !time.contains("12")) {
                timeInt += 12;
            }
            if (timeInt >= Integer.parseInt(origStartHour)) {
                data.addOfficeHoursReservation(day, time, name);
            }
        }

    }

}
