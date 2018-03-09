package scheduler.customer;

import javafx.beans.property.*;


public class Customer {
    private final StringProperty customerId = new SimpleStringProperty(this, "customerId");
    private final StringProperty customerName = new SimpleStringProperty(this, "customerName");
    private final BooleanProperty active = new SimpleBooleanProperty(this, "active");
    private final StringProperty address = new SimpleStringProperty(this, "address");
    private final StringProperty address2 = new SimpleStringProperty(this, "address2");
    private final StringProperty country = new SimpleStringProperty(this, "country");
    private final StringProperty city = new SimpleStringProperty(this, "city");
    private final StringProperty postalCode = new SimpleStringProperty(this, "postalCode");
    private final StringProperty phone = new SimpleStringProperty(this, "phone");

    public Customer(int customerId, String customerName, boolean active, String address, String address2, String postalCode, String phone, String city, String country) {
        setCustomerId(customerId);
        setCustomerName(customerName);
        setActive(active);
        setAddress(address);
        setAddress2(address2);
        setPostalCode(postalCode);
        setPhone(phone);
        setCity(city);
        setCountry(country);
    }

    public Customer() {

    }

    public boolean isActive() {
        return activeProperty().get();
    }

    public BooleanProperty activeProperty() {
        return active;
    }

    public void setActive(boolean active) {
        activeProperty().set(active);
    }

    public String getAddress() {
        return addressProperty().get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        addressProperty().set(address);
    }

    public String getAddress2() {
        return address2Property().get();
    }

    public StringProperty address2Property() {
        return address2;
    }

    public void setAddress2(String address2) {
        address2Property().set(address2);
    }


    public StringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        cityProperty().set(city);
    }

    public String getPostalCode() {
        return postalCodeProperty().get();
    }

    public StringProperty postalCodeProperty() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        postalCodeProperty().set(postalCode);
    }

    public String getPhone() {
        return phoneProperty().get();
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        phoneProperty().set(phone);
    }

    public String getCustomerName() {
        return customerNameProperty().get();
    }

    public StringProperty customerNameProperty() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        customerNameProperty().set(customerName);
    }

    public int getCustomerId() {
        return Integer.valueOf(customerIdProperty().get());
    }

    public StringProperty customerIdProperty() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        customerIdProperty().set(String.valueOf(customerId));
    }

    public String getCountry() {
        return countryProperty().get();
    }

    public StringProperty countryProperty() {
        return country;
    }

    public void setCountry(String country) {
        countryProperty().set(country);
    }

    public String getCity() {
        return cityProperty().get();
    }

    public Customer clone(){
        return new Customer(getCustomerId(), getCustomerName(), isActive(), getAddress(), getAddress2(), getPostalCode(), getPhone(), getCity(), getCountry());
    }

    @Override
    public String toString() {
        return getCustomerName();
    }
}
