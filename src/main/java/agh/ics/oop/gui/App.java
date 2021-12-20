package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;


public class  App extends Application  {

    GridPane gridPane = new GridPane();
    Scene scene;

    public void start(Stage primaryStage){
        //String[] moves = this.getParameters().getRaw().toArray(new String[0]);


        try{

            AbstractWorldMap map = new GrassField(40,10,10);
            ArrayList<Vector2d> positions = new ArrayList<>();

            positions.add(new Vector2d(4,4));
            positions.add(new Vector2d(4,5));
            positions.add(new Vector2d(5,4));
            positions.add(new Vector2d(5,5));
            positions.add(new Vector2d(4,4));
            positions.add(new Vector2d(4,5));
            positions.add(new Vector2d(5,4));
            positions.add(new Vector2d(5,5));

            int moveDelay =200;



            Button startBtn = new Button("start");


            VBox vBox = new VBox(startBtn);
            vBox.setAlignment(Pos.CENTER);
            vBox.setSpacing(10);
            gridPane.getColumnConstraints().add(new ColumnConstraints(50));
            gridPane.getRowConstraints().add(new RowConstraints(50));

            gridPane.add(vBox,1,1);
            scene = new Scene(gridPane,250,150);
            primaryStage.setScene(scene);
            primaryStage.show();

            startBtn.setOnAction(e -> {
                MapVisualizer mapVisualizer = new MapVisualizer(map,primaryStage);
                SimulationEngine engine = new SimulationEngine(map, positions, mapVisualizer,moveDelay);
                try{

                    Thread engineThread = new Thread(engine);
                    engineThread.start();
                }catch (IllegalArgumentException ex){
                    primaryStage.close();
                    System.out.println(ex.getMessage());
                }


            });




        }catch(IllegalArgumentException e){
            primaryStage.close();
            System.out.println(e.getMessage());

        }



    }



}
