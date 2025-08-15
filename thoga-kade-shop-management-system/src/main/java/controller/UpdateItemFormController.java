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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UpdateItemFormController implements Initializable {

    @FXML
    private Button btnClear;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnUpdate;

    @FXML
    private JFXComboBox<String> comboPackSize;

    @FXML
    private TextField txtCode;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtPackSize;

    @FXML
    private JFXTextField txtQtyonHand;

    @FXML
    private JFXTextField txtUnitPrice;

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clear();
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        if(txtCode.getText() == null || txtCode.getText().isEmpty()){
            showAlerts("INPUT ERROR...", "Please Input Item Code...");
            return;
        }
        String code = txtCode.getText();
        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM Item WHERE Code = '" + code + "';");

            if (resultSet.next()){
                txtCode.setDisable(true);
                txtDescription.setText(resultSet.getString("Description"));
                txtDescription.setDisable(false);
                String packSize = resultSet.getString("Pack_Size");
                comboPackSize.setValue(separateUnit(packSize));
                comboPackSize.setDisable(false);
                txtPackSize.setText(separateSize(packSize));
                txtPackSize.setDisable(false);
                txtUnitPrice.setText(resultSet.getString("Unit_Price"));
                txtUnitPrice.setDisable(false);
                txtQtyonHand.setText(resultSet.getString("QTY_On_Hand"));
                txtQtyonHand.setDisable(false);
                btnUpdate.setDisable(false);

            }else {
                showAlerts("NOT FOUND...", "Item Code- "+code+" Not Found...");
            }
        } catch (SQLException e) {
            showAlerts(e.getSQLState(), e.getMessage());
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if(checkInputFields()){
            try {
                PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("UPDATE Item SET Description = ?, Pack_Size = ?, Unit_Price = ?, QTY_On_Hand = ? WHERE Code = ?;");

                int packSize = Integer.parseInt(txtPackSize.getText());
                Double unitPrice = Double.parseDouble(txtUnitPrice.getText());
                int qtyonHand = Integer.parseInt(txtQtyonHand.getText());

                preparedStatement.setObject(1,txtDescription.getText());
                preparedStatement.setObject(2,packSize+comboPackSize.getValue());
                preparedStatement.setObject(3,unitPrice);
                preparedStatement.setObject(4,qtyonHand);
                preparedStatement.setObject(5,txtCode.getText());

                if(0 < preparedStatement.executeUpdate()){
                    showAlerts("SUCCESS...", "Item Details Updated Successfully...");
                    clear();

                }else {
                    showAlerts("FAILED...", "Item Updating Process Failed...\nPlease Try Again...");
                }
            } catch (SQLException e) {
                showAlerts(e.getSQLState(), e.getMessage());
            } catch (NumberFormatException e){
                showAlerts("INVALID INPUT...", "Invalid input...\nPlease Check the values...");
            }
        }
    }

    private boolean checkInputFields() {
        String error = "INPUT ERROR";
        if(txtDescription.getText() == null || txtDescription.getText().isEmpty()){
            showAlerts(error, "Please Input Description");
            return false;
        } else if (comboPackSize.getValue() == null) {
            showAlerts(error, "Please Select Pack Size");
            return false;
        } else if (txtPackSize.getText() == null || txtPackSize.getText().isEmpty()) {
            showAlerts(error, "Please Input Pack Size");
            return false;
        } else if (txtUnitPrice.getText() == null || txtUnitPrice.getText().isEmpty()) {
            showAlerts(error, "Please Input Unit Price");
            return false;
        } else if (txtQtyonHand.getText() == null || txtQtyonHand.getText().isEmpty()) {
            showAlerts(error, "Please Input QTY on Hand");
            return false;
        }
        return true;
    }

    private void loadComboPackSize() {
        ObservableList<String> packSizeList = FXCollections.observableArrayList("g", "kg","mL", "L");
        comboPackSize.setItems(packSizeList);
    }

    private String separateUnit(String word){
        String size = "";
        for (int i = 0; i<word.length(); i++){
            char ch = word.charAt(i);
            if(Character.isLetter(ch)){
                size+=ch;
            }
        }
        return size;
    }

    private String separateSize(String word){
        String unit = "";
        for (int i = 0; i<word.length(); i++){
            char ch = word.charAt(i);
            if(Character.isDigit(ch)){
                unit+=ch;
            }
        }
        return unit;
    }

    private void showAlerts(String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setHeaderText(header);
        alert.setContentText(content);

        Stage stage = (Stage) btnSearch.getScene().getWindow();
        alert.initOwner(stage);
        alert.show();
    }

    private void clear(){
        txtCode.setText(null);
        txtCode.setDisable(false);
        txtDescription.setText(null);
        txtDescription.setDisable(true);
        comboPackSize.setValue(null);
        comboPackSize.setDisable(true);
        txtPackSize.setText(null);
        txtPackSize.setDisable(true);
        txtUnitPrice.setText(null);
        txtUnitPrice.setDisable(true);
        txtQtyonHand.setText(null);
        txtQtyonHand.setDisable(true);
        btnUpdate.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadComboPackSize();
    }
}
