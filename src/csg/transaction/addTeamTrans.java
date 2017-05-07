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
public class addTeamTrans implements jTPS_Transaction{
    String name;
    String color;
    String colorText;
    String link;
    CSGData data;
    
    public addTeamTrans(String initname, String initcolor, String initcolorText, String initlink, 
            CSGData initdata){
        name = initname;
        color = initcolor;
        colorText = initcolorText;
        link = initlink;
        data= initdata;        
    }
    
    @Override
    public void doTransaction() {
        data.addTeam(name, color, colorText, link);
    }

    @Override
    public void undoTransaction() {
        data.removeTeam(name, color, colorText, link);
    }
    
}
