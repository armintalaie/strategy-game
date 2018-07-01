import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javax.swing.text.html.ImageView;

public class GraphicEnemyMapCell extends VBox {
    GraphicEnemyMapDetailedCell[][] unitCell = new GraphicEnemyMapDetailedCell[9][9];
//    just grass
    public GraphicEnemyMapCell() {
        GraphicOwnMapCell unitCell = new GraphicOwnMapCell();
        super.getChildren().add(unitCell);
    }
    public GraphicEnemyMapCell(int[] soldierTypes){
        int num = soldierTypes.length;
        for (int i = 0; i <9; i++) {
            HBox row = new HBox();
            for (int j = 0; j <9 ; j++) {
                if (num>0){
                   num-=1;
                    unitCell[i][j] = new GraphicEnemyMapDetailedCell(soldierTypes[num]);
                }
                else unitCell[i][j] = new GraphicEnemyMapDetailedCell();
            }
            super.getChildren().add(row);
        }
    }
    public GraphicEnemyMapCell(int buildingType){
    GraphicOwnMapCell unitCell = new GraphicOwnMapCell(buildingType);
    super.getChildren().add(unitCell);
    }

}
