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

public class Menu {
    
    public static Scene getScene(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(1);
        grid.setVgap(4);
        
        StackPane titleBox = new StackPane();
        Text sceneTitle = new Text("Menu");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        titleBox.getChildren().add(sceneTitle);
        grid.add(titleBox, 0, 0);
        
        Button allBtn = new Button("All polls");
        Button searchBtn = new Button("Search");
        Button statsBtn = new Button("Statistics");
        VBox vbBtn = new VBox(3);
        vbBtn.setAlignment(Pos.CENTER);
        vbBtn.getChildren().add(allBtn);
        vbBtn.getChildren().add(searchBtn);
        vbBtn.getChildren().add(statsBtn);
        vbBtn.setSpacing(15);
        grid.add(vbBtn, 0, 4);
        
        Scene menuScene = new Scene(grid, 500, 500);
        
        allBtn.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent e) {
               primaryStage.setScene(Search.getScene(primaryStage, menuScene));
               Search.setGo();
           }
        });
        
        searchBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                primaryStage.setScene(Search.getScene(primaryStage, menuScene));
                Search.setReturn(primaryStage, menuScene);
            }
        });
        
        statsBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                primaryStage.setScene(Statistics.getScene(primaryStage));
                Statistics.setReturn(primaryStage, menuScene);
            }
        });
        return menuScene;
    }
}
