package com.example.test3abhanu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

        @FXML
        private TableColumn<Pizzainfo, Integer> OrderID;

        @FXML
        private Button addbutn;

        @FXML
        private Button deletebutn;

        @FXML
        private Label errror;

        @FXML
        private TextField idinput;

        @FXML
        private ComboBox<String> myComboBox;

        @FXML
        private TableColumn<Pizzainfo, String> namecol;

        @FXML
        private TextField nameinput;

        @FXML
        private TableColumn<Pizzainfo, String> numbercol;

        @FXML
        private TextField phoneinput;

        @FXML
        private TableColumn<Pizzainfo, String> sizecol;

        @FXML
        private TableView<Pizzainfo> tableforpizza;

        @FXML
        private TableColumn<Pizzainfo, Integer> toppingscol;

        @FXML
        private TextField toppingsinput;

        @FXML
        private TableColumn<Pizzainfo,  Double> totalbilcols;

        @FXML
        private Button updatebutn;

        @FXML
        private Button viewbutn;

        @FXML
        void addpizza(ActionEvent event) {
                String selectedSize = myComboBox.getValue();
                int numberOfToppings = Integer.parseInt(toppingsinput.getText());
                double getbill = calculateTotalbill(numberOfToppings, selectedSize);
                String query = "INSERT INTO PizzaOrders (`Name`, `Phone`, `Size`, `No of Toppings`, `Total Bill`) VALUES (?, ?, ?, ?, ?)";
                executeQuery(query, List.of(nameinput.getText(), phoneinput.getText(), selectedSize, String.valueOf(numberOfToppings), String.valueOf(getbill)));


        }

        @FXML
        void deletepizza(ActionEvent event) {
                String query = "DELETE FROM PizzaOrders WHERE id=?";
                executeQuery(query, List.of(idinput.getText()));
                showOrderList();

        }

        @FXML
        void updatepizza(ActionEvent event) {
                String selectedSize = myComboBox.getValue();
                int numberOfToppings = Integer.parseInt(toppingsinput.getText());
                double getbill = calculateTotalbill(numberOfToppings, selectedSize);
                String query = "UPDATE PizzaOrders SET `Name`=?, `Phone`=?, `Size`=?, `No of Toppings`=?, `Total Bill`=? WHERE `id`=?";

                executeQuery(query, List.of(nameinput.getText(), phoneinput.getText(), selectedSize, String.valueOf(numberOfToppings), String.valueOf(getbill), idinput.getText()));
                showOrderList();
        }

        @FXML
        void viewpizza(ActionEvent event) {

                String query = "SELECT id, Name, Phone, Size, `No of Toppings` FROM PizzaOrders WHERE id=?";
                int ID = Integer.parseInt(idinput.getText());
                try (Connection conn = Database.getConnection();
                     PreparedStatement pst = conn.prepareStatement(query)) {
                        pst.setInt(1, ID);
                        ResultSet rs = pst.executeQuery();
                        if (rs.next()) {
                                idinput.setText(String.valueOf(rs.getInt("id")));
                                nameinput.setText(rs.getString("Name"));
                                phoneinput.setText(rs.getString("Phone"));
                                myComboBox.setValue(rs.getString("Size"));
                                toppingsinput.setText(String.valueOf(rs.getInt("No of Toppings")));
                        } else {

                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }
        }


        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                showOrderList();
        }
        private void showOrderList() {
                ObservableList<Pizzainfo> list = getpizzaorderlist();
                OrderID.setCellValueFactory(new PropertyValueFactory<>("orderId"));
                namecol.setCellValueFactory(new PropertyValueFactory<>("name"));
                numbercol.setCellValueFactory(new PropertyValueFactory<>("phone"));
                sizecol.setCellValueFactory(new PropertyValueFactory<>("size"));
                toppingscol.setCellValueFactory(new PropertyValueFactory<>("numberOfToppings"));
                totalbilcols.setCellValueFactory(new PropertyValueFactory<>("totalBills"));
                tableforpizza.setItems(list);  //this sets the list in table view as like we have mapped the table in above code
        }


        private ObservableList<Pizzainfo> getpizzaorderlist() {
                ObservableList<Pizzainfo> pizzaorderlist = FXCollections.observableArrayList();
                String query = "SELECT * FROM PizzaOrders";
                try (Connection conn = Database.getConnection();
                     Statement st = conn.createStatement();
                     ResultSet rs = st.executeQuery(query)) {
                        while (rs.next()) {
                                Pizzainfo pizzaorder = new Pizzainfo(rs.getInt("id"), rs.getString("Name"), rs.getString("Phone"), rs.getString("Size"),rs.getInt("No of Toppings"),rs.getDouble("Total Bill"));
                                pizzaorderlist.add(pizzaorder);
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                return pizzaorderlist;
                //in the above code  i used a function executeQuery which is defined  below
        }


        private void executeQuery(String query, List<String> parameters) {
                try (Connection conn = Database.getConnection();
                     PreparedStatement ps = conn.prepareStatement(query)) {
                        for (int i = 0; i < parameters.size(); i++) {
                                ps.setString(i + 1, parameters.get(i));
                        }
                        ps.executeUpdate();
                        makeEmpty();
                        showOrderList();
                } catch (SQLException e) {
                        e.printStackTrace();
                }
        }

        public double calculateTotalbill(int nooftoppings, String size){
                double price = 0;
                switch (size){
                        case "XL":
                                price=15;
                                break;
                        case "L":
                                price=12;
                                break;
                        case "M":
                                price=10;
                                break;
                        case "S":
                                price=8;
                                break;

                }

                double totalbill=price+nooftoppings*1.5;
                double totalbillAfterHst=  totalbill*0.15+totalbill;
                return totalbillAfterHst;

        }
        public void makeEmpty(){
                nameinput.setText("");
                phoneinput.setText("");
                idinput.setText("");
                toppingsinput.setText("");
                myComboBox.setValue(null);


        }



}
