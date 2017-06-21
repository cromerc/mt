package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Group root = new Group();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        Button button = new Button("Exit");
        button.setDefaultButton(true);
        button.setLayoutX(130);button.setLayoutY(125);
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.exit(0);
            }
        };
        button.addEventFilter(MouseEvent.MOUSE_CLICKED,eventHandler);
        root.getChildren().add(button);
        primaryStage.show();
    }


    public static void main(String[] args) {
        //launch(args);
        System.out.println("Hola Mundo");
    }
}
