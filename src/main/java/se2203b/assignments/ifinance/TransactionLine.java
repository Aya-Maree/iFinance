package se2203b.assignments.ifinance;

import javafx.beans.property.*;

public class TransactionLine {
    private StringProperty id;
    private DoubleProperty creditedAmount;
    private DoubleProperty debitedAmount;
    private StringProperty comment;

    private ObjectProperty<MasterAccount> firstMasterAccount;
    private ObjectProperty<MasterAccount> secondMasterAccount;

    // Constructors
    public TransactionLine() {
        this("", 0.0, 0.0, "");
    }

    public TransactionLine(String id, double creditedAmount, double debitedAmount, String comment) {
        this.id = new SimpleStringProperty(id);
        this.comment = new SimpleStringProperty(comment);
        this.creditedAmount = new SimpleDoubleProperty(creditedAmount);
        this.debitedAmount = new SimpleDoubleProperty(debitedAmount);
        this.firstMasterAccount = new SimpleObjectProperty(new MasterAccount());
        this.secondMasterAccount = new SimpleObjectProperty(new MasterAccount());

    }

    //set and get methods
    // id property
    public void setID(String id) {
        this.comment.set(id);
    }
    public StringProperty idProperty() {
        return this.id;
    }
    public String getID() {
        return this.id.get();
    }

    // debitedAmount property
    public void setCreditedAmount(double creditedAmount) {
        this.creditedAmount.set(creditedAmount);
    }
    public DoubleProperty creditedAmountProperty() {
        return this.creditedAmount;
    }
    public double getCreditedAmount() {
        return this.creditedAmount.get();
    }

    // creditedAmount property
    public void setDebitedAmount(double debitedAmount) {
        this.debitedAmount.set(debitedAmount);
    }
    public DoubleProperty debitedAmountProperty() {
        return this.debitedAmount;
    }
    public double getDebitedAmount() {
        return this.debitedAmount.get();
    }

    // comment property
    public void setComment(String comment) {
        this.comment.set(comment);
    }
    public StringProperty commentProperty() {
        return this.comment;
    }
    public String getComment() {
        return this.comment.get();
    }

    // firstMasterAccount Property
    public void setFirstMasterAccount(MasterAccount firstMasterAccount) {
        this.firstMasterAccount.set(firstMasterAccount);
    }
    public ObjectProperty <MasterAccount> firstMasterAccountProperty() {
        return this.firstMasterAccount;
    }
    public MasterAccount getFirstMasterAccount() {
        return this.firstMasterAccount.get();
    }

    // secondMasterAccount Property
    public void setSecondMasterAccount(MasterAccount secondMasterAccount) {
        this.secondMasterAccount.set(secondMasterAccount);
    }
    public ObjectProperty <MasterAccount> secondMasterAccountProperty() {
        return this.secondMasterAccount;
    }
    public MasterAccount getSecondMasterAccount() {
        return this.secondMasterAccount.get();
    }
}
