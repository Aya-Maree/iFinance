package se2203b.assignments.ifinance;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateUserAccountController implements Initializable {
    @FXML
    private TextField address;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField email;

    @FXML
    private Label errorMsg;

    @FXML
    private TextField fullName;

    @FXML
    private PasswordField password1;

    @FXML
    private PasswordField password2;

    @FXML
    private Button saveBtn;

    @FXML
    private TextField userName;

    private String loggedInUser;
    IFinanceController iFinanceController;

    private UserAccountAdapter userAccountAdapter;
    private NonAdminUserAdapter nonAdminUserAdapter;

    public void setAdapters(UserAccountAdapter account, NonAdminUserAdapter profile) {
        userAccountAdapter = account;
        nonAdminUserAdapter = profile;
    }

    public void setIFinanceController(IFinanceController controller) {
        iFinanceController = controller;


    }

    public void createAccount() {
        if (validateForm()) {
            // Get current stage reference
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            // Close stage
            stage.close();
        }

    }

    private boolean validateForm() {
        errorMsg.setText("");
        if (!fullName.getText().isEmpty()) {
            if (!userName.getText().isEmpty()) {
                try {
                    if (!(userAccountAdapter.findRecord(userName.getText()).getUName()).isEmpty()) {
                        // username is already exist for other account
                        errorMsg.setText("This username is not available");
                    } else {
                        if (!password1.getText().isEmpty() && (password1.getText().equals(password2.getText()))) {
                            // save the new account and the user profile and then exit
                            UserAccount account = new UserAccount(userName.getText(), password1.getText(), 0, "", "not-admin");
                            try {
                                userAccountAdapter.insertRecord(account);
                            } catch (SQLException ex) {
                                iFinanceController.displayAlert("Insert User Account: " + ex.getMessage());
                            }
                            NonAdminUser userProfile = new NonAdminUser(0,fullName.getText(), address.getText(), email.getText(), account);

                            try {
                                nonAdminUserAdapter.insertRecord(userProfile);
                                return true;
                            } catch (SQLException ex) {
                                iFinanceController.displayAlert("Insert User Profile: " + ex.getMessage());
                            }
                        } else {
                            // wrong new password
                            errorMsg.setText("The new passwords do not match");
                        }
                    }
                } catch (SQLException ex) {
                    iFinanceController.displayAlert("Find User Account: " + ex.getMessage());
                }
            } else {
                // empty username
                errorMsg.setText("Username can not be empty");

            }

        } else {
            // empty user full name
            errorMsg.setText("User Full name can not be empty");
        }

        return false;
    }

    public void cancel() {
        // Get current stage reference
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        // Close stage
        stage.close();
    }

    public void clearErrorMsg() {
        errorMsg.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
