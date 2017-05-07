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
public class updateTeamTrans implements jTPS_Transaction{
    String name;
    String color;
    String colorText;
    String link;
    CSGData data;
    String oldname;
    String oldcolor;
    String oldcolorText;
    String oldlink;

    public updateTeamTrans(String initname, String initcolor, String initcolorText, String initlink, 
            CSGData initdata, String oldName,String oldColor,String oldColorText,String oldLink){
        name = initname;
        color = initcolor;
        colorText = initcolorText;
        link = initlink;
        data= initdata; 
        oldname = oldName;
        oldcolor = oldColor;
        oldcolorText = oldColorText;
        oldlink = oldLink;
        
    }
    
    @Override
    public void doTransaction() {
        data.removeTeam(oldname, oldcolor, oldcolorText, oldlink);
        data.addTeam(name, color, colorText, link);
    }

    @Override
    public void undoTransaction() {
        data.removeTeam(name, color, color, link);
        data.addTeam(oldname, oldcolor, oldcolorText, oldlink);
        
    }
    
}
