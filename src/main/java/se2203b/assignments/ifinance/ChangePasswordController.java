package se2203b.assignments.ifinance;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ChangePasswordController implements Initializable {

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField newPassword1;

    @FXML
    private TextField newPassword2;

    @FXML
    private TextField oldPassword;

    @FXML
    private Button saveBtn;

    @FXML
    private Label username;

    @FXML
    private Label errorMsg;

    private String loggedInUser;
    IFinanceController iFinanceController;

    private UserAccountAdapter userAccountAdapter;
    public void setUserAccountModel(UserAccountAdapter accountAdapter) {
        userAccountAdapter = accountAdapter;
    }

    public void setIFinanceController(IFinanceController controller) {
        iFinanceController = controller;
        loggedInUser = controller.getUserName();
        username.setText("Change password for " + loggedInUser+ " user");
    }
    public void changePassword() {
        errorMsg.setText("");
        try {
            // Get the user account information from database
            UserAccount account = userAccountAdapter.findRecord(loggedInUser);

                // check the old password
                if (account.getEncryptedPassword().equals(oldPassword.getText())) {
                    // check if the two new password are identical
                    if (newPassword1.getText().equals(newPassword2.getText())) {
                        // save the new password then exit and logout
                        account.setEncryptedPassword(newPassword1.getText());

                        try {
                            userAccountAdapter.updateRecord(account);

                            // Get current stage reference
                            Stage stage = (Stage) cancelBtn.getScene().getWindow();
                            // Close stage
                            stage.close();
                            iFinanceController.logout();
                        } catch (SQLException e) {
                            iFinanceController.displayAlert("Update User Account: " + e.getMessage());
                        }
                    } else {
                        // wrong new password
                        errorMsg.setText("The new passwords do not match");
                    }
                } else {
                    // wrong password
                    errorMsg.setText("Wrong old password");
                }
        } catch (SQLException ex) {
            iFinanceController.displayAlert("Find User Account: " + ex.getMessage());
        }
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
