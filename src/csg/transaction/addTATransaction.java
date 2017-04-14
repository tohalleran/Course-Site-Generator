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
public class addTATransaction implements jTPS_Transaction {
    CSGData ta;
    String email;
    String name;

    public addTATransaction(String name, String email, CSGData data){
        ta = data;
        this.name = name;
        this.email = email;
    }
    
    @Override
    public void doTransaction() {
        ta.addTA(name, email);
    }

    @Override
    public void undoTransaction() {
        ta.removeTA(name);
    }
}
