package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ItemManagmentFormController {

    private final Stage addItemStage = new Stage();
    private final Stage updateItemStage = new Stage();
    private final Stage viewItemStage = new Stage();
    private final Stage deleteItemStage = new Stage();

    @FXML
    private Button btnAddItem;

    @FXML
    private Button btnDeleteItem;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnUpdateItem;

    @FXML
    private Button btnViewItem;

    @FXML
    void btnAddItemOnAction(ActionEvent event) {
        try {
            addItemStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Add_Item_Form.fxml"))));
            addItemStage.setTitle("Add Item Form");
            addItemStage.show();
        } catch (IOException e) {
           showAlerts(e.getClass().getName(), e.getMessage());
        }
    }

    @FXML
    void btnDeleteItemOnAction(ActionEvent event) {
        try {
            deleteItemStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Delete_Item_Form.fxml"))));
            deleteItemStage.setTitle("Delete Item Form");
            deleteItemStage.show();
        } catch (IOException e) {
            showAlerts(e.getClass().getName(), e.getMessage());
        }
    }

    @FXML
    void btnExitOnAction(ActionEvent event) {
        Stage stage = (Stage) btnAddItem.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnUpdateItemOnAction(ActionEvent event) {
        try {
            updateItemStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Update_Item_Form.fxml"))));
            updateItemStage.setTitle("Update Item Form");
            updateItemStage.show();
        } catch (IOException e) {
            showAlerts(e.getClass().getName(), e.getMessage());
        }
    }

    @FXML
    void btnViewItemOnAction(ActionEvent event) {
        try {
            viewItemStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/View_Item_Form.fxml"))));
            viewItemStage.setTitle("Vitw Item Form");
            viewItemStage.show();
        } catch (IOException e) {
            showAlerts(e.getClass().getName(), e.getMessage());
        }
    }

    private void showAlerts(String title, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setHeaderText(title);
        alert.setContentText(content);

        Stage stage = (Stage) btnAddItem.getScene().getWindow();
        alert.initOwner(stage);
        alert.show();
    }

}
