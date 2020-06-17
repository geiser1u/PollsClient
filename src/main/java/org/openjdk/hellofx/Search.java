package org.openjdk.hellofx;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONObject;
import org.json.JSONArray;
import org.netbeans.saas.RestResponse;
import org.netbeans.saas.root.Rootpolls;

/**
 * Classe de scene de recherche
 */
public class Search {

    // Bouton de retour au menu
    private static Button returnBtn;
    // Bouton pour lancer la recherche
    private static Button goBtn;
    // Indice du resultat affiche
    private static int resultIndex = 0;
    
    /**
     * Construction de la scene de recherche
     * @param primaryStage Zone d'affichage de la fenetre
     * @param menuScene Scene de menu
     * @return Scene de recherche
     */
    public static Scene getScene(Stage primaryStage, Scene menuScene) {
        // Creation d'une grille
        GridPane grid = new GridPane();
        // Centrage de la grille
        grid.setAlignment(Pos.CENTER);
        // Largeur de la grille = 2
        grid.setHgap(2);
        // Hauteur de la grille = 7
        grid.setVgap(7);

        // Creation d'une pile
        StackPane titleBox = new StackPane();
        // Creation du texte Search
        Text sceneTitle = new Text("Search");
        // Mise en forme du texte
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        // Ajout du texte a la pile
        titleBox.getChildren().add(sceneTitle);
        // Placement de la pile dans la grille
        grid.add(titleBox, 1, 0);

        // Creation du label ID
        Label idLabel = new Label("ID: ");
        // Placement du label dans la grille
        grid.add(idLabel, 0, 2);
        // Creation du champ ID
        TextField idTextField = new TextField();
        // Placement du champ dans la grille
        grid.add(idTextField, 1, 2);

        // Creation du label Topic
        Label topicLabel = new Label("Topic: ");
        // Placement du label dans la grille
        grid.add(topicLabel, 0, 3);
        // Creation du champ Topic
        TextField topicTextField = new TextField();
        // Placement du champ dans la grille
        grid.add(topicTextField, 1, 3);

        // Creation du label Author
        Label authorLabel = new Label("Author: ");
        // Placement du label dans la grille
        grid.add(authorLabel, 0, 4);
        // Creation du champ Author
        TextField authorTextField = new TextField();
        // Placement du champ dans la grille
        grid.add(authorTextField, 1, 4);

        // Creation du bouton Menu
        returnBtn = new Button("Menu");
        // Creation du bouton Go
        goBtn = new Button("Go");
        // Creation d'une boite horizontale
        HBox hbBtn = new HBox(2);
        // Centrage de la boite horizontale
        hbBtn.setAlignment(Pos.CENTER);
        // Ajout du bouton Menu a la boite horizontale
        hbBtn.getChildren().add(returnBtn);
        // Ajout du bouton Go a la boite horizontale
        hbBtn.getChildren().add(goBtn);
        // Choix de l'espacement entre les boutons
        hbBtn.setSpacing(75);
        // Placement de la boite horizontale dans le grille
        grid.add(hbBtn, 1, 6);

        // Creation de la scene de recherche
        Scene searchScene = new Scene(grid, 500, 500);

        // Definition de l'action associee au bonton Go
        goBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            /**
             * Fonction appelee suite a un clic
             */
            public void handle(ActionEvent e) {
                // Initialisation des attributs du sondage
                String newQuestion = "";
                String newTopic = "";
                String newAuthor = "";
                // Initialisation de la liste des resultats et de la liste des reponses
                ArrayList<String> newChoices = new ArrayList<String>();
                JSONArray choices = null;

                // Recuperation des sondages via le webservice
                try {
                    // Initialisation de la reponse du webservice
                    RestResponse result = null;
                    // Recuperation des parametres de recherche
                    String pollId = idTextField.getText();
                    String pollTopic = topicTextField.getText();
                    String pollAuthor = authorTextField.getText();

                    // Si recherche par ID
                    if (!pollId.isEmpty()) {
                        // Recherche par ID
                        result = Rootpolls.pollById(pollId);
                    } else {
                        // Si recherche par Topic
                        if (!pollTopic.isEmpty()) {
                            // Si Author renseigne
                            if (!pollAuthor.isEmpty()) {
                                // Recherche par Topic et Author
                                result = Rootpolls.pollByAuthorAndType(pollTopic, pollAuthor);
                            // Si Author pas renseigne
                            } else {
                                // Recherche par Topic
                                result = Rootpolls.pollByType(pollTopic);
                            }
                        } else {
                            // Si recherche par Author
                            if (!pollAuthor.isEmpty()) {
                                // Recherche par Author
                                result = Rootpolls.pollByAuthor(pollAuthor);
                            }
                        }
                        // Si aucun champ renseigne, recuperation de tous les sondages
                        result = Rootpolls.allPolls();
                    }
                    
                    // Initialisation du resultat recu du webservice
                    JSONObject jsonObject = null;
                    JSONArray jsonArray = null;
                    // Si un seul sondage
                    if (result.getDataAsString().charAt(0) == '{') {
                        // Transformation en object json
                        jsonObject = new JSONObject(result.getDataAsString());
                    // Si plusieurs sondages
                    } else {
                        // Transformation en liste d'objets json
                        jsonArray = new JSONArray(result.getDataAsString());
                        // Recuperation de l'objet json correspondant a l'index du resultat a afficher
                        jsonObject = jsonArray.getJSONObject(resultIndex);
                    }

                    // Identification des champs de l'objet json
                    newQuestion = jsonObject.getString("question_text");
                    newTopic = jsonObject.getString("question_type");
                    newAuthor = jsonObject.getString("author");
                    choices = jsonObject.getJSONArray("choices");
                    for (int i = 0; i < choices.length(); i++) {
                        newChoices.add(choices.getJSONObject(i).getString("choice_text"));
                        newChoices.add("" + choices.getJSONObject(i).getInt("votes"));
                    }

                    // Mise a jour du texte a afficher dans la scene de resultats
                    SearchResult.setText(newQuestion, newTopic, newAuthor, newChoices);
                    // Construction de la scene de resultats et scene affichee = resultats
                    primaryStage.setScene(SearchResult.getScene());
                    // Definition de l'action associe au bouton New search
                    SearchResult.setReturn(primaryStage, searchScene, menuScene);
                    // Definition de l'action associe au bouton Menu
                    SearchResult.setMenu(primaryStage, menuScene);
                    // Definition de l'action associe au bouton Next
                    SearchResult.setNext(primaryStage, searchScene, menuScene);
                    
                } catch (Exception ex) {
                    // Affichage des erreurs rencontrees le cas echeant
                    ex.printStackTrace();
                }
            }
        });
        
        return searchScene;
    }

    /**
     * Definition de l'action associee au bouton Menu
     * @param primaryStage Zone d'affichage de la fenetre
     * @param menuScene Scene de menu
     */
    public static void setReturn(Stage primaryStage, Scene menuScene) {
        // Definition de l'action associee au bonton Menu
        returnBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            /**
             * Fonction appelee suite a un clic
             */
            public void handle(ActionEvent e) {
                // Scene affichee = menu
                primaryStage.setScene(menuScene);
            }
        });
    }

    /**
     * Clic sur le bouton Go
     */
    public static void setGo() {
        // Clic sur le bouton Go
        goBtn.fire();
    }
    
    /**
     * Incrementation de l'indice du resultat affiche
     */
    public static void setResultIndex() {
        // Incrementation de l'indice du resultat affiche
        resultIndex++;
    }
}
