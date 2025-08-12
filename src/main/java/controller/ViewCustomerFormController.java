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
import model.dto.Customer;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ViewCustomerFormController implements Initializable {

    @FXML
    private Button btnReload;

    @FXML
    private TableColumn<?, ?> colTitle;

    @FXML
    private TableColumn<?, ?> colCity;

    @FXML
    private TableColumn<?, ?> colDob;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPostalCode;

    @FXML
    private TableColumn<?, ?> colProvince;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableColumn<?, ?> colStreetAddress;

    @FXML
    private TableView<Customer> tblDetails;

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        loadTable();
    }

    private void loadTable(){
        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM Customer;");
            ObservableList<Customer> customerList = FXCollections.observableArrayList();

            while (resultSet.next()){
                customerList.add(new Customer(
                        resultSet.getString("Id"),
                        resultSet.getString("Title"),
                        resultSet.getString("Name"),
                        resultSet.getDate("DOB").toLocalDate(),
                        resultSet.getDouble("Salary"),
                        resultSet.getString("Province"),
                        resultSet.getString("City"),
                        resultSet.getString("Street_Address"),
                        resultSet.getString("Postalcode")
                ));
            }

            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
            colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
            colStreetAddress.setCellValueFactory(new PropertyValueFactory<>("streetAddress"));
            colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
            colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
            colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

            tblDetails.setItems(customerList);

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText(e.getSQLState());
            alert.setContentText(e.getMessage());

            Stage stage = (Stage) btnReload.getScene().getWindow();
            alert.initOwner(stage);
            alert.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTable();
    }
}
