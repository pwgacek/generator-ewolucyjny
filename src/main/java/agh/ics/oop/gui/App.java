package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;

import javafx.geometry.HPos;
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

                    Scene simulationScene = new Scene(generalPane,(30*(firstMap.getWidth()+2))+(30*(secondMap.getWidth()+2))+20,30*(Math.max(firstMap.getHeight(),secondMap.getHeight()) +2)+100);




                    MapVisualizer mapVisualizer = new MapVisualizer(firstMapPane, firstMap);
                    MapVisualizer mapVisualizer2 = new MapVisualizer(secondMapPane, secondMap);
                    primaryStage.setScene(simulationScene);
                    MyThread engineThread =  new SimulationEngine(firstMap, mapVisualizer, firstSimulationConditions);
                    MyThread engineThread2 =  new SimulationEngine(secondMap, mapVisualizer2, secondSimulationConditions);

                    try{

                        engineThread.start();
                        engineThread2.start();

                        stopStartBtn.setOnAction(e2 -> {
                            if(firstSimulationConditions.IsRunning()) {
                                firstSimulationConditions.setIsRunning(false);
                                stopStartBtn.setText("START");
                            }
                            else{
                                firstSimulationConditions.setIsRunning(true);
                                engineThread.resumeMe();
                                stopStartBtn.setText("STOP");
                            }

                        });

                        stopStartBtn2.setOnAction(e2-> {
                            if(secondSimulationConditions.IsRunning()) {
                                secondSimulationConditions.setIsRunning(false);
                                stopStartBtn2.setText("START2");
                            }
                            else{
                                secondSimulationConditions.setIsRunning(true);
                                engineThread2.resumeMe();
                                stopStartBtn2.setText("STOP2");
                            }

                        });
                    }catch (IllegalArgumentException e2){
                        primaryStage.close();
                        System.out.println(e2.getMessage());
                    }

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
