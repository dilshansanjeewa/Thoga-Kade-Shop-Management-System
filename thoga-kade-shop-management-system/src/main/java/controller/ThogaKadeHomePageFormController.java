package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ThogaKadeHomePageFormController {

    private final Stage customerStage = new Stage();
    private final Stage itemStage = new Stage();

    @FXML
    private Button btnCustomerManagement;

    @FXML
    private Button btnItemManagement;

    @FXML
    void btnCustomerManagementOnaction(ActionEvent event) {
        try {
            customerStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Customer_Management_Form.fxml"))));
            customerStage.setTitle("Customer Management Form");
            customerStage.show();
        } catch (IOException e) {
            showAlert(e.getClass().getName(), e.getMessage());
        }
    }

    @FXML
    void btnItemManagementOnAction(ActionEvent event) {
        try {
            itemStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Item_Management_Form.fxml"))));
            itemStage.setTitle("Item Management Form");
            itemStage.show();
        } catch (IOException e) {
            showAlert(e.getClass().getName(), e.getMessage());
        }
    }

    private void showAlert (String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setHeaderText(title);
        alert.setContentText(content);

        Stage stage = (Stage) btnCustomerManagement.getScene().getWindow();
        alert.initOwner(stage);
        alert.show();
    }

}
