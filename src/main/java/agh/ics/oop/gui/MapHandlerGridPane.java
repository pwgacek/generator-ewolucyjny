package agh.ics.oop.gui;

import agh.ics.oop.AbstractWorldMap;
import agh.ics.oop.MyThread;
import agh.ics.oop.SimulationConditions;
import agh.ics.oop.SimulationEngine;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;

public class MapHandlerGridPane extends GridPane {
    private final MyThread engineThread;
    private final int horizontalMargin = 30;

    public MapHandlerGridPane(AbstractWorldMap map, SimulationConditions conditions){
        GridPane mapGridPane = new GridPane();
        mapGridPane.setAlignment(Pos.CENTER);
        Button stopStartBtn = new Button("START");
        Label chosenGenotype = new Label();
        chosenGenotype.setFont(new Font(8));
        GridPane.setHalignment(chosenGenotype,HPos.RIGHT);
        //GridPane.setHalignment(stopStartBtn, HPos.CENTER);

        GridPane.setConstraints(mapGridPane,0,0,2,1);
        GridPane.setConstraints(stopStartBtn,0,1);
        GridPane.setConstraints(chosenGenotype,0,1,2,1);
        this.getChildren().add(mapGridPane);
        this.getChildren().add(stopStartBtn);
        this.getChildren().add(chosenGenotype);


        MapVisualizer mapVisualizer = new MapVisualizer(mapGridPane,map,chosenGenotype);
        engineThread =  new SimulationEngine(map, mapVisualizer, conditions);


        this.getColumnConstraints().add(new ColumnConstraints(15*(map.getWidth()+2)));
        this.getColumnConstraints().add(new ColumnConstraints(15*(map.getWidth()+2)));
        this.getRowConstraints().add(new RowConstraints(30*(map.getHeight()+2)));
        this.getRowConstraints().add(new RowConstraints(50));

        this.setHeight(30*(map.getHeight()+2)+50);
        this.setWidth(30*(map.getWidth()+2));

        stopStartBtn.setOnAction(e2 -> {
            if(conditions.IsRunning()) {
                conditions.setIsRunning(false);
                stopStartBtn.setText("START");
            }
            else{
                conditions.setIsRunning(true);
                engineThread.resumeMe();
                stopStartBtn.setText("STOP");
            }

        });


    }

    public void startSimulation(){
        this.engineThread.start();
    }



}
