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
public class updateScheduleTrans implements jTPS_Transaction{
    String type;
    String date;
    String title;
    String topic;
    String link;
    String time;
    String criteria;
    CSGData data;
    String newType;
    String newDate;
    String newTitle;
    String newTopic;
    String newLink;
    String newTime;
    String newCriteria;
    
    public updateScheduleTrans(String type, String date, String title, String topic,
            String link, String time, String criteria, CSGData data, String newType, String newDate,
            String newTitle, String newTopic, String newLink, String newTime, String newCriteria) {
    
        this.type = type;
        this.date = date;
        this.title = title;
        this.topic = topic;
        this.link = link;
        this.time = time;
        this.criteria = criteria;
        this.data = data;

        this.newType = newType;
        this.newDate = newDate;
        this.newTitle = newTitle;
        this.newTopic = newTopic;
        this.newLink = newLink;
        this.newTime = newTime;
        this.newCriteria = newCriteria;
    
    
    
    }
    
    
    
    @Override
    public void doTransaction() {
        data.removeSchedule(date, title);
        data.addSchedule(newType, newDate, newTitle, newTopic, newLink, newTime, newCriteria);
    }

    @Override
    public void undoTransaction() {
        data.removeSchedule(newDate, newTitle);
        data.addSchedule(type, date, title, topic, link, time, criteria);
    }
    
}
