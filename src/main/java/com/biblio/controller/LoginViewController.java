package com.biblio.controller;

import com.biblio.biblioteca.Main;

import com.biblio.service.LoginService;
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

        return LoginService.CheckUserCredential(userNameField.getText(), passwordField.getText());
    }

    private void GoToApp(ActionEvent event)
    {
        Main.OpenMainWindow();

    }

    private boolean CheckConnection()
    {
        if(!Main.IsConnected() && !Main.Reconnect())
        {
            errorBDLabel.setOpacity(1d);
            return false;
        }
        if(errorBDLabel.getOpacity() > 0) errorBDLabel.setOpacity(0d);
        return true;
    }


    public void Init(Scene scene)
    {
        CheckConnection();

    }


}
