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
public class addScheduleTrans implements jTPS_Transaction {

    String type;
    String date;
    String title;
    String topic;
    String link;
    String time;
    String criteria;
    CSGData data;

    public addScheduleTrans(String type, String date, String title, String topic, String link,
            String time, String criteria, CSGData data) {
        this.type = type;
        this.date = date;
        this.title = title;
        this.topic = topic;
        this.link = link;
        this.time = time;
        this.criteria = criteria;
        this.data = data;
    }

    @Override
    public void doTransaction() {
        data.addSchedule(type, date, title, topic, link, time, criteria);
    }

    @Override
    public void undoTransaction() {
        data.removeSchedule(date, title);
    }

}
