package Loginapp;
import Admin.AdminControler;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Firma.FirmaControler;
import Pracownik.WorkersControler;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class LoginControler implements Initializable {
    LoginModel loginModel = new LoginModel();
    @FXML
    private Label dbstatus;
    @FXML
    private Button loginbutton;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private ComboBox<option> combobox;

    public void initialize(URL url, ResourceBundle rb)
    {
        if (this.loginModel.isDatabaseConnected()){
            this.dbstatus.setText("Connected");
        } else {
            this.dbstatus.setText("Not Connected");
        }
        this.combobox.setItems(FXCollections.observableArrayList(option.values()));
    }

    @FXML
    public void Login(ActionEvent event)
    {
        try
        {
            if (this.loginModel.islogin(this.username.getText(), this.password.getText(), ((option)this.combobox.getValue()).toString()))
            {
                Stage stage = (Stage)this.loginbutton.getScene().getWindow();
                stage.close();
                switch (((option)this.combobox.getValue()).toString()) {
                    case "Admin":
                        adminLogin();
                        break;
                    case "Firma":
                        firmaLogin();
                        break;
                    case "Pracownik":
                        workerLogin();
                        break;
                }

            }
            else
            {
                this.dbstatus.setText("Wrong Credentials");
            }
        }
        catch (Exception localException) { localException.getStackTrace();}
    }


    public void adminLogin()
    {
        try
        {
            Stage adminStage = new Stage();
            FXMLLoader adminLoader = new FXMLLoader();
            Pane root = (Pane) adminLoader.load(getClass().getResource("/Admin/AdminFXML.fxml").openStream());
            AdminControler adminControler = (AdminControler)adminLoader.getController();

            Scene adminscene = new Scene(root);

            adminStage.setScene(adminscene);
            adminStage.setTitle("Admin Dashboard");
            adminStage.setResizable(false);
            adminStage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public void firmaLogin(){
        try{
        Stage firmaStage = new Stage();
        FXMLLoader firmaLoader = new FXMLLoader();
        Pane root = (Pane) firmaLoader.load(getClass().getResource("/Firma/FirmaFXML.fxml").openStream());
        FirmaControler firmaControler = (FirmaControler) firmaLoader.getController();

        Scene firmascene = new Scene(root);

        firmaStage.setScene(firmascene);
        firmaStage.setTitle("Firma Dashboard");
        firmaStage.setResizable(false);
        firmaStage.show();
    }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public void workerLogin(){
        try{
            Stage workerStage = new Stage();
            FXMLLoader workerLoader = new FXMLLoader();
            Pane root = (Pane) workerLoader.load(getClass().getResource("/Pracownik/WorkersFXML.fxml").openStream());
            WorkersControler workersControler = (WorkersControler) workerLoader.getController();

            Scene workerscene = new Scene(root);

            workerStage.setScene(workerscene);
            workerStage.setTitle("Pracownik");
           workerStage.setResizable(false);
            workerStage.show();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
