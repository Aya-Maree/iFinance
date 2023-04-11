package se2203b.assignments.ifinance;

import javafx.beans.property.*;

public abstract class iFINANCEUser {

    private IntegerProperty id;
    private StringProperty fullName;

    private ObjectProperty<UserAccount> uAccount
            = new SimpleObjectProperty(new UserAccount());

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
    public void setFullName(String name) {
        this.fullName = new SimpleStringProperty(name);
    }
    public StringProperty fullNameProperty() {
        return this.fullName;
    }
    public String getFullName() {
        return this.fullName.get();
    }

    // userAccount Property
    public void setuAccount(UserAccount uAccount) {

        this.uAccount.set(uAccount);
    }
    public ObjectProperty<UserAccount> uAccountProperty() {
        return this.uAccount;
    }
    public UserAccount getuAccount() {
        return this.uAccount.get();
    }

}
