package org.openjdk.hellofx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.netbeans.saas.RestResponse;
import org.netbeans.saas.root.Rootpolls;

/**
 * Classe de scene de statistiques
 */
public class Statistics {
    
    // Bouton de retour au menu
    private static Button returnBtn;
    
    /**
     * Recuperation de l'ensemble des sondages
     * @return Liste des objets json correspondants aux sondages
     */
    public static JSONArray getAllPolls() {
        // Initialisation de la liste des objets json correspondant aux sondages
        JSONArray jsonArray = null;
        // Recuperation des sondages via le webservice
        try {
            // Recuperation de l'ensemble des sondages
            RestResponse result = Rootpolls.allPolls();
            // Transformation en liste d'objets json
            jsonArray = new JSONArray(result.getDataAsString());
        } catch (Exception ex) {
            // Affichage des erreurs rencontrees le cas echeant
            ex.printStackTrace();
        }
        
        return jsonArray;
    }
    
    /**
     * Construction de la scene de statistiques
     * @param primaryStage Zone d'affichage de la fenetre
     * @return Scene de statistiques
     */
    public static Scene getScene(Stage primaryStage) {
        // Creation d'une grille
        GridPane grid = new GridPane();
        // Centrage de la grille
        grid.setAlignment(Pos.CENTER);
        // Largeur de la grille = 3
        grid.setHgap(3);
        // Hauteur de la grille = 5
        grid.setVgap(5);
        
        // Creation d'une pile
        StackPane titleBox = new StackPane();
        // Creation du texte Statistics
        Text sceneTitle = new Text("Statistics");
        // Mise en forme su texte
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        // Ajout du texte a la pile
        titleBox.getChildren().add(sceneTitle);
        // Placement de la pile dans la grille
        grid.add(titleBox, 1, 0);
        
        // Recuperation de l'ensemble des sondages au format json
        JSONArray jsonArray = getAllPolls();
        
        // Creation d'une pile
        StackPane pollsNumBox = new StackPane();
        // Recuperation du nombre de sondages
        int num = jsonArray.length();
        // Creation du texte Number of polls: suivi du nombre de sondages
        Text pollsNum = new Text("Number of polls: " + num);
        // Ajout du texte a la pile
        pollsNumBox.getChildren().add(pollsNum);
        // Placement de la pile dans la grille
        grid.add(pollsNumBox, 1, 1);
        
        // Creation d'une pile
        StackPane votesMeanBox = new StackPane();
        // Initialisation du nombre de votes
        int sum = 0;
        for (int i=0; i<jsonArray.length(); i++) {
            // Recuperation du champ Choices du sondage
            JSONArray choices  = jsonArray.getJSONObject(i).getJSONArray("choices");
            for (int j=0; j<choices.length(); j++) {
                // Ajout du nombre de votes pour la reponse
                sum += choices.getJSONObject(j).getInt("votes");
            }
        }
        // Division par le nombre de sondages
        int mean = sum / jsonArray.length();
        // Creation du texte Mean of votes per poll: suivi du nombre moyen de votes par sondage
        Text votesMean = new Text("Mean of votes per poll: " + mean);
        // Ajout du texte a la pile
        votesMeanBox.getChildren().add(votesMean);
        // Placement de la pile dans la grille
        grid.add(votesMeanBox, 1, 2);
        
        // Creation d'un diagramme a secteurs
        PieChart topicChart = new PieChart();
        // Choix du titre du diagramme
        topicChart.setTitle("Topics");
        for (int i=0; i<jsonArray.length(); i++) {
            // Initialisation de la variable indiquant que le Topic a deja ete ajoute au diagramme
            boolean added = false;
            for (int j=0; j<topicChart.getData().size(); j++) {
                // Si Topic correspondant
                if (jsonArray.getJSONObject(i).getString("question_type").equals(topicChart.getData().get(j).getName())) {
                    // Mise a jour de la valeur associee au secteur correspondant
                    topicChart.getData().get(j).setPieValue(topicChart.getData().get(j).getPieValue() + 1);
                    // Mise a jour de la variable indiquant que le Topic a deja ete ajoute au diagramme
                    added = true;
                    break;
                }
            }
            // Si Topic jamais rencontre
            if (!added) {
                // Ajout d'un secteur au diagramme
                topicChart.getData().add(new PieChart.Data(jsonArray.getJSONObject(i).getString("question_type"), 1));
            }
        }
        // Placement du diagramme dans la grille
        grid.add(topicChart, 0, 3);
        
        // Creation d'un diagramme a secteurs
        PieChart authorChart = new PieChart();
        // Choix du titre du diagramme
        authorChart.setTitle("Authors");
        for (int i=0; i<jsonArray.length(); i++) {
            // Initialisation de la variable indiquant que le Author a deja ete ajoute au diagramme
            boolean added = false;
            for (int j=0; j<authorChart.getData().size(); j++) {
                // Si Author correspondant
                if (jsonArray.getJSONObject(i).getString("author").equals(authorChart.getData().get(j).getName())) {
                    // Mise a jour de la valeur associee au secteur correspondant
                    authorChart.getData().get(j).setPieValue(authorChart.getData().get(j).getPieValue() + 1);
                    // Mise a jour de la variable indiquant que le Author a deja ete ajoute au diagramme
                    added = true;
                    break;
                }
            }
            // Si Author jamais rencontre
            if (!added) {
                // Ajout d'un secteur au diagramme
                authorChart.getData().add(new PieChart.Data(jsonArray.getJSONObject(i).getString("author"), 1));
            }
        }
        // Placement du diagramme dans la grille
        grid.add(authorChart, 2, 3);
        
        // Creation du bouton Menu
        returnBtn = new Button("Menu");
        // Creation du boite verticale
        VBox vbBtn = new VBox(3);
        // Centrage de la boite verticale
        vbBtn.setAlignment(Pos.CENTER);
        // Ajout du bouton Menu a la boite verticale
        vbBtn.getChildren().add(returnBtn);
        // Placement de la boite verticale dans la grille
        grid.add(vbBtn, 1, 4);
        
        // Creation de la scene de statistiques
        Scene statScene = new Scene(grid, 500, 500);
        
        return statScene;
    }
    
    /**
     * Definition de l'action associee au bouton Return
     * @param primaryStage Zone d'affichage de la fenetre
     * @param menuScene Scene de menu
     */
    public static void setReturn(Stage primaryStage, Scene menuScene) {
        // Definition de l'action associee au bouton Return
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
}
