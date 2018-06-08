import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class BouncingIcon extends ImageView {
    public BouncingIcon(Image image) {
        super(image);
        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setScaleX(1.2);
                setScaleY(1.2);
            }
        });
        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setScaleX(1.0);
                setScaleY(1.0);
            }
        });
    }
}
