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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class UpdateCustomerFormController implements Initializable {

    @FXML
    private Button btnClear;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnUpdate;

    @FXML
    private JFXComboBox<String> comboCity;

    @FXML
    private JFXComboBox<String> comboProvince;

    @FXML
    private JFXComboBox<String> comboTitle;

    @FXML
    private DatePicker datePikerDob;

    @FXML
    private TextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPostalCode;

    @FXML
    private JFXTextField txtSalary;

    @FXML
    private JFXTextField txtStreetAddress;

    @FXML
    void btnClearOnAction(ActionEvent event) {
        if(! btnUpdate.isDisabled()){
            clearForm();
        }
    }

    @FXML
    void btnSearchOnaction(ActionEvent event) {
        if(txtId.getText() == null || txtId.getText().isEmpty()){
            showAlearts("INPUT ERROR...","Please Input Customer ID...");
            return;
        }
        String id = txtId.getText();
        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM Customer WHERE id = '"+id+"';");
            if(resultSet.next()){
                txtId.setDisable(true);
                comboTitle.setDisable(false);
                comboTitle.setValue(resultSet.getString("Title"));
                txtName.setDisable(false);
                txtName.setText(resultSet.getString("Name"));
                datePikerDob.setDisable(false);
                datePikerDob.setValue(resultSet.getDate("DOB").toLocalDate());
                txtSalary.setDisable(false);
                txtSalary.setText(resultSet.getString("Salary"));
                comboProvince.setDisable(false);
                comboProvince.setValue(resultSet.getString("Province"));
                comboCity.setDisable(false);
                comboCity.setValue(resultSet.getString("City"));
                txtStreetAddress.setDisable(false);
                txtStreetAddress.setText(resultSet.getString("Street_Address"));
                txtPostalCode.setDisable(false);
                txtPostalCode.setText(resultSet.getString("Postalcode"));
                btnUpdate.setDisable(false);
            }else{
                showAlearts("NOT FOUND...", "Customer id- "+id+" Not Found");
            }
        } catch (SQLException e) {
            showAlearts(e.getSQLState(), e.getMessage());
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if(checkForm()){
            try {
                String id = txtId.getText();
                String title = comboTitle.getValue();
                String name = txtName.getText();
                LocalDate dob = datePikerDob.getValue();
                double salary = Double.parseDouble(txtSalary.getText());
                String province = comboProvince.getValue();
                String city = comboCity.getValue();
                String streetAddress = txtStreetAddress.getText();
                String postalCode = txtPostalCode.getText();

                PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("UPDATE Customer SET Title = ?, Name = ?, DOB = ?, Salary = ?, Province = ?, City = ?, Street_Address = ?, Postalcode = ? WHERE Id = ?;");
                preparedStatement.setObject(1, title);
                preparedStatement.setObject(2, name);
                preparedStatement.setObject(3, dob);
                preparedStatement.setObject(4, salary);
                preparedStatement.setObject(5, province);
                preparedStatement.setObject(6, city);
                preparedStatement.setObject(7, streetAddress);
                preparedStatement.setObject(8, postalCode);
                preparedStatement.setObject(9, id);

                if(0 < preparedStatement.executeUpdate()){
                    showAlearts("SUCCESS...", "Customer Details Updated Successfully...");
                    clearForm();
                }else {
                    showAlearts("FAILED...", "Customer Updating Process Failed...\nPlease Try Again...");
                }

            }catch (NumberFormatException ex){
                showAlearts("INPUT ERROR...", "Please Input Valid Salary Amount");
                txtSalary.setText(null);
            } catch (SQLException e) {
                showAlearts(e.getSQLState(), e.getMessage());
            }

        }
    }

    @FXML
    void comboProvinceOnAction(ActionEvent event) {
        if (comboCity.isDisabled()) {
            comboCity.setDisable(false);
        }
        if(comboProvince.getValue() == null){
            return;
        }
        if (comboProvince.getValue().equals("Western")) {
            ObservableList<String> cityList = FXCollections.observableArrayList("Colombo", "Gampaha", "Kaluthara");
            comboCity.setItems(cityList);
            return;
        }
        if (comboProvince.getValue().equals("Central")) {
            ObservableList<String> cityList = FXCollections.observableArrayList("Kandy", "Matale", "Nuwara Eliya");
            comboCity.setItems(cityList);
            return;
        }
        if (comboProvince.getValue().equals("Southern")) {
            ObservableList<String> cityList = FXCollections.observableArrayList("Galle", "Mathara", "Hanbantota");
            comboCity.setItems(cityList);
            return;
        }
        if (comboProvince.getValue().equals("North")) {
            ObservableList<String> cityList = FXCollections.observableArrayList("Jaffna", "Kilinochchi", "Mannar", "Mullaitivu", "Vavuniya");
            comboCity.setItems(cityList);
            return;
        }
        if (comboProvince.getValue().equals("Eastern")) {
            ObservableList<String> cityList = FXCollections.observableArrayList("Trinkomalee", "Batticaloa", "Ampara");
            comboCity.setItems(cityList);
            return;
        }
        if (comboProvince.getValue().equals("North Western")) {
            ObservableList<String> cityList = FXCollections.observableArrayList("Kurunegala", "Puttalama");
            comboCity.setItems(cityList);
            return;
        }
        if (comboProvince.getValue().equals("North Central")) {
            ObservableList<String> cityList = FXCollections.observableArrayList("Anuradhapura", "Polonnaruwa");
            comboCity.setItems(cityList);
            return;
        }
        if (comboProvince.getValue().equals("Uva")) {
            ObservableList<String> cityList = FXCollections.observableArrayList("Badulla", "Monaragala");
            comboCity.setItems(cityList);
            return;
        }
        if (comboProvince.getValue().equals("Sabaragamuwa")) {
            ObservableList<String> cityList = FXCollections.observableArrayList("Ratnapura", "Kegalle");
            comboCity.setItems(cityList);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTitle();
        loadProince();
    }

    private void loadTitle(){
        ObservableList<String> titleList = FXCollections.observableArrayList("Mr.", "Ms.", "Mrs.", "Miss.");
        comboTitle.setItems(titleList);
    }

    private void loadProince(){
        ObservableList<String> provinceList = FXCollections.observableArrayList(
                "Western",
                "Central",
                "Southern",
                "North",
                "Eastern",
                "North Western",
                "North Central",
                "Uva",
                "Sabaragamuwa"
        );
        comboProvince.setItems(provinceList);
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
        comboTitle.setDisable(true);
        comboTitle.setValue(null);
        txtName.setDisable(true);
        txtName.setText(null);
        datePikerDob.setDisable(true);
        datePikerDob.setValue(null);
        txtSalary.setDisable(false);
        txtSalary.setText(null);
        comboProvince.setDisable(true);
        comboProvince.setValue(null);
        comboCity.setDisable(true);
        comboCity.setValue(null);
        txtStreetAddress.setDisable(true);
        txtStreetAddress.setText(null);
        txtPostalCode.setDisable(true);
        txtPostalCode.setText(null);
        btnUpdate.setDisable(true);
    }

    private boolean checkForm() {
        if(comboTitle.getValue() == null){
            showAlearts("Input Error...","Please select customer title...");
            return false;
        } else if (txtName.getText() == null || txtName.getText().isEmpty()) {
            showAlearts("Input Error...","Please input customer name...");
            return false;
        } else if (datePikerDob.getValue() == null) {
            showAlearts("Input Error...","Please select date of birth...");
            return false;
        } else if (txtSalary.getText() == null || txtSalary.getText().isEmpty()) {
            showAlearts("Input Error...","Please input customer salary...");
            return false;
        } else if (comboProvince.getValue() == null){
            showAlearts("Input Error...","Please select Province...");
            return false;
        } else if (comboCity.getValue() == null) {
            showAlearts("Input Error...","Please select city...");
            return false;
        } else if (txtStreetAddress.getText() == null || txtStreetAddress.getText().isEmpty()) {
            showAlearts("Input Error...","Please input street address...");
            return false;
        } else if (txtPostalCode.getText() == null || txtPostalCode.getText().isEmpty()) {
            showAlearts("Input Error...","Please input postal code...");
            return false;
        }
        return true;
    }
}
