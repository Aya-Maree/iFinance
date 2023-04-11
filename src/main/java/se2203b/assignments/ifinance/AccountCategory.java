package se2203b.assignments.ifinance;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AccountCategory {
    private StringProperty name;
    private StringProperty type;

    // Constructors
    public AccountCategory() {
        this( "", "");
    }

    public AccountCategory(String name, String type) {
        this.name = new SimpleStringProperty(name);
        this.type = new SimpleStringProperty(type);
    }

    //set and get methods
    // name property
    public void setName(String name) {
        this.name.set(name);
    }
    public StringProperty nameProperty() {
        return this.name;
    }
    public String getName() {
        return this.name.get();
    }

    // type property
    public void setType(String type) {
        this.type.set(type);
    }
    public StringProperty typeProperty() {
        return this.type;
    }
    public String getType() {
        return this.type.get();
    }
}
