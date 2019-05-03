package hu.unideb.inf.library.controller;

import hu.unideb.inf.library.model.UserModel;
import hu.unideb.inf.library.model.pojo.User;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    public void initialize(URL location, ResourceBundle resources) {
        um = new UserModel();
    }

    /**
     * UserModel osztály egy példánya.
     */
    private UserModel um;

    /**
     * A bejelentkezési ablak "Felhasználónév" mezőjének input értéke.
     */
    @FXML
    private TextArea loginUsernameInput;

    /**
     * A bejelentkezési ablak "Jelszó" mezőjének input értéke.
     */
    @FXML
    private PasswordField loginPasswordInput;

    /**
     * Hibás felhasználónév/jelszó esetén figyelmeztető szöveg labelje.
     */
    @FXML
    private Label loginErrorMsg;

    /**
     * Bejelentkezés elsütése kattintásra
     * @param event
     */
    @FXML
    private void triggerLogin(Event  event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(HomeController.class.getResource("/fxml/HomePageScreen.fxml"));
        Parent homePageParent = loader.load();
        Scene homePageScene = new Scene(homePageParent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        User currentUser =  um.getUser(loginUsernameInput.getText(), loginPasswordInput.getText()) ;

        if (currentUser != null) {
            HomeController homeController = loader.getController();
            homeController.setUser(currentUser);
            homeController.init();

            stage.hide();
            stage.setTitle("Library - Home screen");
            stage.setScene(homePageScene);
            stage.show();

            Rectangle2D stageBounds = Screen.getPrimary().getBounds();
            stage.setX((stageBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((stageBounds.getHeight() - stage.getHeight()) / 2);
        } else {
            loginErrorMsg.setText("Hibás felhasználónév vagy jelszó!");
        }


    }


}
