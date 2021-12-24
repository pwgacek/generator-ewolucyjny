package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class  App extends Application  {


    public void start(Stage primaryStage){

        try{
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


            Scene startScene = new Scene(startGridPane,400,600);

            primaryStage.setScene(startScene);
            primaryStage.show();


            readyBtn.setOnAction(e -> {
                try{
                    if((firstMapOptionsGridPane.getWidthCondition()+1)*(firstMapOptionsGridPane.getHeightCondition()+1) < firstMapOptionsGridPane.getAnimalQuantityCondition())throw new IllegalArgumentException("Map doesn't have enough space for animals!");
                    if((secondMapOptionsGridPane.getWidthCondition()+1)*(secondMapOptionsGridPane.getHeightCondition()+1) < secondMapOptionsGridPane.getAnimalQuantityCondition())throw new IllegalArgumentException("Map doesn't have enough space for animals!");
                    if(firstMapOptionsGridPane.getMoveEnergyCondition()==0)throw new IllegalArgumentException("Move Energy has to be above 0");
                    if(secondMapOptionsGridPane.getMoveEnergyCondition()==0)throw new IllegalArgumentException("Move Energy has to be above 0");
                    if(firstMapOptionsGridPane.getAnimalQuantityCondition()<10)throw new IllegalArgumentException("Minimum animal quantity is 10");
                    if(secondMapOptionsGridPane.getAnimalQuantityCondition()<10)throw new IllegalArgumentException("Minimum animal quantity is 10");


                    SimulationConditions firstSimulationConditions = new SimulationConditions(50,false,firstMapOptionsGridPane.getStartEnergyCondition(),firstMapOptionsGridPane.getMoveEnergyCondition(),firstMapOptionsGridPane.getPlantEnergyCondition(),firstMapOptionsGridPane.getAnimalQuantityCondition());
                    SimulationConditions secondSimulationConditions = new SimulationConditions(50,false,secondMapOptionsGridPane.getStartEnergyCondition(),secondMapOptionsGridPane.getMoveEnergyCondition(),secondMapOptionsGridPane.getPlantEnergyCondition(),secondMapOptionsGridPane.getAnimalQuantityCondition());

                    MapConditions firstMapConditions = new MapConditions(firstMapOptionsGridPane.getWidthCondition(), firstMapOptionsGridPane.getHeightCondition(), firstMapOptionsGridPane.getJungleRatio());
                    MapConditions secondMapConditions = new MapConditions(secondMapOptionsGridPane.getWidthCondition(), secondMapOptionsGridPane.getHeightCondition(), secondMapOptionsGridPane.getJungleRatio());


                    AbstractWorldMap firstMap = new TorusGrassField(firstMapConditions);
                    AbstractWorldMap secondMap = new GrassField(secondMapConditions);



                    GridPane generalPane = new GridPane();

                    MapHandlerGridPane firstMapPane = new MapHandlerGridPane(firstMap,firstSimulationConditions);
                    MapHandlerGridPane secondMapPane = new MapHandlerGridPane(secondMap,secondSimulationConditions);


                    GridPane.setConstraints(firstMapPane,0,0);
                    GridPane.setConstraints(secondMapPane,1,0);

                    generalPane.getChildren().add(firstMapPane);

                    generalPane.getChildren().add(secondMapPane);


                    generalPane.getColumnConstraints().add(new ColumnConstraints(640));
                    generalPane.getColumnConstraints().add(new ColumnConstraints(640));
                    generalPane.getRowConstraints().add(new RowConstraints(800));



                    //Scene simulationScene = new Scene(generalPane,(30*(firstMap.getWidth()+2))+(30*(secondMap.getWidth()+2))+20,30*(Math.max(firstMap.getHeight(),secondMap.getHeight()) +2)+100);
                    Scene simulationScene = new Scene(generalPane,1280, 800);





                    primaryStage.setScene(simulationScene);
                    firstMapPane.startSimulation();
                    secondMapPane.startSimulation();


                }catch(Exception exception){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sth went wrong...");
                    alert.setHeaderText(exception.getMessage());
                    alert.setContentText("Please, insert correct data.");
                    alert.showAndWait();
                }



            });


        }catch(IllegalArgumentException e){
            primaryStage.close();
            System.out.println(e.getMessage());

        }


        System.out.println("eloddd");
    }



}
