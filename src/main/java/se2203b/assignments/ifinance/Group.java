package se2203b.assignments.ifinance;

import javafx.beans.property.*;

public class Group {
    private IntegerProperty id;
    private StringProperty name;
    private ObjectProperty<Group> parent;
    private ObjectProperty<AccountCategory> element;
    // Constructors
    public Group() {
        this(0, "", null, null);


    }

    public Group(int id, String name, Group parent, AccountCategory category) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.parent = new SimpleObjectProperty(parent);
        this.element = new SimpleObjectProperty(category);
    }

    //set and get methods
    // id property
    public void setID(int id) {
        this.id = new SimpleIntegerProperty(id);
    }
    public IntegerProperty idProperty() {
        return this.id;
    }
    public int getID() {
        return this.id.get();
    }

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

    // parent Property
    public void setParent(Group parent) {
        this.parent.set(parent);
    }
    public ObjectProperty <Group> parentProperty() {
        return this.parent;
    }
    public Group getParent() {
        return this.parent.get();
    }

    // element Property
    public void setElement(AccountCategory element) {
        this.element.set(element);
    }
    public ObjectProperty <AccountCategory> elementProperty() {
        return this.element;
    }
    public AccountCategory getElement() {
        return this.element.get();
    }


}