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
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddCustomerFormController implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnClear;

    @FXML
    private JFXComboBox<String> comboCity;

    @FXML
    private JFXComboBox<String> comboProvince;

    @FXML
    private JFXComboBox<String> comboTitle;

    @FXML
    private DatePicker datePikerDob;

    @FXML
    private Label lblId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPostalCode;

    @FXML
    private JFXTextField txtSalary;

    @FXML
    private JFXTextField txtStreetAddress;

    @FXML
    void btnAddOnAction(ActionEvent event) {
        if(checkForm()){
            String id = lblId.getText();
            String title = comboTitle.getValue();
            String name = txtName.getText();
            LocalDate dob = datePikerDob.getValue();
            double salary = Double.parseDouble(txtSalary.getText());
            String province = comboProvince.getValue();
            String city = comboCity.getValue();
            String streetAddress = txtStreetAddress.getText();
            String postalCode = txtPostalCode.getText();

            try {
                PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO Customer VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
                preparedStatement.setObject(1,id);
                preparedStatement.setObject(2,title);
                preparedStatement.setObject(3,name);
                preparedStatement.setObject(4,dob);
                preparedStatement.setObject(5,salary);
                preparedStatement.setObject(6,province);
                preparedStatement.setObject(7,city);
                preparedStatement.setObject(8,streetAddress);
                preparedStatement.setObject(9,postalCode);

                if(0 < preparedStatement.executeUpdate()){
                    showAlearts("SUCCESS...","Customer Added to System Successfully...");

                    clrarForm();
                    loadId();
                }else{
                    showAlearts("FAILED,,,","Customer Not Added to System...");
                }
            } catch (SQLException e) {
                showAlearts(e.getSQLState(),e.getMessage());
            }
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clrarForm();
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
        loadId();
        loadTitle();
        loadProvince();
    }

    private void loadId(){
        lblId.setText(generateId());
    }

    private String generateId(){
        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT id FROM customer ORDER BY id DESC LIMIT 1;");
            if(resultSet.next()) {
                return String.format("C%03d", Integer.parseInt(resultSet.getString("id").substring(1)) + 1);
            }

        } catch (SQLException e) {
            showAlearts(e.getSQLState(), e.getMessage());
        }
        return "C001";
    }

    private void loadTitle(){
        ObservableList<String> titleList = FXCollections.observableArrayList("Mr.", "Ms.", "Mrs.", "Miss.");
        comboTitle.setItems(titleList);
    }

    private void loadProvince(){
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

    private void showAlearts(String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setHeaderText(header);
        alert.setContentText(content);

        Stage stage = (Stage) btnAdd.getScene().getWindow();
        alert.initOwner(stage);
        alert.showAndWait();
    }

    private void clrarForm(){
        comboTitle.setValue(null);
        txtName.setText(null);
        datePikerDob.setValue(null);
        txtSalary.setText(null);
        comboProvince.setValue(null);
        comboCity.setValue(null);
        txtStreetAddress.setText(null);
        txtPostalCode.setText(null);
        comboCity.setDisable(true);
    }
}
