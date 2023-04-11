package se2203b.assignments.ifinance;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NonAdminUser extends iFINANCEUser {
    private StringProperty address;
    private StringProperty email;


    // Constructors
    public NonAdminUser() {
        setFullName("");
        this.address = new SimpleStringProperty("");
        this.email = new SimpleStringProperty("");
        setuAccount(new UserAccount());
    }

    public NonAdminUser(int id, String fullName, String address, String email, UserAccount account) {
        setID(id);
        setFullName(fullName);
        this.address = new SimpleStringProperty(address);
        this.email = new SimpleStringProperty(email);
        setuAccount(account);
    }

    //set and get methods
    // address property
    public void setAddress(String address) {
        this.address.set(address);
    }
    public StringProperty addressProperty() {
        return this.address;
    }
    public String getAddress() {
        return this.address.get();
    }

    // email property
    public void setEmail(String email) {
        this.email.set(email);
    }
    public StringProperty emailProperty() {
        return this.email;
    }
    public String getEmail() {
        return this.email.get();
    }

}
