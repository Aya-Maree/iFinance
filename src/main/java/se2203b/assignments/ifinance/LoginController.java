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

/**
 * Controls the login screen
 */
public class LoginController implements Initializable {
    @FXML
    private TextField user;
    @FXML
    private PasswordField password;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private Label errorMsg;

    IFinanceController iFinanceController;

    private UserAccountAdapter userAccountAdapter;

    public void setUserAccountModel(UserAccountAdapter accountAdapter) {
        userAccountAdapter = accountAdapter;
    }

    /**
     * Check authorization credentials.
     */
    public void authorize() {
        errorMsg.setText("");
        try {
            // Get the user account information from database
            UserAccount account = userAccountAdapter.findRecord(user.getText());
            if (account.getUName().isEmpty()) {
                // Account not found
                errorMsg.setText("Incorrect username");
            } else {
                // Account exist, now check the password
                if (account.getEncryptedPassword().equals(password.getText())) {
                    // enable controls based on the account type
                    authenticated(account.getUName(), account.getAccountType());
                } else {
                    // wrong password
                    errorMsg.setText("Wrong password");
                }
            }
        } catch (SQLException ex) {
            iFinanceController.displayAlert("ERROR: " + ex.getMessage());
        }


    }

    /**
     * Will show the main application screen.
     */
    public void authenticated(String userName, String privilege) {
        iFinanceController.setUserName(userName);
        if (privilege.equals("admin")) {
            // show admin menu options
            iFinanceController.enableAdminControls();
        } else {
            // show non admin menu options
            iFinanceController.enableNonAdminControls();
        }

        // Get current stage reference
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        // Close stage
        stage.close();
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

    public void setIFinanceController(IFinanceController controller) {
        iFinanceController = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorMsg.setText("");
    }
}