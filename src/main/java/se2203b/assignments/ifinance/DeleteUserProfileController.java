package se2203b.assignments.ifinance;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DeleteUserProfileController implements Initializable {
    @FXML
    private TextField id;
    @FXML
    private TextField address;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField email;

    @FXML
    private TextField fullName;

    @FXML
    private Button deleteBtn;

    @FXML
    private ComboBox userName;

    IFinanceController iFinanceController;

    private UserAccountAdapter userAccountAdapter;
    private NonAdminUserAdapter nonAdminUserAdapter;
    private final ObservableList<String> usernamesList = FXCollections.observableArrayList();
    private final ObservableList<UserAccount> userAccountList = FXCollections.observableArrayList();

    public void setAdapters(UserAccountAdapter account, NonAdminUserAdapter profile) {
        userAccountAdapter = account;
        nonAdminUserAdapter = profile;
        buildData();
    }

    public void setIFinanceController(IFinanceController controller) {
        iFinanceController = controller;
    }

    public void buildData() {
        try {
            usernamesList.addAll(userAccountAdapter.getUsernamesList());
        } catch (SQLException ex) {
            iFinanceController.displayAlert("User Accounts List: " + ex.getMessage());
        }
    }

    public void deleteAccount() {
        try {
            UserAccount account = userAccountAdapter.findRecord(userName.getValue().toString());
            NonAdminUser userProfile = new NonAdminUser(Integer.valueOf(id.getText()),fullName.getText(), address.getText(), email.getText(), account);

            nonAdminUserAdapter.deleteRecord(userProfile);

            // Get current stage reference
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            // Close stage
            stage.close();
        } catch (SQLException ex) {
            iFinanceController.displayAlert("Find User Account: " + ex.getMessage());
        }


    }

    // retrieve the selected profile and update the screen
    public void getAccount() {
        try {
            NonAdminUser profile = nonAdminUserAdapter.findRecord(userName.getValue().toString());

            deleteBtn.setDisable(false);
            id.setText(String.valueOf(profile.getID()));
            fullName.setText(profile.getFullName());
            address.setText(profile.getAddress());
            email.setText(profile.getEmail());
        } catch (SQLException ex) {
            iFinanceController.displayAlert("Find Profile: " + ex.getMessage());
        }
    }

    public void cancel() {
        // Get current stage reference
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        // Close stage
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userName.setItems(usernamesList);

    }
}
