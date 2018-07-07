import javafx.animation.Timeline;
import javafx.scene.image.Image;

import javax.swing.text.Element;
import javax.swing.text.html.ImageView;

import static utils.Icon.*;

public class Angel extends javafx.scene.image.ImageView {
Image[] angels = {new Image(ANGEL1),new Image(ANGEL2),new Image(ANGEL3)};
Timeline angel = new Timeline();

    public Angel() {

        super(new Image(ANGEL1));
//        angel.getKeyFrames().add();
         angel.play();
    }
}
