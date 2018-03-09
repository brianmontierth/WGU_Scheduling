package scheduler.helper;

import javafx.collections.ObservableList;
import scheduler.customer.Customer;

import java.sql.*;

public class DataAccess {
    private static Connection connection;

    protected static void connect() throws SQLException {
        String db = "U04UYO";
        String url = "jdbc:mysql://52.206.157.109/" + db;
        String user = "U04UYO";
        String pass = "53688348326";

        try {
            connection = DriverManager.getConnection(url, user, pass);
            System.out.println("Connected to database : " + db);
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }

    protected static void close() throws SQLException {
        if (connection != null) {
            connection.close();
            System.out.println("Connection closed!");
        }
    }

    protected static void addCustomer(Customer customer, String user) throws SQLException{
        connection.setAutoCommit(false);
        System.out.println("Adding Customer: " + customer);
        try {
            // insert into customer table
            String insertCustomer = "INSERT INTO customer (customerId, customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES(?,?,?,?,CURRENT_DATE,?,CURRENT_TIMESTAMP,?);";
            PreparedStatement psCustomer = connection.prepareStatement(insertCustomer);
            psCustomer.setInt(1, customer.getCustomerId());
            psCustomer.setString(2, customer.getCustomerName());
            psCustomer.setInt(3, customer.getCustomerId()); //addressId
            psCustomer.setInt(4, (customer.isActive()) ? 1 : 0);
            psCustomer.setString(5, user);
            psCustomer.setString(6, user);

            //insert into address table
            String insertAddress = "INSERT INTO address (addressId, address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, ?, ?, ?, ?, ?, CURRENT_DATE, ?, CURRENT_TIMESTAMP, ?);";
            PreparedStatement psAddress = connection.prepareStatement(insertAddress);
            psAddress.setInt(1, customer.getCustomerId()); //addressId
            psAddress.setString(2, customer.getAddress());
            psAddress.setString(3, customer.getAddress2());
            psAddress.setInt(4, customer.getCustomerId()); //cityId
            psAddress.setString(5, customer.getPostalCode());
            psAddress.setString(6, customer.getPhone());
            psAddress.setString(7, user);
            psAddress.setString(8, user);

            //insert into city table
            String insertCity = "INSERT INTO city (cityId, city, countryId, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, ?, ?, CURRENT_DATE, ?, CURRENT_TIMESTAMP, ?);";
            PreparedStatement psCity = connection.prepareStatement(insertCity);
            psCity.setInt(1, customer.getCustomerId()); //cityId
            psCity.setString(2, customer.getCity());
            psCity.setInt(3, customer.getCustomerId()); //countryId
            psCity.setString(4, user);
            psCity.setString(5, user);

            //insert into country table
            String insertCountry = "INSERT INTO country (countryId, country, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, ?, CURRENT_DATE, ?, CURRENT_TIMESTAMP, ?);";
            PreparedStatement psCountry = connection.prepareStatement(insertCountry);
            psCountry.setInt(1, customer.getCustomerId()); //countryId
            psCountry.setString(2, customer.getCountry());
            psCountry.setString(3, user);
            psCountry.setString(4, user);

            //execute statements
            psCustomer.executeUpdate();
            psAddress.executeUpdate();
            psCity.executeUpdate();
            psCountry.executeUpdate();

            //commit successful statements
            connection.commit();
            System.out.println("Added successfully...");

        }catch (SQLException e){
            connection.rollback();
            String message = "Customer insert failed: \nSQLException: " + e.getMessage();
            //System.out.println(message);
            throw new SQLException(message);
        } finally {
            connection.setAutoCommit(true);
        }
    }

    protected static void updateCustomer(Customer customer, String user) throws SQLException {
        connection.setAutoCommit(false);
        System.out.println("Updating Customer: " + customer);
        try {
            // update customer table
            String updateCustomer = "UPDATE customer SET customerName = ?, active = ?, lastUpdate = CURRENT_TIMESTAMP, lastUpdateBy = ? WHERE customerId = ?;";
            PreparedStatement psCustomer = connection.prepareStatement(updateCustomer);
            psCustomer.setString(1, customer.getCustomerName());
            psCustomer.setInt(2, (customer.isActive()) ? 1 : 0);
            psCustomer.setString(3, user);
            psCustomer.setInt(4, customer.getCustomerId());

            //update address table
            String updateAddress = "UPDATE address SET address = ?, address2 = ?, postalCode = ?, phone = ?, lastUpdate = CURRENT_TIMESTAMP, lastUpdateBy = ? WHERE addressId = ?;";
            PreparedStatement psAddress = connection.prepareStatement(updateAddress);
            psAddress.setString(1, customer.getAddress());
            psAddress.setString(2, customer.getAddress2());
            psAddress.setString(3, customer.getPostalCode());
            psAddress.setString(4, customer.getPhone());
            psAddress.setString(5, user);
            psAddress.setInt(6, customer.getCustomerId()); //addressId

            //update city table
            String updateCity = "UPDATE city SET city = ?, lastUpdate = CURRENT_TIMESTAMP, lastUpdateBy = ? WHERE cityId = ?;";
            PreparedStatement psCity = connection.prepareStatement(updateCity);
            psCity.setString(1, customer.getCity());
            psCity.setString(2, user);
            psCity.setInt(3, customer.getCustomerId()); //cityId

            //update country table
            String updateCountry = "UPDATE country SET country = ?, lastUpdate = CURRENT_TIMESTAMP, lastUpdateBy = ? WHERE countryId = ?;";
            PreparedStatement psCountry = connection.prepareStatement(updateCountry);
            psCountry.setString(1, customer.getCountry());
            psCountry.setString(2, user);
            psCountry.setInt(3, customer.getCustomerId()); //countryId

            //execute statements
            psCustomer.executeUpdate();
            psAddress.executeUpdate();
            psCity.executeUpdate();
            psCountry.executeUpdate();

            //commit successful statements
            connection.commit();
            System.out.println("Updated successfully...");

        }catch (SQLException e){
            connection.rollback();
            String message = "Customer update failed: \nSQLException: " + e.getMessage();
            //System.out.println(message);
            throw new SQLException(message);
        } finally {
            connection.setAutoCommit(true);
        }
    }


    protected static int getMaxId() throws SQLException {
        try {
            String select = "SELECT MAX(customerId) AS maxCustomerId FROM customer;";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(select);
            while (rs.next()){
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("select max customer id failed:");
            System.out.println("SQLException: " + e.getMessage());
        }
        return 0;
    }

    protected static void getCustomers(ObservableList<Customer> customerList) {
        String select = " SELECT cust.customerId, cust.customerName, cust.active, addr.address, addr.address2, addr.postalCode, addr.phone, city.city, ctry.country FROM customer cust INNER JOIN address addr ON cust.addressId = addr.addressId INNER JOIN city ON addr.cityId = city. cityId INNER JOIN country ctry ON city.countryId = ctry.countryId WHERE cust.active = 1 ORDER BY cust.customerName;";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(select);
            while (rs.next()){
                 customerList.add(
                         new Customer(
                                   rs.getInt(1)
                                 , rs.getString(2)
                                 , rs.getBoolean(3)
                                 , rs.getString(4)
                                 , rs.getString(5)
                                 , rs.getString(6)
                                 , rs.getString(7)
                                 , rs.getString(8)
                                 , rs.getString(9)
                         )
                 );
            }
        } catch (SQLException e) {
            System.out.println("DataAccess.getCustomers() failed:");
            System.out.println("SQLException: " + e.getMessage());
        }
    }


    public static boolean Auth(String user, String pass) throws SQLException {

        try  {
            String select = "SELECT userName FROM user WHERE userName = ? AND password = ?;";
            PreparedStatement psUser = connection.prepareStatement(select);
            psUser.setString(1, user);
            psUser.setString(2, pass);
            ResultSet rs = psUser.executeQuery();
            while (rs.next()) {
                //System.out.println(user);
                //System.out.println(rs.getString(1));
                //System.out.println(user.equals(rs.getString(1)));
                return user.equals(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("DataAccess.Auth() failed:");
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
    }
}
