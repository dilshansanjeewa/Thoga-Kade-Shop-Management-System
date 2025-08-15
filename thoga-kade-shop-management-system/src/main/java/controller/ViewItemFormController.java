package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.dto.Item;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ViewItemFormController implements Initializable {

    @FXML
    private Button btnReload;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colPackSize;

    @FXML
    private TableColumn<?, ?> colQtyonHand;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableView<Item> tblDetails;

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        loadTable();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTable();
    }

    private void loadTable (){
        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM Item;");
            ObservableList<Item> itemList = FXCollections.observableArrayList();

            while (resultSet.next()){
                itemList.add(new Item(
                        resultSet.getNString("Code"),
                        resultSet.getString("Description"),
                        resultSet.getString("Pack_Size"),
                        resultSet.getDouble("Unit_Price"),
                        resultSet.getInt("QTY_On_Hand")
                ));
            }

            colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
            colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            colPackSize.setCellValueFactory(new PropertyValueFactory<>("packetSixe"));
            colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
            colQtyonHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

            tblDetails.setItems(itemList);
        } catch (SQLException e) {
            showAlerts(e.getSQLState(), e.getMessage());
        }
    }

    private void showAlerts(String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setHeaderText(header);
        alert.setContentText(content);

        Stage stage = (Stage) btnReload.getScene().getWindow();
        alert.initOwner(stage);
        alert.show();
    }
}
