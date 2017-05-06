/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author tonyohalleran
 */
public class Recitation <E extends Comparable<E>> implements Comparable<E>{
    private final StringProperty section;
    private final StringProperty instructor;
    private final StringProperty dayTime;
    private final StringProperty location;
    private final StringProperty ta1;
    private final StringProperty ta2;
         
    public Recitation(String section, String instructor, String dayTime, String location,
            String ta1, String ta2){
    
        this.section = new SimpleStringProperty(section);
        this.instructor = new SimpleStringProperty(instructor);
        this.dayTime = new SimpleStringProperty(dayTime);
        this.location = new SimpleStringProperty(location);
        this.ta1 = new SimpleStringProperty(ta1);
        this.ta2 = new SimpleStringProperty(ta2);
    }
    
    
    
    @Override
    public int compareTo(E otherRec) {
        return getSection().compareTo(((Recitation)otherRec).getSection());
    }
    

    public String getSection() {
        return section.get();
    }

    public String getInstructor() {
        return instructor.get();
    }

    public String getDayTime() {
        return dayTime.get();
    }

    public String getLocation() {
        return location.get();
    }

    public String getTa1() {
        return ta1.get();
    }

    public String getTa2() {
        return ta2.get();
    }
    
    
    public void setSection(String initSection) {
        section.set(initSection);
    }

    public void setInstructor(String initInstructor) {
        instructor.set(initInstructor);
    }

    public void setDayTime(String initDayTime) {
        dayTime.set(initDayTime);
    }

    public void setLocation(String initLocation) {
        location.set(initLocation);
    }

    public void setTa1(String initTA1) {
        ta1.set(initTA1);
    }

    public void setTa2(String initTA2) {
        ta2.set(initTA2);
    }
    
    
    
}
