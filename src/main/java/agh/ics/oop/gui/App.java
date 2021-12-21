package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;


public class  App extends Application  {
    private AtomicBoolean stop = new AtomicBoolean();



    public void start(Stage primaryStage){
        //String[] moves = this.getParameters().getRaw().toArray(new String[0]);


        try{
            this.stop.set(false);
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
            GridPane gridPane = new GridPane();
            gridPane.getColumnConstraints().add(new ColumnConstraints(50));
            gridPane.getRowConstraints().add(new RowConstraints(50));

            gridPane.add(vBox,1,1);
            Scene scene = new Scene(gridPane,250, 200);
            primaryStage.setScene(scene);
            primaryStage.show();

            startBtn.setOnAction(e -> {

                GridPane generalPane = new GridPane();
                GridPane gridPane2 = new GridPane();
                Button stopBtn = new Button("STOP");
                GridPane.setConstraints(gridPane2,0,0);
                generalPane.getChildren().add(gridPane2);
                GridPane.setConstraints(stopBtn,0,1);
                generalPane.getChildren().add(stopBtn);
                GridPane.setHalignment(stopBtn, HPos.CENTER);
                Scene scene2 = new Scene(generalPane,30*12,30*12+100);



                MapVisualizer mapVisualizer = new MapVisualizer(gridPane2,scene2, map,primaryStage);
                SimulationEngine engine = new SimulationEngine(map, positions, mapVisualizer,moveDelay,stop);
                try{

                    Thread engineThread = new Thread(engine);
                    engineThread.start();
                    stopBtn.setOnAction(e2 -> {
                        if(stop.get()) engineThread.setUncaughtExceptionHandler();

                    });
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
