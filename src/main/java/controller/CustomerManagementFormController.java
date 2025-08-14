package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerManagementFormController {

    private Stage addCustomerStage = new Stage();
    private Stage updataCustomerStage = new Stage();
    private Stage deleteCustomerStage = new Stage();
    private Stage viewCustomerStage = new Stage();

    @FXML
    private Button btnAddCustomer;

    @FXML
    private Button btnDeleteCustomer;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnUpdateCustomer;

    @FXML
    private Button btnViewCustomer;

    @FXML
    void btnAddCustomerOnAction(ActionEvent event) {
        try {
            addCustomerStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Add_Customer_Form.fxml"))));
            addCustomerStage.show();
        } catch (IOException e) {
            showAlerts(e.getClass().getName(), e.getMessage());
        }
    }

    @FXML
    void btnDeleteCustomerOnAction(ActionEvent event) {
        try {
            deleteCustomerStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Delete_Customer_Form.fxml"))));
            deleteCustomerStage.show();
        } catch (IOException e) {
            showAlerts(e.getClass().getName(), e.getMessage());
        }
    }

    @FXML
    void btnExitOnAction(ActionEvent event) {
        Stage stage = (Stage) btnAddCustomer.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnUpdateCustomerOnAction(ActionEvent event) {
        try {
            updataCustomerStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Update_Customer_Form.fxml"))));
            updataCustomerStage.show();
        } catch (IOException e) {
            showAlerts(e.getClass().getName(), e.getMessage());
        }
    }

    @FXML
    void btnViewCustomerOnAction(ActionEvent event) {
        try {
            viewCustomerStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/VIew_Customer_Form.fxml"))));
            viewCustomerStage.show();
        } catch (IOException e) {
            showAlerts(e.getClass().getName(), e.getMessage());
        }
    }

    private void showAlerts(String title, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setHeaderText(title);
        alert.setContentText(content);

        Stage stage = (Stage) btnAddCustomer.getScene().getWindow();
        alert.initOwner(stage);
        alert.show();
    }
}
