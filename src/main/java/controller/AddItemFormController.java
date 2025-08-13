package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class AddItemFormController {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnClear;

    @FXML
    private JFXComboBox<?> comboPackSize;

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

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

}
