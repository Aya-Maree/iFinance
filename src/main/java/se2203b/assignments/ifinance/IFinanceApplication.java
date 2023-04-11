package se2203b.assignments.ifinance;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
/**
 *
 * @author Abdelkader Ouda
 */
public class IFinanceApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        //load user interface as FXML file
        FXMLLoader loader = new FXMLLoader(IFinanceApplication.class.getResource("IFinance-view.fxml"));
        Parent root = loader.load();

        //create JavaFX scene
        Scene scene = new Scene(root);
        stage.setTitle("iFINANCE");
        // add main window icon
        stage.getIcons().add(new Image("file:src/main/resources/se2203b/assignments/ifinance/WesternLogo.png"));
        // add the generated scene to the main stage (main window)
        stage.setScene(scene);
        //open JavaFX window
        stage.show();
    }


}
