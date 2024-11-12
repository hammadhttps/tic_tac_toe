module com.example.tic_tac_toe {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tic_tac_toe to javafx.fxml;
    exports com.example.tic_tac_toe;
}