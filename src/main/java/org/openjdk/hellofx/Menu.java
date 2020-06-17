package org.openjdk.hellofx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Classe de scene de menu
 */
public class Menu {
    
    /**
     * Construction de la scene de menu
     * @param primaryStage Zone d'affichage de la fenetre
     * @return Scene de menu
     */
    public static Scene getScene(Stage primaryStage) {
        // Creation d'une grille
        GridPane grid = new GridPane(); 
        // Centrage de la grille
        grid.setAlignment(Pos.CENTER); 
        // Largeur de la grille = 1
        grid.setHgap(1); 
        // Hauteur de la grille = 4
        grid.setVgap(4); 
        
        // Creation d'une pile
        StackPane titleBox = new StackPane();
        // Creation du texte Menu
        Text sceneTitle = new Text("Menu");
        //Mise en forme du texte
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20)); // Choix 
        // Ajout du texte a la pile
        titleBox.getChildren().add(sceneTitle);
        // Placement de la pile dans la grille
        grid.add(titleBox, 0, 0);
        
        // Creation du bouton All polls
        Button allBtn = new Button("All polls");
        // Creation du bouton Search
        Button searchBtn = new Button("Search");
        // Creation du bouton Statistics
        Button statsBtn = new Button("Statistics");
        // Creationd'une boite verticale
        VBox vbBtn = new VBox(3);
        // Centrage de la boite verticale
        vbBtn.setAlignment(Pos.CENTER);
        // Ajout des boutons a la boite verticale
        vbBtn.getChildren().add(allBtn);
        vbBtn.getChildren().add(searchBtn);
        vbBtn.getChildren().add(statsBtn);
        vbBtn.setSpacing(15);
        // Placement de la boite verticale dans la grille
        grid.add(vbBtn, 0, 4);
        
        // Creation de la scene de menu
        Scene menuScene = new Scene(grid, 500, 500);
        
        // Definition de l'action associee au bonton All polls
        allBtn.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           /**
            * Fonction appelee suite a un clic
            */
           public void handle(ActionEvent e) {
               // Construction de la scene de recherche et scene affichee = recherche
               primaryStage.setScene(Search.getScene(primaryStage, menuScene));
               // Clic sur le bouton Go
               Search.setGo();
           }
        });
        
        // Definition de l'action associee au bonton Search
        searchBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            /**
             * Fonction appelee suite a un clic
             */
            public void handle(ActionEvent e) {
                // Construction de la scene de recherche et scene affichee = recherche
                primaryStage.setScene(Search.getScene(primaryStage, menuScene));
                // Modification de la fonction appelee suite a un clic sur le bouton Return
                Search.setReturn(primaryStage, menuScene);
            }
        });
        
        // Definition de l'action associee au bonton Statistics
        statsBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            /**
             * Fonction appelee suite a un clic
             */
            public void handle(ActionEvent e) {
                // Construction de la scene de statistiques et scene affichee = statistiques
                primaryStage.setScene(Statistics.getScene(primaryStage));
                // Modification de la fonction appelee suite a un clic sur le bouton Return
                Statistics.setReturn(primaryStage, menuScene);
            }
        });
        
        return menuScene;
    }
}
