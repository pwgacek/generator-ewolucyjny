package agh.ics.oop;

import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Statistician {
    private final int mapID;
    private final ArrayList<Snapshot> statisticsHistory;

    public Statistician(int mapID) {
        this.mapID = mapID;
        this.statisticsHistory = new ArrayList<>();
    }

    public void addSnapshot(Snapshot snapshot){

        statisticsHistory.add(snapshot);
    }


    public void writeStatisticsHistoryToFile(){

        String path = "src/main/statistics/map" + mapID + "statistics.csv";
        File file = new File(path);
        boolean writerOpen = false;
        PrintWriter writer = null;
        try {
            file.createNewFile();
            try{
                writer = new PrintWriter(path);
                writerOpen = true;

                int sumAnimalQuantity=0;
                int sumGrassQuantity=0;
                double sumAverageAnimalEnergy=0;
                double sumAverageLifeSpan=0;
                int lifeSpanCounter=0;
                double sumAverageChildrenQuantity=0;
                writer.println("date;animal quantity;grass quantity;average animal energy;average animal life span;average children quantity;");

                for(Snapshot snapshot: statisticsHistory){
                    sumAnimalQuantity+= snapshot.getAnimalQuantity();
                    sumGrassQuantity+= snapshot.getGrassQuantity();
                    sumAverageAnimalEnergy+= snapshot.getAverageAnimalEnergy();
                    sumAverageLifeSpan+= snapshot.getAverageLifeSpan();
                    if(snapshot.getAverageLifeSpan() > 0)lifeSpanCounter++;
                    sumAverageChildrenQuantity+=snapshot.getAverageChildrenQuantity();

                    String string = snapshot.getDate() + ";" +
                            snapshot.getAnimalQuantity() + ";" +
                            snapshot.getGrassQuantity() + ";" +
                            snapshot.getAverageAnimalEnergy() + ";" +
                            snapshot.getAverageLifeSpan() + ";" +
                            snapshot.getAverageChildrenQuantity() + ";";
                    writer.println(string);
                }

                double meanAnimalQuantity = Math.round(((double)sumAnimalQuantity/statisticsHistory.size())*100.0)/100.0;
                double meanGrassQuantity = Math.round(((double)sumGrassQuantity/statisticsHistory.size())*100.0)/100.0;
                double meanAverageAnimalEnergy = Math.round((sumAverageAnimalEnergy/statisticsHistory.size())*100.0)/100.0;
                double meanAverageAnimalLifeSpan;
                if(lifeSpanCounter>0){
                    meanAverageAnimalLifeSpan = Math.round((sumAverageLifeSpan/lifeSpanCounter)*100.0)/100.0;
                }
                else{
                    meanAverageAnimalLifeSpan =0;
                }

                double meanAverageChildrenQuantity = Math.round((sumAverageChildrenQuantity/statisticsHistory.size())*100.0)/100.0;

                writer.println("mean;"+meanAnimalQuantity+";"+meanGrassQuantity+";"+meanAverageAnimalEnergy+";"+meanAverageAnimalLifeSpan+";"+meanAverageChildrenQuantity+";");
                writer.close();
            }catch (FileNotFoundException e){
                if(writerOpen)writer.close();
                System.out.println(e.getMessage());

            }



        } catch (IOException e) {

            e.printStackTrace();
        }
    }


}
