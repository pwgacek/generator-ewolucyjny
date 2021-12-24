package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class MapVisualizer {
    private GridPane gridPane;
    private final AbstractWorldMap map;
    private final GuiElementBox[][] guiElementBoxArray;





    MapVisualizer(GridPane gridPane, AbstractWorldMap map){
        this.gridPane = gridPane;

        for(int x=0;x<=map.getWidth()+1;x++){
            gridPane.getColumnConstraints().add(new ColumnConstraints(30));
        }
        for(int y=0;y<=map.getHeight()+1;y++){
            gridPane.getRowConstraints().add(new RowConstraints(30));
        }
        guiElementBoxArray = new GuiElementBox[(map.getWidth()+1)][(map.getHeight()+1)];
        Label label = new Label("y/x");
        gridPane.add(label,0,0);
        GridPane.setHalignment(label, HPos.CENTER);

        for(int x=0;x<=map.getWidth();x++){
            label = new Label(String.valueOf(x));
            gridPane.add(label,x+1,0);
            GridPane.setHalignment(label, HPos.CENTER);

        }
        for(int y=0;y<=map.getHeight();y++){
            label = new Label(String.valueOf(map.getHeight()-y));
            gridPane.add(label,0,y+1);
            GridPane.setHalignment(label, HPos.CENTER);


        }

        for(int y=0;y<=map.getHeight();y++){
            for(int x=0;x<=map.getWidth();x++){
                Vector2d cords = new Vector2d(x,y);
                boolean isJungle = map.jungleBottomLeftCords.precedes(cords) && map.jungleUpperRightCords.follows(cords);
                guiElementBoxArray[x][y] = new GuiElementBox(isJungle);
                gridPane.add(guiElementBoxArray[x][y].getVerticalBox(),x+1,map.getHeight()-y +1);
                GridPane.setHalignment(guiElementBoxArray[x][y].getVerticalBox(), HPos.CENTER);
            }
        }
        gridPane.setGridLinesVisible(true);
        this.map = map;


    }



    public void positionChanged() {

        for(int y = 0; y<=map.getHeight();y++){
            for(int x = 0;x <=map.getWidth();x++){
                IMapElement element = (IMapElement) map.objectAt(new Vector2d(x,y));


                if(element !=null){

                    guiElementBoxArray[x][y].setImageView(element);

                }
                else{
                    guiElementBoxArray[x][y].removeImageView();

                }

            }
        }


    }
}
