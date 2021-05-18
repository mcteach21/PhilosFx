package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
   /* @FXML
    private ImageView logo;*/

    @FXML
    Pane pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File file = new File("resources/philo2.png");
        Image image = new Image(file.toURI().toString());
        //logo.setImage(image);

        ImageView img = new ImageView();
        img.setImage(image);

        img.setFitWidth(130.0);
        img.setFitHeight(250.0);

        img.setLayoutX(118.0);
        img.setLayoutY(77.0);
        img.setPreserveRatio(true);
        pane.getChildren().add(img);
    }
}