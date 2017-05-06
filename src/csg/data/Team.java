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
    public String getRed() {
        String red = color.get().substring(0,2);
        if(red.substring(0, 1).equals("0"))
            return red.substring(1);
        else
            return red;
    }
    public String getGreen() {
        String green = color.get().substring(2,4);
        if(green.substring(0, 1).equals("0"))
            return green.substring(1);
        else
            return green;
    }
    public String getBlue() {
        String blue = color.get().substring(4);
        if(blue.substring(0, 1).equals("0"))
            return blue.substring(1);
        else
            return blue;
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
