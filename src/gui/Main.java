package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Philosophes : Diner!");

        Scene scene = new Scene(root, 450, 450);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(scene);

        primaryStage.show();

       /* primaryStage.setTitle("ImageView Experiment 1");

        FileInputStream input = new FileInputStream("src/sample/philo2.jpg");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.preserveRatioProperty();

        HBox hbox = new HBox(imageView);

        Scene scene = new Scene(hbox, 200, 100);
        primaryStage.setScene(scene);
        primaryStage.show();*/

    }


    public static void main(String[] args) {
        launch(args);
    }
}
