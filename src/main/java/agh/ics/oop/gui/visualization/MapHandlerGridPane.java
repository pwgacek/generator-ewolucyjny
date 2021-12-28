package agh.ics.oop.gui.visualization;

import agh.ics.oop.conditions.SimulationConditions;
import agh.ics.oop.gui.charts.ChartsHolder;
import agh.ics.oop.gui.charts.DoubleStatsChart;
import agh.ics.oop.gui.charts.StatsChart;
import agh.ics.oop.maps.AbstractWorldMap;
import agh.ics.oop.simulation.SimulationEngine;
import agh.ics.oop.statistics.Snapshot;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class MapHandlerGridPane extends GridPane {

    private final SimulationEngine engineThread;
    private final Button stopStartBtn;
    private final Label dominantGenotypeLabel;
    private final DoubleStatsChart animalAndGrassChart;
    private final StatsChart energyChart;
    private final StatsChart lifeSpanChart;
    private final StatsChart childrenQuantityChart;
    private final SimulationConditions conditions;

    public MapHandlerGridPane(AbstractWorldMap map, SimulationConditions conditions){

        this.conditions = conditions;
        double cellSize = Math.min(640/(map.getWidth()+2),300/(map.getHeight()+2));
        AtomicBoolean isRunning = this.conditions.getIsRunning();

        GridPane mapGridPane = new GridPane();
        mapGridPane.setAlignment(Pos.CENTER);


        stopStartBtn = new Button("START");
        stopStartBtn.setPrefWidth(70);
        dominantGenotypeLabel = new Label();
        dominantGenotypeLabel.setFont(new Font(12));


        HBox hBox = new HBox(stopStartBtn, dominantGenotypeLabel);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(15);
        GridPane.setHalignment(hBox, HPos.CENTER);


        animalAndGrassChart = new DoubleStatsChart("quantity of animals(red) & grass(orange)");
        energyChart = new StatsChart("average animal energy");
        lifeSpanChart = new StatsChart("average life span");
        childrenQuantityChart = new StatsChart("average children quantity");

        ChartsHolder chartsHolder = new ChartsHolder(animalAndGrassChart,energyChart,lifeSpanChart,childrenQuantityChart);



        GridPane.setConstraints(mapGridPane,0,0,2,1);
        GridPane.setConstraints(hBox,0,1,2,1);
        GridPane.setConstraints(chartsHolder,0,2,2,1);

        this.getChildren().add(mapGridPane);
        this.getChildren().add(hBox);
        this.getChildren().add(chartsHolder);



        MapVisualizer mapVisualizer = new MapVisualizer(mapGridPane,map,cellSize,isRunning);
        engineThread =  new SimulationEngine(this,map, mapVisualizer);



        this.getColumnConstraints().add(new ColumnConstraints(320));
        this.getColumnConstraints().add(new ColumnConstraints(320));
        this.getRowConstraints().add(new RowConstraints(300));
        this.getRowConstraints().add(new RowConstraints(50));
        this.getRowConstraints().add(new RowConstraints(300));

        this.setWidth(640);
        this.setHeight(670);


        stopStartBtn.setOnAction(e2 -> {
            if(conditions.isRunning()) {
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

   public void terminateSimulation(){
        if(this.engineThread.isAlive()) this.engineThread.setTerminated(true);
        else System.out.println("wątek juz nie zyje");
   }

    public SimulationConditions getConditions() {
        return conditions;
    }

    public void disableStopStartBtn(){
        stopStartBtn.setText("STOP");
        stopStartBtn.setDisable(true);
    }

    public void updateCharts(Snapshot snapshot){

        this.animalAndGrassChart.update(snapshot.getAnimalQuantity(),snapshot.getGrassQuantity());
        this.energyChart.update(snapshot.getAverageAnimalEnergy());
        this.lifeSpanChart.update(snapshot.getAverageLifeSpan());
        this.childrenQuantityChart.update(snapshot.getAverageChildrenQuantity());

    }

    public void updateDominantGenotypeLabel(ArrayList<Integer> dominantGenotype){
        if(dominantGenotype!=null)this.dominantGenotypeLabel.setText("dominant genotype: "+dominantGenotype);
        else this.dominantGenotypeLabel.setText("dominant genotype: none");
    }

    public void showMagicalEvolutionInfo(int evolutionCounter,int mapID){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Magical evolution happened on map "+mapID+"!");
        alert.setContentText("Already performed "+evolutionCounter+" evolutions");
        alert.showAndWait();

    }






}
