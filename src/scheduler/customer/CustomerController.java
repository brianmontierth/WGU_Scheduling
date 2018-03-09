package scheduler.customer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import scheduler.helper.DataAccess;
import scheduler.main.Main;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    private Customer selectedCustomer = new Customer();

    private ObservableList<Customer> customerList = FXCollections.observableArrayList();

    @FXML
    public ListView<Customer> customerListView = new ListView<>();
    @FXML
    public Button customerSaveButton = new Button();
    @FXML
    public Button customerEditButton = new Button();
    @FXML
    public Button customerClearButton = new Button();
    @FXML
    public Button customerAddNewButton = new Button();

    @FXML
    public Label customerId = new Label();
    @FXML
    public TextField customerName = new TextField();
    @FXML
    public TextField address = new TextField();
    @FXML
    public TextField address2 = new TextField();
    @FXML
    public TextField city = new TextField();
    @FXML
    public TextField postalCode = new TextField();
    @FXML
    public TextField country = new TextField();
    @FXML
    public TextField phone = new TextField();
    @FXML
    public CheckBox active = new CheckBox();



    @Override @FXML
    public void initialize(URL location, ResourceBundle resources) {

        customerSaveButton.setOnAction(event -> saveCustomerForm());
        customerClearButton.setOnAction(event -> clearCustomerForm());
        customerEditButton.setOnAction(event -> disableCustomerForm(false));
        customerAddNewButton.setOnAction(event -> clearCustomerForm());
        customerListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null)
                return;
            this.selectCustomer(newValue);
        });
        clearCustomerForm();
        populateCustomerListView();

    }

    private int getCustomerCounter(){
        try {
            return DataAccess.getMaxId();
        } catch(SQLException e) {
            System.out.println("DataAccess.getMaxId() failed. returning 0.");
            return 0;
        }
    }

    private void selectCustomer(Customer customer) {
        selectedCustomer = customer.clone();
        bindCustomer();
        disableCustomerForm(true);
    }

    private void disableCustomerForm(boolean disable) {
        customerId.setDisable(disable);
        customerName.setDisable(disable);
        address.setDisable(disable);
        address2.setDisable(disable);
        city.setDisable(disable);
        postalCode.setDisable(disable);
        country.setDisable(disable);
        phone.setDisable(disable);
        active.setDisable(disable);
        customerEditButton.setVisible(disable);
        customerSaveButton.setVisible(!disable);
        customerClearButton.setVisible(!disable);
    }

    private void clearCustomerForm() {
        selectedCustomer = new Customer();
        bindCustomer();
        customerId.setText(String.valueOf(getCustomerCounter() + 1));
        disableCustomerForm(false);
        active.setSelected(true);
    }

    private void saveCustomerForm(){
        System.out.println("Save Triggered....");
        if (!validateCustomerForm()){
            return;
        }


        try {
            if (Integer.valueOf(customerId.getText()) <= DataAccess.getMaxId()){ //update existing customer
                DataAccess.updateCustomer(selectedCustomer, Main.getUser());
                populateCustomerListView();
                disableCustomerForm(true);
            }

            else { // else add new Customer
                DataAccess.addCustomer(selectedCustomer, Main.getUser());
                populateCustomerListView();
                clearCustomerForm();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


    public void bindCustomer(){
        customerId.textProperty().bindBidirectional(selectedCustomer.customerIdProperty());
        customerName.textProperty().bindBidirectional(selectedCustomer.customerNameProperty());
        address.textProperty().bindBidirectional(selectedCustomer.addressProperty());
        address2.textProperty().bindBidirectional(selectedCustomer.address2Property());
        city.textProperty().bindBidirectional(selectedCustomer.cityProperty());
        postalCode.textProperty().bindBidirectional(selectedCustomer.postalCodeProperty());
        country.textProperty().bindBidirectional(selectedCustomer.countryProperty());
        phone.textProperty().bindBidirectional(selectedCustomer.phoneProperty());
        active.selectedProperty().bindBidirectional(selectedCustomer.activeProperty());
    }

    private boolean validateCustomerForm() {
        //form validation code
        return true;
    }


    private void populateCustomerListView(){
        customerList.clear();
        DataAccess.getCustomers(customerList);
        customerListView.setItems(customerList);
    }

}
