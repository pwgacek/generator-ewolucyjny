package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;


public class  App extends Application  {
    private AtomicBoolean isRunning = new AtomicBoolean();



    public void start(Stage primaryStage){
        //String[] moves = this.getParameters().getRaw().toArray(new String[0]);


        try{
            this.isRunning.set(false);
            AbstractWorldMap map = new GrassField(10,10);
            ArrayList<Vector2d> positions = new ArrayList<>();

            positions.add(new Vector2d(4,4));
            positions.add(new Vector2d(4,5));
            positions.add(new Vector2d(5,4));
            positions.add(new Vector2d(5,5));
            positions.add(new Vector2d(4,4));
            positions.add(new Vector2d(4,5));
            positions.add(new Vector2d(5,4));
            positions.add(new Vector2d(5,5));

            int moveDelay =50;

            GridPane generalPane = new GridPane();
            GridPane firstMapPane = new GridPane();
            Button stopStartBtn = new Button("START");
            GridPane.setConstraints(firstMapPane,0,0);
            generalPane.getChildren().add(firstMapPane);
            GridPane.setConstraints(stopStartBtn,0,1);
            generalPane.getChildren().add(stopStartBtn);
            GridPane.setHalignment(stopStartBtn, HPos.CENTER);
            Scene scene = new Scene(generalPane,30*12,30*12+100);
            primaryStage.setScene(scene);



            MapVisualizer mapVisualizer = new MapVisualizer(firstMapPane, map,primaryStage);
            MyThread engineThread =  new SimulationEngine(map, positions, mapVisualizer,moveDelay, isRunning);

            try{

                engineThread.start();
                stopStartBtn.setOnAction(e -> {
                    if(isRunning.get()) {
                        isRunning.set(false);
                        stopStartBtn.setText("START");
                    }
                    else{
                        isRunning.set(true);
                        engineThread.resumeMe();
                        stopStartBtn.setText("STOP");
                    }

                });
            }catch (IllegalArgumentException e){
                primaryStage.close();
                System.out.println(e.getMessage());
            }

        }catch(IllegalArgumentException e){
            primaryStage.close();
            System.out.println(e.getMessage());

        }



    }



}
