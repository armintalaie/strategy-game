import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class BouncingButton extends StackPane {
    public BouncingButton(Image image , String name , int size ,Color color) {
        super();
        ImageView imageView = new ImageView(image);
        Text nameT = new Text (name);
        nameT.setFont(Font.font("Courier New", FontWeight.BOLD,size));
        nameT.setFill(color);
        super.getChildren().add(imageView);
        super.getChildren().add(nameT);
        super.setAlignment(Pos.CENTER);
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
