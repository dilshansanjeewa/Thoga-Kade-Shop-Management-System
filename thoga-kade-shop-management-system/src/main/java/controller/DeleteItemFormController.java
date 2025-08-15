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

public class DeleteItemFormController {

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSearch;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblPackSize;

    @FXML
    private Label lblQtyonHand;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private TextField txtCode;

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clear();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            if(0 < DBConnection.getInstance().getConnection().createStatement().executeUpdate("DELETE FROM Item WHERE Code = '"+txtCode.getText()+"'")){
                    showAlerts("SUCCESS...", "Item Details Deleted Successfully...");
                    clear();
            }
            else{
                showAlerts("FAILED...", "Item Details Deleting Process Faileb...\nPlease Try again...");
            }
        } catch (SQLException e) {
            showAlerts(e.getSQLState(), e.getMessage());
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        if(txtCode.getText() == null || txtCode.getText().isEmpty()){
            showAlerts("INPUT ERROR...", "Please Input Item Code...");
        }
        else {
            try {
                String code = txtCode.getText();
                ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM Item WHERE Code = '"+code+"';");

                if(resultSet.next()){
                    txtCode.setEditable(false);
                    lblDescription.setText(resultSet.getString("Description"));
                    lblPackSize.setText(resultSet.getString("Pack_Size"));
                    lblUnitPrice.setText("Rs. "+resultSet.getString("Unit_Price")+"0");
                    lblQtyonHand.setText(resultSet.getString("QTY_On_Hand"));
                    btnDelete.setDisable(false);
                }else {
                    showAlerts("NOT FOUND...", "Item Code- "+code+" Not Found...\nPlease Try Again...");
                }

            } catch (SQLException e) {
                showAlerts(e.getSQLState(), e.getMessage());
            }
        }
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
        if(!txtCode.isEditable()){
           txtCode.setEditable(true);
           txtCode.setText(null);
           lblDescription.setText(null);
           lblPackSize.setText(null);
           lblUnitPrice.setText(null);
           lblQtyonHand.setText(null);
           btnDelete.setDisable(true);
        }
    }

}
