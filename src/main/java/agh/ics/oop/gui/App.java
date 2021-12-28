package agh.ics.oop.gui;

import agh.ics.oop.conditions.MapConditions;
import agh.ics.oop.conditions.SimulationConditions;
import agh.ics.oop.gui.visualization.MapHandlerGridPane;
import agh.ics.oop.gui.options.OptionsGridPane;
import agh.ics.oop.maps.AbstractWorldMap;
import agh.ics.oop.maps.GrassField;
import agh.ics.oop.maps.TorusGrassField;
import javafx.application.Application;

import javafx.application.Platform;

import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;



public class  App extends Application  {


    public void start(Stage primaryStage){
        GridPane startGridPane = new GridPane();
        OptionsGridPane firstMapOptionsGridPane = new OptionsGridPane("First Map");
        OptionsGridPane secondMapOptionsGridPane = new OptionsGridPane("Second Map");

        GridPane.setConstraints(firstMapOptionsGridPane,0,0);
        GridPane.setConstraints(secondMapOptionsGridPane,1,0);
        startGridPane.getColumnConstraints().add(new ColumnConstraints(200));
        startGridPane.getColumnConstraints().add(new ColumnConstraints(200));
        startGridPane.getChildren().add(firstMapOptionsGridPane);
        startGridPane.getChildren().add(secondMapOptionsGridPane);

        Button readyBtn = new Button("Done");
        readyBtn.setMinWidth(200);
        GridPane.setConstraints(readyBtn,0,1,2,1);
        GridPane.setHalignment(readyBtn, HPos.CENTER);
        startGridPane.getChildren().add(readyBtn);


        Scene startScene = new Scene(startGridPane,400,700);
        primaryStage.setResizable(false);

        primaryStage.setScene(startScene);
        primaryStage.show();


        readyBtn.setOnAction(e -> {
            try{
                if((firstMapOptionsGridPane.getWidthCondition()+1)*(firstMapOptionsGridPane.getHeightCondition()+1) < firstMapOptionsGridPane.getAnimalQuantityCondition())throw new IllegalArgumentException("Map doesn't have enough space for animals!");
                if((secondMapOptionsGridPane.getWidthCondition()+1)*(secondMapOptionsGridPane.getHeightCondition()+1) < secondMapOptionsGridPane.getAnimalQuantityCondition())throw new IllegalArgumentException("Map doesn't have enough space for animals!");
                if(firstMapOptionsGridPane.getMoveEnergyCondition()==0)throw new IllegalArgumentException("Move Energy should be above 0");
                if(secondMapOptionsGridPane.getMoveEnergyCondition()==0)throw new IllegalArgumentException("Move Energy should be above 0");
                if(firstMapOptionsGridPane.getAnimalQuantityCondition()<10)throw new IllegalArgumentException("Minimum quantity of animals is 10");
                if(secondMapOptionsGridPane.getAnimalQuantityCondition()<10)throw new IllegalArgumentException("Minimum quantity of animals is 10");


                SimulationConditions firstSimulationConditions = new SimulationConditions(50,false,firstMapOptionsGridPane.getStartEnergyCondition(),firstMapOptionsGridPane.getMoveEnergyCondition(),firstMapOptionsGridPane.getPlantEnergyCondition(),firstMapOptionsGridPane.getAnimalQuantityCondition(),firstMapOptionsGridPane.isEvolutionMagical());
                SimulationConditions secondSimulationConditions = new SimulationConditions(50,false,secondMapOptionsGridPane.getStartEnergyCondition(),secondMapOptionsGridPane.getMoveEnergyCondition(),secondMapOptionsGridPane.getPlantEnergyCondition(),secondMapOptionsGridPane.getAnimalQuantityCondition(),secondMapOptionsGridPane.isEvolutionMagical());

                MapConditions firstMapConditions = new MapConditions(firstMapOptionsGridPane.getWidthCondition(), firstMapOptionsGridPane.getHeightCondition(), firstMapOptionsGridPane.getJungleRatio());
                MapConditions secondMapConditions = new MapConditions(secondMapOptionsGridPane.getWidthCondition(), secondMapOptionsGridPane.getHeightCondition(), secondMapOptionsGridPane.getJungleRatio());


                AbstractWorldMap firstMap = new TorusGrassField(firstMapConditions);
                AbstractWorldMap secondMap = new GrassField(secondMapConditions);



                GridPane generalGridPane = new GridPane();

                MapHandlerGridPane firstMapPane = new MapHandlerGridPane(firstMap,firstSimulationConditions);
                MapHandlerGridPane secondMapPane = new MapHandlerGridPane(secondMap,secondSimulationConditions);


                GridPane.setConstraints(firstMapPane,0,0);
                GridPane.setConstraints(secondMapPane,1,0);

                generalGridPane.getChildren().add(firstMapPane);
                generalGridPane.getChildren().add(secondMapPane);


                generalGridPane.getColumnConstraints().add(new ColumnConstraints(640));
                generalGridPane.getColumnConstraints().add(new ColumnConstraints(640));
                generalGridPane.getRowConstraints().add(new RowConstraints(670));



                Scene simulationScene = new Scene(generalGridPane,1280, 670);





                primaryStage.setScene(simulationScene);
                firstMapPane.startSimulation();
                secondMapPane.startSimulation();




                primaryStage.setOnCloseRequest(t -> {
                    if(firstSimulationConditions.isRunning() || secondSimulationConditions.isRunning())
                    {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Sth went wrong...");
                        alert.setHeaderText("Please, stop simulation before you close the window!");
                        alert.showAndWait();
                        t.consume();
                    }
                    else{
                        firstMapPane.terminateSimulation();
                        secondMapPane.terminateSimulation();
                        Platform.exit();
                        System.exit(0);
                    }

                });




            }catch(Exception exception){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sth went wrong...");
                alert.setHeaderText(exception.getMessage());
                alert.setContentText("Please, insert correct data.");
                alert.showAndWait();
            }



        });


    }



}
