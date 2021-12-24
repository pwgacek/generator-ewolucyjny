package agh.ics.oop.gui;

import agh.ics.oop.AbstractWorldMap;
import agh.ics.oop.MyThread;
import agh.ics.oop.SimulationConditions;
import agh.ics.oop.SimulationEngine;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class MapHandlerGridPane extends GridPane {
    private final MyThread engineThread;

    public MapHandlerGridPane(AbstractWorldMap map, SimulationConditions conditions){
        GridPane mapGridPane = new GridPane();
        Button stopStartBtn = new Button("START");
        GridPane.setHalignment(stopStartBtn, HPos.CENTER);
        GridPane.setConstraints(mapGridPane,0,0);
        GridPane.setConstraints(stopStartBtn,0,1);
        this.getChildren().add(mapGridPane);
        this.getChildren().add(stopStartBtn);

        this.getColumnConstraints().add(new ColumnConstraints(map.getWidth()));
        this.getRowConstraints().add(new RowConstraints(map.getHeight()));
        this.getRowConstraints().add(new RowConstraints(50));


        MapVisualizer mapVisualizer = new MapVisualizer(mapGridPane,map);
        engineThread =  new SimulationEngine(map, mapVisualizer, conditions);

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
