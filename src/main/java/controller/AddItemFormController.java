package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddItemFormController implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnClear;

    @FXML
    private JFXComboBox<String> comboPackSize;

    @FXML
    private Label lblCode;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtPackSize;

    @FXML
    private JFXTextField txtQtyonHand;

    @FXML
    private JFXTextField txtUnitPrice;

    @FXML
    void btnAddOnAction(ActionEvent event) {
        if(checkInputFields()){
            try {
                String code = lblCode.getText();
                String description = txtDescription.getText();
                String packSize = Integer.parseInt(txtPackSize.getText()) + comboPackSize.getValue();
                Double unitPrice = Double.parseDouble(txtUnitPrice.getText());
                int qtyonHand = Integer.parseInt(txtQtyonHand.getText());

                PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO Item VALUES (?, ?, ?, ?, ?);");
                preparedStatement.setObject(1, code);
                preparedStatement.setObject(2, description);
                preparedStatement.setObject(3, packSize);
                preparedStatement.setObject(4, unitPrice);
                preparedStatement.setObject(5, qtyonHand);

                if(0 < preparedStatement.executeUpdate()){
                    showAlerts("SUCCESS...", "Item Added to System Successfully...");
                    clear();
                    loadCode();
                }else{
                    showAlerts("FAILED...", "Item Adding Process Failed...\nPlease Try Again...");
                }

            }catch (NumberFormatException e){
                showAlerts("INVALID INPUT", "Please Check the Values...");
            } catch (SQLException e) {
                showAlerts(e.getSQLState(), e.getMessage());
            }
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clear();
    }

    @FXML
    void comboPackSizeOnAction(ActionEvent event) {
        if(txtPackSize.isDisabled()){
            txtPackSize.setDisable(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCode();
        loadComboPackSize();
    }

    private boolean checkInputFields() {
        if(txtDescription.getText() == null || txtDescription.getText().equals("")){
            showAlerts("INPUT ERROR", "Please Input Description");
            return false;
        } else if (comboPackSize.getValue() == null) {
            showAlerts("INPUT ERROR", "Please Select Pack Size");
            return false;
        } else if (txtPackSize.getText() == null || txtPackSize.getText().equals("")) {
            showAlerts("INPUT ERROR", "Please Input Pack Size");
            return false;
        } else if (txtUnitPrice.getText() == null || txtUnitPrice.getText().equals("")) {
            showAlerts("INPUT ERROR", "Please Input Unit Price");
            return false;
        } else if (txtQtyonHand.getText() == null || txtQtyonHand.getText().equals("")) {
            showAlerts("INPUT ERROR", "Please Input QTY on Hand");
            return false;
        }
        return true;
    }

    private void loadComboPackSize() {
        ObservableList<String> packSizeList = FXCollections.observableArrayList("g", "kg","mL", "L");
        comboPackSize.setItems(packSizeList);
    }

    private void loadCode() {
        lblCode.setText(generateCode());
    }

    private String generateCode() {
        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT Code FROM Item ORDER BY Code DESC LIMIT 1;");
            if(resultSet.next()){
                return String.format("P%03d", Integer.parseInt(resultSet.getString("Code").substring(1))+1);
            }

        } catch (SQLException e) {
            showAlerts(e.getSQLState(), e.getMessage());
        }
        return "P001";
    }

    private void clear(){
        txtDescription.setText(null);
        comboPackSize.setValue(null);
        txtPackSize.setText(null);
        txtPackSize.setDisable(true);
        txtUnitPrice.setText(null);
        txtQtyonHand.setText(null);
    }

    private void showAlerts(String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setHeaderText(header);
        alert.setContentText(content);

        Stage stage = (Stage) lblCode.getScene().getWindow();
        alert.initOwner(stage);
        alert.show();
    }
}
