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

public class SearchResult {
    
    private static Button menuBtn;
    private static Button returnBtn;
    private static Button nextBtn;
    private static String question;
    private static String topic ;
    private static String author;
    private static ArrayList<String> choices;
    
    public static Scene getScene() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(1);
        int lastIndex = 0;
        
        if (choices != null) {
            grid.setVgap(5+choices.size());

            StackPane titleBox = new StackPane();
            Text sceneTitle = new Text("Search results");
            sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            titleBox.getChildren().add(sceneTitle);
            grid.add(titleBox, 0, 0);

            Label pollQuestion = new Label("Question: " + question);
            grid.add(pollQuestion, 0, 2);

            Label pollTopic = new Label("Topic: " + topic);
            grid.add(pollTopic, 0, 3);

            Label pollAuthor = new Label("Author: " + author);
            grid.add(pollAuthor, 0, 4);

            grid.add(new Label("Votes:"), 0, 5);
            lastIndex = 5;
            for (int i=0; i<choices.size()/2; i++) {
                grid.add(new Label("- " + choices.get(2*i) + ": " + choices.get(2*i+1)), 0, 6+i);
                lastIndex += 1;
            }
        }
        menuBtn = new Button("Menu");
        returnBtn = new Button("New search");
        nextBtn = new Button("Next");
        HBox hbBtn = new HBox(1);
        hbBtn.setAlignment(Pos.CENTER);
        hbBtn.getChildren().add(menuBtn);
        hbBtn.getChildren().add(returnBtn);
        hbBtn.getChildren().add(nextBtn);
        hbBtn.setSpacing(40);
        grid.add(hbBtn, 0, lastIndex+1);
        return new Scene(grid, 500, 500);
    }
    
    public static void setMenu(Stage primaryStage, Scene menuScene) {
        menuBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                primaryStage.setScene(menuScene);
            }
        });
    }
    
    public static void setReturn(Stage primaryStage, Scene searchScene, Scene menuScene) {
        returnBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Search.setReturn(primaryStage, menuScene);
                primaryStage.setScene(searchScene);
            }
        });
    }
    
    public static void setNext(Stage primaryStage, Scene searchScene, Scene menuScene) {
        nextBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Search.setResultIndex();
                //Scene searchScene = Search.getScene(primaryStage);
                primaryStage.setScene(searchScene);
                SearchResult.setReturn(primaryStage, searchScene, menuScene);
                SearchResult.setMenu(primaryStage, menuScene);
                Search.setGo();
            }
        });
    }
    
    public static void setText(String newQuestion, String newTopic, String newAuthor, ArrayList<String> newChoices) {
        question = newQuestion;
        topic = newTopic;
        author = newAuthor;
        choices = newChoices;
    }
}
