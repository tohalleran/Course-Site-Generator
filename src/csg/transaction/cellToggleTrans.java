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
public class cellToggleTrans implements jTPS_Transaction{
    CSGData data;
    String name;
    String cellKey;
    
    public cellToggleTrans(String name, String cellKey, CSGData data){
        this.name= name;
        this.cellKey = cellKey;
        this.data = data;
    }
    
    @Override
    public void doTransaction() {
        data.toggleTAOfficeHours(cellKey, name);
    }

    @Override
    public void undoTransaction() {
        data.toggleTAOfficeHours(cellKey, name);
    }
    
}
