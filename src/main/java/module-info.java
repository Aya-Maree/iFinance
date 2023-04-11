module se2203b.assignments.ifinance {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens se2203b.assignments.ifinance to javafx.fxml;
    exports se2203b.assignments.ifinance;
}