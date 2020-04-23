package org.openjdk.hellofx;

import java.util.ArrayList;
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

public class Statistics {
    
    private static Button returnBtn;
    
    public static JSONArray getAllPolls() {
        JSONArray jsonArray = null;
        try {
            RestResponse result = Rootpolls.allPolls();
            System.out.println(result.getDataAsString());
            jsonArray = new JSONArray(result.getDataAsString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return jsonArray;
    }
    
    public static Scene getScene(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(3);
        grid.setVgap(5);
        
        StackPane titleBox = new StackPane();
        Text sceneTitle = new Text("Statistics");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        titleBox.getChildren().add(sceneTitle);
        grid.add(titleBox, 1, 0);
        
        JSONArray jsonArray = getAllPolls();
        
        StackPane pollsNumBox = new StackPane();
        int num = jsonArray.length();
        Text pollsNum = new Text("Number of polls: " + num);
        pollsNumBox.getChildren().add(pollsNum);
        grid.add(pollsNumBox, 1, 1);
        
        StackPane votesMeanBox = new StackPane();
        int sum = 0;
        for (int i=0; i<jsonArray.length(); i++) {
            JSONArray choices  = jsonArray.getJSONObject(i).getJSONArray("choices");
            for (int j=0; j<choices.length(); j++) {
                sum += choices.getJSONObject(j).getInt("votes");
            }
        }
        int mean = sum / jsonArray.length();
        Text votesMean = new Text("Mean of votes per poll: " + mean);
        votesMeanBox.getChildren().add(votesMean);
        grid.add(votesMeanBox, 1, 2);
        
        PieChart topicChart = new PieChart();
        topicChart.setTitle("Topics");
        for (int i=0; i<jsonArray.length(); i++) {
            boolean added = false;
            for (int j=0; j<topicChart.getData().size(); j++) {
                if (jsonArray.getJSONObject(i).getString("question_type").equals(topicChart.getData().get(j).getName())) {
                    topicChart.getData().get(j).setPieValue(topicChart.getData().get(j).getPieValue() + 1);
                    added = true;
                    break;
                }
            }
            if (!added) {
                topicChart.getData().add(new PieChart.Data(jsonArray.getJSONObject(i).getString("question_type"), 1));
            }
        }
        grid.add(topicChart, 0, 3);
        
        PieChart authorChart = new PieChart();
        authorChart.setTitle("Authors");
        for (int i=0; i<jsonArray.length(); i++) {
            boolean added = false;
            for (int j=0; j<authorChart.getData().size(); j++) {
                if (jsonArray.getJSONObject(i).getString("author").equals(authorChart.getData().get(j).getName())) {
                    authorChart.getData().get(j).setPieValue(authorChart.getData().get(j).getPieValue() + 1);
                    added = true;
                    break;
                }
            }
            if (!added) {
                authorChart.getData().add(new PieChart.Data(jsonArray.getJSONObject(i).getString("author"), 1));
            }
        }
        grid.add(authorChart, 2, 3);
        
        returnBtn = new Button("Menu");
        VBox vbBtn = new VBox(3);
        vbBtn.setAlignment(Pos.CENTER);
        vbBtn.getChildren().add(returnBtn);
        vbBtn.setSpacing(75);
        grid.add(vbBtn, 1, 4);
        
        Scene menuScene = new Scene(grid, 500, 500);
        
        return menuScene;
    }
    
    public static void setReturn(Stage primaryStage, Scene menuScene) {
        returnBtn.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent e) {
               primaryStage.setScene(menuScene);
           }
        });
    }
}
