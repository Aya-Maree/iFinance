package se2203b.assignments.ifinance;

import javafx.beans.property.*;

public class MasterAccount {
    private StringProperty name;
    private DoubleProperty openingAmount;
    private DoubleProperty closingAmount;
    private ObjectProperty<Group> groupAccount;

    // Constructors
    public MasterAccount() {
        this( "", 0.0, 0.0);
    }

    public MasterAccount( String name, double openingAmount, double closingAmount) {
        this.name = new SimpleStringProperty(name);
        this.openingAmount = new SimpleDoubleProperty(openingAmount);
        this.closingAmount = new SimpleDoubleProperty(closingAmount);
        this.groupAccount = new SimpleObjectProperty(new Group());
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

    // openingAmount property
    public void setOpeningAmount(double openingAmount) {
        this.openingAmount.set(openingAmount);
    }
    public DoubleProperty openingAmountProperty() {
        return this.openingAmount;
    }
    public double getOpeningAmount() {
        return this.openingAmount.get();
    }

    // closingAmount property
    public void setClosingAmount(double closingAmount) {
        this.closingAmount.set(closingAmount);
    }
    public DoubleProperty closingAmountProperty() {
        return this.closingAmount;
    }
    public double getClosingAmount() {
        return this.closingAmount.get();
    }

    // groupAccount Property
    public void setGroupAccount(Group groupAccount) {
        this.groupAccount.set(groupAccount);
    }
    public ObjectProperty <Group> groupAccountProperty() {
        return this.groupAccount;
    }
    public Group getGroupAccount() {
        return this.groupAccount.get();
    }
}
