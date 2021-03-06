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
public class Student <E extends Comparable<E>> implements Comparable<E>{
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty team;
    private final StringProperty role;
    
    public Student(String initFirstName, String initLastName, String initTeam, String initRole){
        firstName = new SimpleStringProperty(initFirstName);
        lastName = new SimpleStringProperty(initLastName);
        team = new SimpleStringProperty(initTeam);
        role = new SimpleStringProperty(initRole);
    }
    
    
    
    @Override
    public int compareTo(E otherName) {
        return getFirstName().compareTo(((Student)otherName).getFirstName());
    }

    public String getFirstName() {
        return firstName.get();
    }

    public String getLastName() {
        return lastName.get();
    }

    public String getTeam() {
        return team.get();
    }

    public String getRole() {
        return role.get();
    }
    
    
    public void setFirstName(String initFirstName) {
        firstName.set(initFirstName);
    }

    public void getLastName(String initLastName) {
        lastName.set(initLastName);
    }

    public void setTeam(String initTeam) {
        team.set(initTeam);
    }

    public void setRole(String initRole) {
        role.set(initRole);
    }
    
    
    
    
}
