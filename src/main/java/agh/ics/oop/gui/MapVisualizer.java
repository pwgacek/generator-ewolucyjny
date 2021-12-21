package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class MapVisualizer {
    private GridPane gridPane;

    private final AbstractWorldMap map;
    private final Stage primaryStage;
    private final  Scene scene;




    MapVisualizer(GridPane gridPane,Scene scene, AbstractWorldMap map, Stage primaryStage){
        this.gridPane = gridPane;
        gridPane.setMinHeight(360);
        gridPane.setMaxHeight(360);
        gridPane.setMinWidth(360);
        gridPane.setMaxWidth(360);



        this.map = map;
        this.primaryStage = primaryStage;
        this.scene = scene;
        this.primaryStage.setScene(this.scene);



    }



    public void positionChanged() {
        //this.gridPane = new GridPane();
        gridPane.setGridLinesVisible(false);

        gridPane.getChildren().clear();

        //gridPane.setGridLinesVisible(true);


        Label label = new Label("y/x");
        gridPane.add(label,0,0);
        gridPane.getColumnConstraints().add(new ColumnConstraints(30));
        gridPane.getRowConstraints().add(new RowConstraints(30));
        GridPane.setHalignment(label, HPos.CENTER);

        for(int x=0;x<=map.getWidth();x++){
            label = new Label(String.valueOf(x));
            gridPane.add(label,x+1,0);
            GridPane.setHalignment(label, HPos.CENTER);
            gridPane.getColumnConstraints().add(new ColumnConstraints(30));
        }
        for(int y=0;y<=map.getHeight();y++){
            label = new Label(String.valueOf(map.getHeight()-y));
            gridPane.add(label,0,y+1);
            GridPane.setHalignment(label, HPos.CENTER);
            gridPane.getRowConstraints().add(new RowConstraints(30));

        }
        boolean isJungle;
        for(int y = 0; y<=map.getHeight();y++){
            for(int x = 0;x <=map.getWidth();x++){
                IMapElement element = (IMapElement) map.objectAt(new Vector2d(x,y));
                GuiElementBox guiElementBox;
                isJungle = y>3 && y<7 && x>3 && x<7;
                if(element !=null){

                    guiElementBox = new GuiElementBox(element,isJungle);

                }
                else{
                    guiElementBox = new GuiElementBox(isJungle);

                }
                gridPane.add(guiElementBox.getVerticalBox(),x+1,map.getHeight()-y +1);
                GridPane.setHalignment(guiElementBox.getVerticalBox(), HPos.CENTER);
            }
        }

        //this.primaryStage.setScene(new Scene(this.getGridPane(),this.getGridPane().getColumnCount()*30,this.getGridPane().getRowCount()*30));
        this.primaryStage.show();
    }
}
