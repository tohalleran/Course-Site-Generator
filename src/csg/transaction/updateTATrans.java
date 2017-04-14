/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transaction;

import csg.data.CSGData;
import java.util.HashMap;
import javafx.scene.control.Label;
import jtps.jTPS_Transaction;

/**
 *
 * @author tonyohalleran
 */
public class updateTATrans implements jTPS_Transaction {

    CSGData data;
    String origName;
    String updatedName;
    String email;
    HashMap<String, Label> labels;

    public updateTATrans(String name, String updatedName, String email, HashMap<String, Label> labels, CSGData data) {
        origName = name;
        this.updatedName = updatedName;
        this.email = email;
        this.labels = labels;
        this.data = data;

    }

    @Override
    public void doTransaction() {
        for (Label label : labels.values()) {
                if (label.getText().equals(origName)
                        || (label.getText().contains(origName + "\n"))
                        || (label.getText().contains("\n" + origName))) {
                    data.updateTAFromCell(label.textProperty(), origName, updatedName);
                }
            }
            //  GO UPDATE TA
            data.removeTA(origName);
            data.addTA(updatedName, email);
    }

    @Override
    public void undoTransaction() {
        for (Label label : labels.values()) {
            if (label.getText().equals(updatedName)
                    || (label.getText().contains(updatedName + "\n"))
                    || (label.getText().contains("\n" + updatedName))) {
                data.updateTAFromCell(label.textProperty(), updatedName, origName);
            }
        }

        //  GO UPDATE TA
        data.removeTA(updatedName);
        data.addTA(origName, email);
    
    }

}
