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
    
    public Schedule(String initType, String initDate, String initTitle, String initTopic){
        type = new SimpleStringProperty(initType);
        date = new SimpleStringProperty(initDate);
        title = new SimpleStringProperty(initTitle);
        topic = new SimpleStringProperty(initTopic);
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

    public String getTitle() {
        return title.get();
    }

    public String getTopic() {
        return topic.get();
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
    
    
}
