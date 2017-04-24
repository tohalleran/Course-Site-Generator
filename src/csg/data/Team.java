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
public class Team <E extends Comparable<E>> implements Comparable<E>{
    private final StringProperty name;
    private final StringProperty color;
    private final StringProperty colorText;
    private final StringProperty link;
    
    public Team(String initName, String initColor, String initColorText, String initLink){
        name = new SimpleStringProperty(initName);
        color = new SimpleStringProperty(initColor);
        colorText = new SimpleStringProperty(initColorText);
        link = new SimpleStringProperty(initLink);
    }
    
    
    @Override
    public int compareTo(E otherTeam) {
        return getName().compareTo(((Team)otherTeam).getName());
    }

    public String getName() {
        return name.get();
    }

    public String getColor() {
        return color.get();
    }

    public String getColorText() {
        return colorText.get();
    }

    public String getLink() {
        return link.get();
    }
    
    public void setName(String initName) {
        name.set(initName);
    }

    public void setColor(String initColor) {
        color.set(initColor);
    }

    public void setColorText(String initColorText) {
        colorText.set(initColorText);
    }

    public void setLink(String initLink) {
        link.set(initLink);
    }
    
    
    
}
