package scheduler.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import scheduler.helper.DataAccess;
import scheduler.helper.SceneManager;


import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class LoginController implements Initializable {

    @FXML
    public Label companyLabel = new Label();
    @FXML
    public Label usernameLabel = new Label();
    @FXML
    public TextField username = new TextField();
    @FXML
    public Label passwordLabel = new Label();
    @FXML
    public PasswordField password = new PasswordField();
    @FXML
    public Button loginButton = new Button();

    private String errorMismatch;
    private String errorIncomplete;



    @Override @FXML
    public void initialize(URL location, ResourceBundle resources) {
        init_Locale(resources);
        loginButton.setOnAction(this::authenticate);
    }

    @FXML
    private void authenticate(ActionEvent event) {
        if (username.getText().isEmpty() || password.getText().isEmpty())
        {
            new Alert(Alert.AlertType.ERROR, errorIncomplete).show();
            return;
        }
        // todo do some logging here
        System.out.println("Attempting to authenticate...");
        if (DataAccess.Auth(username.getText(), password.getText())) {
            System.out.println("Access granted...");
            SceneManager.activate("main");
        }
        else {
            System.out.println("Access denied..." + errorMismatch);
            new Alert(Alert.AlertType.ERROR, errorMismatch).show();
        }
    }

    @FXML
    private void init_Locale(ResourceBundle rb) {
        companyLabel.setText(rb.getString("companyName"));
        usernameLabel.setText(rb.getString("usernameText"));
        passwordLabel.setText(rb.getString("passwordText"));
        loginButton.setText(rb.getString("loginButtonText"));
        errorMismatch = rb.getString("mismatch");
        errorIncomplete = rb.getString("incomplete");
    }
}
