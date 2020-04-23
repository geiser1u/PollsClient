package org.openjdk.hellofx;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.AccessibleAction;
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

public class Search {

    private static Button returnBtn;
    private static Button goBtn;
    private static int resultIndex = 0;
    
    public static Scene getScene(Stage primaryStage, Scene menuScene) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(2);
        grid.setVgap(7);

        StackPane titleBox = new StackPane();
        Text sceneTitle = new Text("Search");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        titleBox.getChildren().add(sceneTitle);
        grid.add(titleBox, 1, 0);

        Label idLabel = new Label("ID: ");
        grid.add(idLabel, 0, 2);
        TextField idTextField = new TextField();
        grid.add(idTextField, 1, 2);

        Label topicLabel = new Label("Topic: ");
        grid.add(topicLabel, 0, 3);
        TextField topicTextField = new TextField();
        grid.add(topicTextField, 1, 3);

        Label authorLabel = new Label("Author: ");
        grid.add(authorLabel, 0, 4);
        TextField authorTextField = new TextField();
        grid.add(authorTextField, 1, 4);

        returnBtn = new Button("Menu");
        goBtn = new Button("Go");
        HBox hbBtn = new HBox(2);
        hbBtn.setAlignment(Pos.CENTER);
        hbBtn.getChildren().add(returnBtn);
        hbBtn.getChildren().add(goBtn);
        hbBtn.setSpacing(75);
        grid.add(hbBtn, 1, 6);

        Scene searchScene = new Scene(grid, 500, 500);

        goBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String newQuestion = "";
                String newTopic = "";
                String newAuthor = "";
                ArrayList<String> newChoices = new ArrayList<String>();
                JSONArray choices = null;

                try {
                    RestResponse result = null;
                    String pollId = idTextField.getText();
                    String pollTopic = topicTextField.getText();
                    String pollAuthor = authorTextField.getText();

                    if (!pollId.isEmpty()) {
                        result = Rootpolls.pollById(pollId);
                    } else {
                        if (!pollTopic.isEmpty()) {
                            if (!pollAuthor.isEmpty()) {
                                result = Rootpolls.pollByAuthorAndType(pollTopic, pollAuthor);
                            } else {
                                result = Rootpolls.pollByType(pollTopic);
                            }
                        } else {
                            if (!pollAuthor.isEmpty()) {
                                result = Rootpolls.pollByAuthor(pollAuthor);
                            }
                        }
                        result = Rootpolls.allPolls();
                    }
                    System.out.println(result.getDataAsString());
                    JSONObject jsonObject = null;
                    JSONArray jsonArray = null;
                    if (result.getDataAsString().charAt(0) == '{') {
                        jsonObject = new JSONObject(result.getDataAsString());
                    } else {
                        jsonArray = new JSONArray(result.getDataAsString());
                        jsonObject = jsonArray.getJSONObject(resultIndex);
                    }

                    newQuestion = jsonObject.getString("question_text");
                    newTopic = jsonObject.getString("question_type");
                    newAuthor = jsonObject.getString("author");
                    choices = jsonObject.getJSONArray("choices");
                    for (int i = 0; i < choices.length(); i++) {
                        newChoices.add(choices.getJSONObject(i).getString("choice_text"));
                        newChoices.add("" + choices.getJSONObject(i).getInt("votes"));
                    }

                    SearchResult.setText(newQuestion, newTopic, newAuthor, newChoices);
                    primaryStage.setScene(SearchResult.getScene());
                    SearchResult.setReturn(primaryStage, searchScene, menuScene);
                    SearchResult.setMenu(primaryStage, menuScene);
                    SearchResult.setNext(primaryStage, searchScene, menuScene);
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        return searchScene;
    }

    public static void setReturn(Stage primaryStage, Scene menuScene) {
        returnBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                primaryStage.setScene(menuScene);
            }
        });
    }

    public static void setGo() {
        goBtn.fire();
    }
    
    public static void setResultIndex() {
        resultIndex++;
    }
}
