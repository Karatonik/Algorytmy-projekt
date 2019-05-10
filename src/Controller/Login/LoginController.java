package Controller.Login;

import Controller.Admin.AdminController;
import Controller.Firm.FirmController;
import Model.Option;
import Controller.Worker.WorkersController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public static String namef = " ";
    Model.Login.LoginModel loginModel = new Model.Login.LoginModel();
    @FXML
    private Label dbstatus;
    @FXML
    private Button loginbutton;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private ComboBox<Option> combobox;

    public void initialize(URL url, ResourceBundle rb) {
        if (this.loginModel.isDatabaseConnected()) {
            this.dbstatus.setText("Connected");
        } else {
            this.dbstatus.setText("Not Connected");
        }
        this.combobox.setItems(FXCollections.observableArrayList(Option.values()));
    }

    @FXML
    public void Login(ActionEvent event) {
        try {
            if (this.loginModel.islogin(this.username.getText(), this.password.getText(), this.combobox.getValue().toString())) {
                Stage stage = (Stage) this.loginbutton.getScene().getWindow();
                stage.close();
                switch (this.combobox.getValue().toString()) {
                    case "Admin":
                        adminLogin();
                        break;
                    case "Firma": {
                        namef = username.getText();
                        firmaLogin();

                        break;
                    }
                    case "Pracownik": {
                        namef = username.getText();
                        workerLogin();
                    }
                    break;
                }

            } else {
                this.dbstatus.setText("Wrong Creditials");
            }
        } catch (Exception localException) {
            localException.getStackTrace();
        }
    }


    public  void adminLogin() {
        try {
            Stage adminStage = new Stage();
            FXMLLoader adminLoader = new FXMLLoader();
            Pane root = adminLoader.load(getClass().getResource("/View/Admin.fxml").openStream());
            AdminController adminController = adminLoader.getController();

            Scene adminscene = new Scene(root);

            adminStage.setScene(adminscene);
            adminStage.setTitle("Admin Dashboard");
            adminStage.setResizable(false);
            adminStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void firmaLogin() {
        try {
            Stage firmaStage = new Stage();
            FXMLLoader firmaLoader = new FXMLLoader();
            Pane root = firmaLoader.load(getClass().getResource("/View/Firm.fxml").openStream());
            FirmController firmaController = firmaLoader.getController();

            Scene firmascene = new Scene(root);

            firmaStage.setScene(firmascene);
            firmaStage.setTitle("Firma Dashboard");
            firmaStage.setResizable(false);
            firmaStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void workerLogin() {
        try {
            Stage workerStage = new Stage();
            FXMLLoader workerLoader = new FXMLLoader();
            Pane root = workerLoader.load(getClass().getResource("/View/Worker.fxml").openStream());
            WorkersController workerController = workerLoader.getController();

            Scene workerscene = new Scene(root);

            workerStage.setScene(workerscene);
            workerStage.setTitle("Pracownik");
            workerStage.setResizable(false);
            workerStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
