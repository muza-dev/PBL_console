package controllers;

import dao.OrderDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Order;

public class OrderListController {

    @FXML private TableView<Order> orderTable;
    @FXML private TableColumn<Order, Integer> idCol;
    @FXML private TableColumn<Order, String> emailCol;
    @FXML private TableColumn<Order, String> dateCol;
    @FXML private TableColumn<Order, String> itemsCol;
    @FXML private TableColumn<Order, Double> totalCol;
    @FXML private TableColumn<Order, String> statusCol;

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("customerEmail"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        itemsCol.setCellValueFactory(new PropertyValueFactory<>("items"));
        totalCol.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        ObservableList<Order> data = FXCollections.observableArrayList(OrderDAO.getAllOrders());
        orderTable.setItems(data);
    }
}
