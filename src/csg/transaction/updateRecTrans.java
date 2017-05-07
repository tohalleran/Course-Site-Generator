/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transaction;

import csg.data.CSGData;
import jtps.jTPS_Transaction;

/**
 *
 * @author tonyohalleran
 */
public class updateRecTrans implements jTPS_Transaction {

    String oldSection;
    String oldInstructor;
    String oldDayTime;
    String oldLocation;
    String oldTa1;
    String oldTa2;
    String newSection;
    String newInstructor;
    String newDayTime;
    String newLocation;
    String newTa1;
    String newTa2;
    CSGData data;

    public updateRecTrans(String oldSection, String oldInstructor, String oldDayTime, String oldLocation,
            String oldTa1, String oldTa2, String newSection, String newInstructor, String newDayTime,
            String newLocation, String newTa1, String newTa2, CSGData data) {
        this.oldSection = oldSection;
        this.oldInstructor = oldInstructor;
        this.oldDayTime = oldDayTime;
        this.oldLocation = oldLocation;
        this.oldTa1 = oldTa1;
        this.oldTa2 = oldTa2;
        this.newSection = newSection;
        this.newInstructor = newInstructor;
        this.newDayTime = newDayTime;
        this.newLocation = newLocation;
        this.newTa1 = newTa1;
        this.newTa2 = newTa2;
        this.data = data;

    }

    @Override
    public void doTransaction() {
        //  GO UPDATE REC
            data.removeRec(oldSection);
            data.addRecitation(newSection, newInstructor, newDayTime, newLocation, newTa1, newTa2);
    }

    @Override
    public void undoTransaction() {
        //  GO UPDATE REC
            data.removeRec(newSection);
            data.addRecitation(oldSection, oldInstructor,oldDayTime, oldLocation, oldTa1, oldTa2);
    }

}
