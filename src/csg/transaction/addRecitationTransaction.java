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
public class addRecitationTransaction implements jTPS_Transaction{
    String section;
    String instructor;
    String dayTime;
    String location;
    String ta1;
    String ta2;
    CSGData data;
    
    public addRecitationTransaction(String initSection,String initInstructor, 
            String initDayTime, String initLocation, String initTa1, String initTa2, CSGData initData){
        
        section = initSection;
        instructor = initInstructor;
        dayTime = initDayTime;
        location = initLocation;
        ta1 = initTa1;
        ta2 = initTa2;
        data = initData;
        
    }
    
    
    
    @Override
    public void doTransaction() {
        // ADD THE NEW TA TO THE DATA
            data.addRecitation(section, instructor, dayTime, location, ta1, ta2);
    }

    @Override
    public void undoTransaction() {
        data.removeRec(section);
    }
    
}
