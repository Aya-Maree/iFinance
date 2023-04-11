/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se2203b.assignments.ifinance;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Abdelkader Ouda
 */
public class AboutController {
    @FXML
    Button okBtn;

    public void exit() {
        // Get current stage reference
        Stage stage = (Stage) okBtn.getScene().getWindow();
        // Close stage
        stage.close();
    }
}
