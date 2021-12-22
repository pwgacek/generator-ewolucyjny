package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;

import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;


public class  App extends Application  {
    private final AtomicBoolean isRunning = new AtomicBoolean();
    private final AtomicBoolean isRunning2 = new AtomicBoolean();



    public void start(Stage primaryStage){

        try{
            this.isRunning.set(false);
            this.isRunning2.set(false);
            AbstractWorldMap map = new GrassField(20,20);
            AbstractWorldMap map2 = new TorusGrassField(20,20);
            ArrayList<Vector2d> positions = new ArrayList<>();

            positions.add(new Vector2d(10,10));
            positions.add(new Vector2d(11,10));
            positions.add(new Vector2d(10,11));
            positions.add(new Vector2d(11,11));
            positions.add(new Vector2d(10,9));
            positions.add(new Vector2d(9,10));
            positions.add(new Vector2d(9,11));
            positions.add(new Vector2d(11,9));
            positions.add(new Vector2d(9,9));

            int moveDelay =50;

            GridPane generalPane = new GridPane();

            GridPane firstMapPane = new GridPane();
            GridPane secondMapPane = new GridPane();

            Button stopStartBtn = new Button("START");
            Button stopStartBtn2 = new Button("START2");

            GridPane.setConstraints(firstMapPane,0,0);
            GridPane.setConstraints(secondMapPane,1,0);

            generalPane.getChildren().add(firstMapPane);
            generalPane.getChildren().add(secondMapPane);

            GridPane.setConstraints(stopStartBtn,0,1);
            GridPane.setConstraints(stopStartBtn2,1,1);

            generalPane.getChildren().add(stopStartBtn);
            generalPane.getChildren().add(stopStartBtn2);

            GridPane.setHalignment(stopStartBtn, HPos.CENTER);
            GridPane.setHalignment(stopStartBtn2, HPos.CENTER);

            Scene scene = new Scene(generalPane,(30*(map.getWidth()+2))+(30*(map.getWidth()+2))+20,30*(map.getHeight()+2)+100);
            primaryStage.setScene(scene);



            MapVisualizer mapVisualizer = new MapVisualizer(firstMapPane, map,primaryStage);
            MapVisualizer mapVisualizer2 = new MapVisualizer(secondMapPane, map2,primaryStage);

            MyThread engineThread =  new SimulationEngine(map, positions, mapVisualizer,moveDelay, isRunning);
            MyThread engineThread2 =  new SimulationEngine(map2, positions, mapVisualizer2,moveDelay, isRunning2);

            try{

                engineThread.start();
                engineThread2.start();

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

                stopStartBtn2.setOnAction(e -> {
                    if(isRunning2.get()) {
                        isRunning2.set(false);
                        stopStartBtn2.setText("START2");
                    }
                    else{
                        isRunning2.set(true);
                        engineThread2.resumeMe();
                        stopStartBtn2.setText("STOP2");
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
