package csg.data;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class represents a Teaching Assistant for the table of TAs.
 * 
 * @author Richard McKenna
 */
public class TeachingAssistant<E extends Comparable<E>> implements Comparable<E>  {
    // THE TABLE WILL STORE TA NAMES AND EMAILS
    private final StringProperty name;
    private final StringProperty email;
    private BooleanProperty undergrad;

    

    /**
     * Constructor initializes both the TA name and email.
     */
    public TeachingAssistant(String initName, String initEmail) {
        name = new SimpleStringProperty(initName);
        email = new SimpleStringProperty(initEmail);
        undergrad = new SimpleBooleanProperty();
    }
    
    public TeachingAssistant(String initName, String initEmail, Boolean bool) {
        name = new SimpleStringProperty(initName);
        email = new SimpleStringProperty(initEmail);
        undergrad = new SimpleBooleanProperty(bool);
    }

    // ACCESSORS AND MUTATORS FOR THE PROPERTIES
    public boolean getUndergrad() {
        return undergrad.get();
    }
    public BooleanProperty undergradProperty(){
        
        return undergrad;
    }
    
    public void setUndergrad(boolean initUndergrad) {
        undergrad.set(initUndergrad);
    }
    

    public String getName() {
        return name.get();
    }

    public void setName(String initName) {
        name.set(initName);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String initEmail) {
        email.set(initEmail);
    }

    @Override
    public int compareTo(E otherTA) {
        return getName().compareTo(((TeachingAssistant)otherTA).getName());
    }
    
    @Override
    public String toString() {
        return name.getValue();
    }
}