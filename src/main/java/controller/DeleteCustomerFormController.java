package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteCustomerFormController {

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSearch;

    @FXML
    private Label lblCity;

    @FXML
    private Label lblDob;

    @FXML
    private Label lblName;

    @FXML
    private Label lblPostalCode;

    @FXML
    private Label lblProvince;

    @FXML
    private Label lblSalary;

    @FXML
    private Label lblStreetAddress;

    @FXML
    private TextField txtId;

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearForm();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            if(0 < DBConnection.getInstance().getConnection().createStatement().executeUpdate("DELETE FROM Customer WHERE Id = '" + txtId.getText() + "';")){
                showAlearts("SUCCESS...", "Customer (id="+txtId.getText()+") Deleted Successfully...");
                clearForm();
            }else {
                showAlearts("FAILED...", "Customer Deleting Process Failed...\nPlease Try Again...");
            }
        } catch (SQLException e) {
            showAlearts(e.getSQLState(), e.getMessage());
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        if(txtId.getText() == null || txtId.getText().equals("")){
            showAlearts("INPUT ERROR... ", "Please Input Customer ID...");
            return;
        }
        String id = txtId.getText();
        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM Customer WHERE id = '"+id+"';");

            if(resultSet.next()){
                txtId.setDisable(true);
                lblName.setText(resultSet.getString("Title")+" "+resultSet.getString("Name"));
                lblDob.setText(resultSet.getString("DOB"));
                lblSalary.setText(resultSet.getString("Salary"));
                lblStreetAddress.setText(resultSet.getString("Street_Address"));
                lblCity.setText(resultSet.getString("City"));
                lblProvince.setText(resultSet.getString("Province"));
                lblPostalCode.setText(resultSet.getString("Postalcode"));
                btnDelete.setDisable(false);
            }else {
                showAlearts("NOT FOUND", "No Customer Found id- "+id+"...\nPlease Try Again...");
            }
        } catch (SQLException e) {
            showAlearts(e.getSQLState(), e.getMessage());
        }
    }

    private void showAlearts(String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setHeaderText(header);
        alert.setContentText(content);

        Stage stage = (Stage) btnSearch.getScene().getWindow();
        alert.initOwner(stage);
        alert.showAndWait();
    }

    private void clearForm(){
        txtId.setDisable(false);
        txtId.setText(null);
        lblName.setText(null);
        lblDob.setText(null);
        lblSalary.setText(null);
        lblStreetAddress.setText(null);
        lblCity.setText(null);
        lblProvince.setText(null);
        lblPostalCode.setText(null);
        btnDelete.setDisable(true);
    }

}
