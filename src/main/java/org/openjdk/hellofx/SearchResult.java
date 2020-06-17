package org.openjdk.hellofx;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Classe de scene de resultats
 */
public class SearchResult {
    
    // Bouton de retour au menu
    private static Button menuBtn;
    // Bouton de retour a la recherche
    private static Button returnBtn;
    // Bouton d'affichage du resultat suivant
    private static Button nextBtn;
    //Champ Question du resultat
    private static String question;
    // Champ Topic du resultat
    private static String topic ;
    // Champ Author du resultat
    private static String author;
    // Champ Choices du resultat
    private static ArrayList<String> choices;
    
    /**
     * Construction de la scene de resultats
     * @return Scene de resultats
     */
    public static Scene getScene() {
        // Creation d'une grille
        GridPane grid = new GridPane();
        // Centrage de la grille
        grid.setAlignment(Pos.CENTER);
        // Largeur de la grille = 1
        grid.setHgap(1);
        // Initialisation de l'ordonnee du dernier element graphique
        int lastIndex = 0;
        
        // Si le sondage comporte des reponses
        if (choices != null) {
            // Hauteur de la grille = 5 + nombre de reponses differentes
            grid.setVgap(5+choices.size());

            // Creation d'une pile
            StackPane titleBox = new StackPane();
            // Creation du texte Search results
            Text sceneTitle = new Text("Search results");
            // Mise en forme du texte
            sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            // Ajout du texte a la pile
            titleBox.getChildren().add(sceneTitle);
            // Placement de la pile dans la grille
            grid.add(titleBox, 0, 0);

            // Creation du label Question: suivi du champ Question du sondage
            Label pollQuestion = new Label("Question: " + question);
            // Placement du label dans la grille
            grid.add(pollQuestion, 0, 2);

            // Creation du label Topic: suivi du champ Topic du sondage
            Label pollTopic = new Label("Topic: " + topic);
            // Placement du label dans la grille
            grid.add(pollTopic, 0, 3);

            // Creation du label Author: suivi du champ Author du sondage
            Label pollAuthor = new Label("Author: " + author);
            // Placement du label dans la grille
            grid.add(pollAuthor, 0, 4);

            // Creation du label Votes: et placement dans la grille
            grid.add(new Label("Votes:"), 0, 5);
            // Mise a jour de l'ordonnee du dernier element graphique
            lastIndex = 5;
            // Creation d'un label par reponse differente au sondage
            for (int i=0; i<choices.size()/2; i++) {
                // Creation du label donnant une reponse au sondage et son nombre de votes associe et placement dans la grille
                grid.add(new Label("- " + choices.get(2*i) + ": " + choices.get(2*i+1)), 0, 6+i);
                // Mise a jour de l'ordonnee du dernier element graphique
                lastIndex += 1;
            }
        }
        
        // Creation du bouton Menu
        menuBtn = new Button("Menu");
        // Creation du bouton New search
        returnBtn = new Button("New search");
        // Creation du bouton Next
        nextBtn = new Button("Next");
        // Creation d'une boite horizontale
        HBox hbBtn = new HBox(1);
        // Centrage de la boite horizontale
        hbBtn.setAlignment(Pos.CENTER);
        // Ajout du bouton Menu a la boite horizontale
        hbBtn.getChildren().add(menuBtn);
        // Ajout du bouton New search a la boite horizontale
        hbBtn.getChildren().add(returnBtn);
        // Ajout du bouton Next a la boite horizontale
        hbBtn.getChildren().add(nextBtn);
        // Choix de l'espacement entre les boutons
        hbBtn.setSpacing(40);
        // Placement de la boite horizontale dans la grille
        grid.add(hbBtn, 0, lastIndex+1);
        
        return new Scene(grid, 500, 500);
    }
    
    /**
     * Definition de l'action associee au bouton Menu
     * @param primaryStage Zone d'affichage de la fenetre
     * @param menuScene Scene de menu
     */
    public static void setMenu(Stage primaryStage, Scene menuScene) {
        // Definition de l'action associee au bouton Menu
        menuBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            /**
             * Fontion appelee suite a un clic
             */
            public void handle(ActionEvent e) {
                // Scene affichee = menu
                primaryStage.setScene(menuScene);
            }
        });
    }
    
    /***
     * Definition de l'action associee au bouton New search
     * @param primaryStage Zone d'affichage de la fenetre
     * @param searchScene Scene de recherche
     * @param menuScene Scene de menu
     */
    public static void setReturn(Stage primaryStage, Scene searchScene, Scene menuScene) {
        // Definition de l'action associee au bouton New search
        returnBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            /**
             * Fonction appelee suite a un clic
             */
            public void handle(ActionEvent e) {
                // Definition de l'action associe au bouton Return
                Search.setReturn(primaryStage, menuScene);
                // Scene affichee = recherche
                primaryStage.setScene(searchScene);
            }
        });
    }
    
    /**
     * Definition de l'action associee au bouton Next
     * @param primaryStage Zone d'affichage de la fenetre
     * @param searchScene Scene de recherche
     * @param menuScene Scene de menu
     */
    public static void setNext(Stage primaryStage, Scene searchScene, Scene menuScene) {
        // Definition de l'action associee au bouton Next
        nextBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            /**
             * Fonction appelee suite a un clic
             */
            public void handle(ActionEvent e) {
                // Mise a jour de l'indice du resultat a afficher
                Search.setResultIndex();
                // Scene affichee = recherche
                primaryStage.setScene(searchScene);
                // Definition de l'action associee au bouton New search
                SearchResult.setReturn(primaryStage, searchScene, menuScene);
                // Definition de l'action associee au bouton Menu
                SearchResult.setMenu(primaryStage, menuScene);
                // Clic sur le bouton Go
                Search.setGo();
            }
        });
    }
    
    /**
     * Mise a jour des champs du resultat
     * @param newQuestion Nouveau champ Question du resultat
     * @param newTopic Nouveau champ Topic du resultat
     * @param newAuthor Nouveau champ Author du resultat
     * @param newChoices Nouveau champ Choices du resultat
     */
    public static void setText(String newQuestion, String newTopic, String newAuthor, ArrayList<String> newChoices) {
        // Mise a jour du champ Question du resultat
        question = newQuestion;
        // Mise a jour du champ Topic du resultat
        topic = newTopic;
        // Mise a jour du champ Author du resultat
        author = newAuthor;
        // Mise a jour du champ Choices du resultat
        choices = newChoices;
    }
}
