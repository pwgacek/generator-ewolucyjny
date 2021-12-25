package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;

import java.util.concurrent.atomic.AtomicBoolean;

public class MapHandlerGridPane extends GridPane {
    private final MyThread engineThread;
    private final int horizontalMargin = 30;

    public MapHandlerGridPane(AbstractWorldMap map, SimulationConditions conditions){
        double cellSize = Math.min(640/(map.getWidth()+2),400/(map.getHeight()+2));
        GridPane mapGridPane = new GridPane();
        mapGridPane.setAlignment(Pos.CENTER);
        Button stopStartBtn = new Button("START");
        Label chosenGenotype = new Label();
        chosenGenotype.setFont(new Font(8));
        GridPane.setHalignment(chosenGenotype,HPos.RIGHT);
        GridPane.setHalignment(stopStartBtn, HPos.CENTER);

        GridPane.setConstraints(mapGridPane,0,0,2,1);
        GridPane.setConstraints(stopStartBtn,0,1);
        GridPane.setConstraints(chosenGenotype,0,1,2,1);
        this.getChildren().add(mapGridPane);
        this.getChildren().add(stopStartBtn);
        this.getChildren().add(chosenGenotype);

        AtomicBoolean isRunning = conditions.getIsRunning();
        Statistics statistics = new Statistics();
        MapVisualizer mapVisualizer = new MapVisualizer(mapGridPane,map,chosenGenotype,cellSize,isRunning);
        engineThread =  new SimulationEngine(map, mapVisualizer, conditions,statistics);


        this.getColumnConstraints().add(new ColumnConstraints(320));
        this.getColumnConstraints().add(new ColumnConstraints(320));
        this.getRowConstraints().add(new RowConstraints(cellSize*(map.getHeight()+2)));
        this.getRowConstraints().add(new RowConstraints(50));

        this.setWidth(640);
        this.setHeight(800);


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
