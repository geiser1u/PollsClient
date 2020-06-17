package org.openjdk.hellofx;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe d'application javafx
 */
public class App extends Application {

    /**
     * Fonction de lancement de la fenetre
     */
    public static void main(String[] args) {
        // Lancement de start
        launch(args);
    }

    @Override
    /**
     * Fonction appelee au lancement de la fenetre
     * @param primaryStage Zone d'affichage de la fenetre
     */
    public void start(Stage primaryStage) {
        // Construction de la scene du menu
        Scene menu = Menu.getScene(primaryStage);
        // Modification du titre de la fenetre
        primaryStage.setTitle("Incredible Polls");
        // Scene affichee = menu
        primaryStage.setScene(menu);
        // Affichage
        primaryStage.show();
    }
}
