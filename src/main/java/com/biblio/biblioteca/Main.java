package com.biblio.biblioteca;

import com.biblio.controller.*;
import com.biblio.connection.ConnectionDB;
import com.biblio.model.Book;
import com.biblio.model.Student;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main extends Application {

    static private Connection connection;
    static private Stage currentStage;

    static private Main instance;


    @Override
    public void start(Stage stage) {
        if (instance == null)
        {
            instance = this;
        } else
        {
            System.out.println("Se están creando demasiadas instancias de la clase main");
        }
        currentStage = stage;
        currentStage.getIcons().addAll(new Image("icon-colorful.png"));
        OpenLoginWindow();
    }

    @Override
    public void stop()
    {
        if (IsConnected()) ConnectionDB.CloseConnection(connection);
    }

    public static void main(String[] args) {
        connection = ConnectionDB.Connect();
        launch();
    }

    public static void CloseApp()
    {
        Platform.exit();
    }

    public static void GoToWeb(String link)
    {
        instance.getHostServices().showDocument(link);
    }

    //*************************
    //      Switch Views
    //*************************

    public static void OpenLoginWindow()
    {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            SetWindowDraggable(scene, currentStage);
            currentStage.initStyle(StageStyle.TRANSPARENT);

            currentStage.setTitle("Biblioteca - Login");
            currentStage.setResizable(false);
            currentStage.setScene(scene);
            currentStage.show();

            ((LoginViewController) fxmlLoader.getController()).Init(scene);
        } catch (IOException ex) {ex.printStackTrace();}
    }
    public static void OpenMainWindow()
    {
        try
        {
            currentStage.hide();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            SetWindowDraggable(scene, currentStage);
            currentStage.setScene(scene);
            currentStage.setResizable(true);
            currentStage.setTitle("Biblioteca");
            currentStage.show();

            ((BibliotecaController) fxmlLoader.getController()).Init();
        } catch (Exception ex) { ex.printStackTrace();}
    }

    public static void OpenBookCreatorWindow()
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("BookCreator-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Main.OpenSecondaryWindow(scene,"Registrador de Libro", false);
            ((BookCreatorViewController)  fxmlLoader.getController()).Init();
        } catch (Exception ex) { ex.printStackTrace();}
    }

    public static void OpenBookEditorWindow(Book book, BibliotecaController bibliotecaController)
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("BookEditor-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Main.OpenSecondaryWindow(scene,"Editor de Libro", false);
            ((BookEditorViewController)  fxmlLoader.getController()).Init(book, bibliotecaController);
        } catch (Exception ex) { ex.printStackTrace();}
    }

    public static void OpenStudentCreatorWindow() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AlumnCreator-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Main.OpenSecondaryWindow(scene,"Generador de estudiantes espontáneos", false);
            ((AlumnCreatorViewController)  fxmlLoader.getController()).Init();
        } catch (Exception ex) { ex.printStackTrace();}

    }
    public static void OpenStudentEditorWindow(Student student, AlumnTabController alumnTabController)
    {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AlumnEditor-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Main.OpenSecondaryWindow(scene,"Transformador de estudiantes díscolos", false);
            ((AlumnEditorViewController)  fxmlLoader.getController()).Init(student, alumnTabController);
        } catch (Exception ex) { ex.printStackTrace();}

    }

    public static void OpenBorrowCreatorWindow(Book book, BibliotecaController bibliotecaController)
    {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("BorrowCreator-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Main.OpenSecondaryWindow(scene,"Prestamista", false);
            ((BorrowCreatorViewController)  fxmlLoader.getController()).Init(book, bibliotecaController);
        } catch (Exception ex) { ex.printStackTrace();}

    }

    public static void OpenSecondaryWindow(Scene scene, String windowTitle, boolean resizable)
    {
        Stage secondaryStage = new Stage();

        secondaryStage.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
        SetWindowDraggable(scene, secondaryStage);

        secondaryStage.setTitle(windowTitle);
        secondaryStage.initStyle(StageStyle.TRANSPARENT);
        secondaryStage.setResizable(resizable);


        secondaryStage.show();
    }

    public static void CloseSecondaryWindow(Stage stage)
    {
        stage.close();
    }

    public static void SetWindowDraggable(Scene scene, Stage secondaryStage)
    {
        Parent root = scene.getRoot();
        scene.setFill(Color.TRANSPARENT);

        root.setOnMousePressed(pressEvent -> {
            root.setOnMouseDragged(dragEvent -> {
                secondaryStage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                secondaryStage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
            });
        });
    }
    //*************************
    //      DATABASE RELATED
    //*************************

    public static boolean IsConnected ()
    {
        return connection != null ;
    }

    public static boolean Reconnect()
    {
        if (IsConnected()) ConnectionDB.CloseConnection(connection);
        connection = ConnectionDB.Connect();
        return IsConnected();
    }

    public static boolean CheckUserCredential(String userName, String password)
    {
        //TODO: DELETE CHEAT LOGIN BEFORE RELEASE TIME
        if(CheatLogIn(userName, password)) return true;

        final String userTable = "usuarios";
        final String userField = "usuario";
        final String passwordField = "clave";
        boolean result = false;

        //String SQLrequest = "SELECT * FROM "+userTable+" WHERE "+userField+"=" + userName +" AND "+passwordField+"="+password;
        String SQLrequest = "SELECT * FROM "+userTable+" WHERE "+userField+"=? AND "+passwordField+"=?";
        System.out.println("Comprobando usuario y contraseña");
        try {
            PreparedStatement statement = connection.prepareStatement(SQLrequest) ;
            statement.setString(1, userName);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            result = resultSet.next();

        } catch (Exception ex){ex.printStackTrace();}

        return result;
    }

    private static boolean CheatLogIn(String username, String password)
    {
        //TODO: DELETE!
        return username.equals("Rimbarrita")  && password.equals("Rimbarruca") ;
    }

}