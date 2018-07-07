import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import static utils.ConstantStrings.*;
import static utils.ConstantNumbers.*;
import static utils.Icon.*;

public class MenuButton extends BouncingButton {
    public MenuButton(String name) {
        super(new Image(WHITE_FIRE,BUTTON_IMAGE_SIZE,BUTTON_IMAGE_SIZE,true,true),
                name, BUTTON_FONT_SIZE, Color.VIOLET.darker());
        setEffect(new Reflection());
    }
}
