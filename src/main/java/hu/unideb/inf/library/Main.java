package hu.unideb.inf.library;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Main extends Application {

    private Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * Program belépési pontja
     * Kezdésnek a bejelentkezési ablakot nyitja meg
     * @param stage kezdőképernyő stage objektuma
     * @throws Exception exception kezelése nem talált fxml esetén
     */
    @Override
    public void start(Stage stage) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/LoginScreen.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);
            stage.setTitle("Library");
            stage.setScene(scene);

            stage.show();
        } catch (IOException ex) {
            logger.error("Hiba a kezdőképernyő betöltésekor! " + ex);
        }

    }

    public static void main(String[] args) {
       launch(args);
    }
}
