package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;

import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class  App extends Application  {


    public void start(Stage primaryStage){

        try{
            SimulationConditions firstSimulationConditions = new SimulationConditions(50,false,100,1,10,60);
            SimulationConditions secondSimulationConditions = new SimulationConditions(50,false,100,1,10,60);

            MapConditions firstMapConditions = new MapConditions(10,30,0.5);
            MapConditions secondMapConditions = new MapConditions(30,10,0.5);


            AbstractWorldMap firstMap = new TorusGrassField(firstMapConditions);
            AbstractWorldMap secondMap = new GrassField(secondMapConditions);



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

            Scene scene = new Scene(generalPane,(30*(firstMap.getWidth()+2))+(30*(secondMap.getWidth()+2))+20,30*(Math.max(firstMap.getHeight(),secondMap.getHeight()) +2)+100);
            primaryStage.setScene(scene);



            MapVisualizer mapVisualizer = new MapVisualizer(firstMapPane, firstMap,primaryStage);
            MapVisualizer mapVisualizer2 = new MapVisualizer(secondMapPane, secondMap,primaryStage);

            MyThread engineThread =  new SimulationEngine(firstMap, mapVisualizer, firstSimulationConditions);
            MyThread engineThread2 =  new SimulationEngine(secondMap, mapVisualizer2, secondSimulationConditions);

            try{

                engineThread.start();
                engineThread2.start();

                stopStartBtn.setOnAction(e -> {
                    if(firstSimulationConditions.getIsRunning()) {
                        firstSimulationConditions.setIsRunning(false);
                        stopStartBtn.setText("START");
                    }
                    else{
                        firstSimulationConditions.setIsRunning(true);
                        engineThread.resumeMe();
                        stopStartBtn.setText("STOP");
                    }

                });

                stopStartBtn2.setOnAction(e -> {
                    if(secondSimulationConditions.getIsRunning()) {
                        secondSimulationConditions.setIsRunning(false);
                        stopStartBtn2.setText("START2");
                    }
                    else{
                        secondSimulationConditions.setIsRunning(true);
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


        System.out.println("eloddd");
    }



}
