package org.openjdk.hellofx;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.netbeans.saas.root.Rootpolls;
import org.netbeans.saas.RestResponse;
import org.json.JSONObject;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Welcome");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Polls");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("PollID:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        /*Label pw = new Label("");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);*/

        Button btn = new Button("Request");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                //actiontarget.setFill(Color.FIREBRICK);
                String question = "";
                try {
                    String pollId = userTextField.getText();
                    System.out.println(pollId);

                    RestResponse result = Rootpolls.pollById(pollId);
                    
                    JSONObject jsonObject = new JSONObject(result.getDataAsString());
                    System.out.println(jsonObject);
                    question = "Question : " + jsonObject.getString("question_text");
                    //question += "\n" + jsonObject.getString("pub_date");
                    question += "\n" + "Type : " + jsonObject.getString("question_type");
                    question += "\n" + "Author : " + jsonObject.getString("author");
                    JSONArray choices = jsonObject.getJSONArray("choices");
                    JSONObject jsonObject1;
                    question += "\n" + "Choices :";
                    for (int i = 0; i < choices.length(); i++) {
                        jsonObject1 = choices.getJSONObject(i);
                        question += "\n- " + jsonObject1.getString("choice_text") + " : " + jsonObject1.getInt("votes");
                    }
                    //System.out.println(question);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                actiontarget.setText(question);
            }
        });

        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        try {
            scene.getStylesheets().add(App.class.getResource("Login.css").toExternalForm());
        } catch (Exception e) {
            System.err.println(e);
        }
        primaryStage.show();
    }
}
