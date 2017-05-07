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
public class addStudentTrans implements jTPS_Transaction {

    String firstName;
    String lastName;
    String team;
    String role;
    CSGData data;

    public addStudentTrans(String initfirstName, String initlastName, String initteam, String initrole,
            CSGData initdata) {
        firstName = initfirstName;
        lastName = initlastName;
        team = initteam;
        role = initrole;
        data = initdata;
    }

    @Override
    public void doTransaction() {
        data.addStudent(firstName, lastName, team, role);
    }

    @Override
    public void undoTransaction() {
        data.removeStudent(firstName, lastName, team, role);
    }

}
