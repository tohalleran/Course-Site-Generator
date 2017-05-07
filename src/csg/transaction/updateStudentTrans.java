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
public class updateStudentTrans implements jTPS_Transaction{
    String firstName;
    String lastName;
    String team;
    String role;
    CSGData data;
    String oldfirstName;
    String oldlastName;
    String oldteam;
    String oldrole;
    
    
    public updateStudentTrans(String initfirstName, String initlastName, String initteam, String initrole,
            CSGData initdata, String oldfirstName,String oldlastName, String oldteam, String oldrole) {
        firstName = initfirstName;
        lastName = initlastName;
        team = initteam;
        role = initrole;
        data = initdata;
        this.oldfirstName = oldfirstName;
        this.oldlastName = oldlastName;
        this.oldteam = oldteam;
        this.oldrole = oldrole;
    }
    
    
    @Override
    public void doTransaction() {
        data.removeStudent(oldfirstName, oldlastName, oldteam, oldrole);
        data.addStudent(firstName, lastName, team, role);
    }

    @Override
    public void undoTransaction() {
        data.removeStudent(firstName, lastName, team, role);
        data.addStudent(oldfirstName, oldlastName, oldteam, oldrole);
    }
    
}
