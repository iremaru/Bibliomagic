package com.biblio.controller;

import com.biblio.biblioteca.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LoginViewController {

    @FXML
    private TextField userNameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label userErrorMsg;
    @FXML
    private Label passwordErrorMsg;
    @FXML
    private Label errorBDLabel;
    @FXML
    private AnchorPane window;

    @FXML
    private void OnBtnOkClick(ActionEvent event)
    {
        boolean userNameIsCorrect = CheckUserNameField();
        boolean passwordIsCorrect = CheckPasswordField();

        if(userNameIsCorrect && passwordIsCorrect)
        {
            System.out.println("Contraseña y Usuario bien escritos");
            if( VerifyCredentialsInDB() ) {
                GoToApp(event);
            }
        }
    }

    @FXML
    private void CloseApp(ActionEvent event)
    {
        Main.CloseApp();
    }

    private boolean CheckUserNameField() {

        if(userNameField.getText().isEmpty())
        {
            userErrorMsg.setText("Debe introducir un nombre de usuario.");
            return false;
        }
        userErrorMsg.setText("");
        return true;

    }

    private boolean CheckPasswordField()
    {
        if(passwordField.getText().isEmpty())
        {
            passwordErrorMsg.setText("Debe introducir una contraseña.");
            return false;
        }
        passwordErrorMsg.setText("");
        return true;
    }

    private boolean VerifyCredentialsInDB() {
        /*if (CheckConnection())
        {
            System.out.println("Verificando credenciales");
            return BibliotecaApplication.CheckUserCredential(userNameField.getText(), passwordField.getText());
        }*/

        return Main.CheckUserCredential(userNameField.getText(), passwordField.getText());
    }

    private void GoToApp(ActionEvent event)
    {
        /*try
        {
            Stage stage = (Stage)(((Node)(event.getSource())).getScene().getWindow()) ;
            stage.hide();

            //Parent root = FXMLLoader.load(BibliotecaApplication.class.getResource("main-view.fxml"));
            Scene scene = new Scene(FXMLLoader.load(BibliotecaApplication.class.getResource("main-view.fxml")));
            stage.setScene(scene);
            stage.show();

        } catch (Exception ex) {}*/
        Main.OpenMainWindow();

    }

    private boolean CheckConnection()
    {
        System.out.println("Comprobando conexión");
        //Comprueba que ni esté conectada ni reconecta
        if(!Main.IsConnected() && !Main.Reconnect())
        {
            errorBDLabel.setOpacity(1d);
            return false;
        }
        System.out.println("Está conectado");
        if(errorBDLabel.getOpacity() > 0) errorBDLabel.setOpacity(0d);
        return true;
    }


    public void Init(Scene scene)
    {
        CheckConnection();

    }


}
