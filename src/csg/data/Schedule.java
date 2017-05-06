/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author tonyohalleran
 */
public class Schedule <E extends Comparable<E>> implements Comparable<E> {
    private final StringProperty type;
    private final StringProperty date;
    private final StringProperty title;
    private final StringProperty topic;
    private final StringProperty link;
    private final StringProperty time;
    private final StringProperty criteria;
    
    public Schedule(String initType, String initDate, String initTitle, String initTopic, String initLink,
            String initTime, String initCriteria){
        type = new SimpleStringProperty(initType);
        date = new SimpleStringProperty(initDate);
        title = new SimpleStringProperty(initTitle);
        topic = new SimpleStringProperty(initTopic);
        link = new SimpleStringProperty(initLink);
        time = new SimpleStringProperty(initTime);
        criteria = new SimpleStringProperty(initCriteria);
    }
    
    
    @Override
    public int compareTo(E otherSchedule) {
        try{
            Date date = new SimpleDateFormat("yyyy-mm-dd").parse(getDate());
            Date otherDate = new SimpleDateFormat("yyyy-mm-dd").parse(((Schedule)otherSchedule).getDate());
            
            return date.compareTo(otherDate);
            
        }catch(ParseException e){
            System.out.println("Error in Class Schedule compareTo() method");
            return -2;
        }
    }

    public String getType() {
        return type.get();
    }

    public String getDate() {
        return date.get();
    }
    public String getMonth() {
        String month = date.get().substring(5, 7);
        if(month.substring(0,1).equals("0"))
            return month.substring(1);
        else
            return month;
    }
    public String getDay() {
        String day = date.get().substring(8);
        if(day.substring(0,1).equals("0"))
            return day.substring(1);
        else
            return day;
    }

    public String getTitle() {
        return title.get();
    }

    public String getTopic() {
        return topic.get();
    }
    public String getLink(){
        return link.get();
    }
    public String getTime(){
        return time.get();
    }
    public String getCriteria(){
        return criteria.get();
    }
    
    public void setType(String initType) {
        type.set(initType);
    }

    public void setDate(String initDate) {
        date.set(initDate);
    }

    public void setTitle(String initTitle) {
        title.set(initTitle);
    }

    public void setTopic(String initTopic) {
        topic.set(initTopic);
    }
    public void setLink(String initLink){
        link.set(initLink);
    }
    public void setTime(String initTime){
        time.set(initTime);
    }
    public void setCriteria(String initCriteria){
        criteria.set(initCriteria);
    }
    
}
