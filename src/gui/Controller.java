package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import philos.Diner;
import philos.INotifier;
import philos.State;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class Controller implements Initializable {
   /* @FXML
    private ImageView logo;*/

    @FXML
    Pane pane;

    @FXML
    Button start;
    @FXML
    Button stop;

    @FXML
    ImageView img1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ImageView logo_center = new ImageView();
        setImageSource("philo.png", logo_center);
        logo_center.setFitWidth(130.0);
        logo_center.setFitHeight(250.0);
        logo_center.setLayoutX(118.0);
        logo_center.setLayoutY(77.0);
        pane.getChildren().add(logo_center);

       /* Optional<Node> img = pane.getChildrenUnmodifiable().stream()
                .filter(n->(n.getId()==null?"":n.getId()).trim().equals("img2")).findFirst();
        if(img.isPresent()){
            setImageSource("eat0.png", ((ImageView)img.get()));
            //System.out.println(img.get().getId());
        }*/

        INotifier notifier = (state, num) -> {

            Optional<Node> img_node = pane.getChildrenUnmodifiable().stream()
                    .filter(n->(n.getId()==null?"":n.getId()).trim().equals("img"+(num+1))).findFirst();
            if(img_node.isPresent()) {
                ImageView img = ((ImageView) img_node.get());
                switch (state) {
                    case Thinking:
                        setImageSource("eat0.png", img);
                        break;
                    case Starving:
                        setImageSource("eat1.png", img);
                        break;
                    case Eating:
                        setImageSource("eat1_red.png", img);
                        break;
                    default:
                        setImageSource("eat2.png", img);
                        break;
                }
            }
        };
        start.setOnAction(e -> {
            start.setDisable(true);
            stop.setDisable(false);

            Diner.start(notifier);
        });
        stop.setOnAction(e -> {
            //start.setDisable(false);
            stop.setDisable(true);
            Diner.stop();
        });
    }

    private void setImageSource(String image_path, ImageView img) {
        File file = new File("resources/"+image_path);

        if(file.exists()) {
            Image image = new Image(file.toURI().toString());
            img.setImage(image);
           /* img.setFitWidth(130.0);
            img.setFitHeight(250.0);
            img.setLayoutX(118.0);
            img.setLayoutY(77.0);*/
            img.setPreserveRatio(true);
        }else{
            System.out.println("Image non exist!!");
        }
    }
}
